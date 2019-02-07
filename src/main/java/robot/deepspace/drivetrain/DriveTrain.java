package robot.deepspace.drivetrain;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.deepspace.RobotMap;

/** Drive train
 * 
 *  Each side has two motors, encoder, 2-speed gearbox
 */
public class DriveTrain extends Subsystem
{
    // Support units :
    // 95700 counts for 36feet
    private final static double COUNTS_PER_INCH = 95700 / (36.0*12.0);
    private final WPI_TalonSRX left = new WPI_TalonSRX(RobotMap.LEFT_MOTOR_MAIN);
    private final WPI_TalonSRX left_slave = new WPI_TalonSRX(RobotMap.LEFT_MOTOR_SLAVE);

    private final WPI_TalonSRX right = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR_MAIN);
    private final WPI_TalonSRX right_slave = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR_SLAVE);
    
    private final DifferentialDrive drive = new DifferentialDrive(left, right);
    private final Solenoid gearbox1 = new Solenoid(RobotMap.GEARBOX_SOLENOID1);
    private final Solenoid gearbox2 = new Solenoid(RobotMap.GEARBOX_SOLENOID2);
    private double speed = 0;
    private double rotation = 0;

    private final Gyro gyro = new ADXRS450_Gyro();

    private PIDController position_pid;
    private PIDController heading_pid;

    public DriveTrain()
    {
        // Resets Everything To Default
        left.configFactoryDefault();
        right.configFactoryDefault();
        left_slave.configFactoryDefault();
        right_slave.configFactoryDefault();

        // Invert motors or sensors as necessary
        left.setInverted(true);
        right.setInverted(true);
        left_slave.setInverted(true);
        right_slave.setInverted(true);

        left.setSensorPhase(false);
        right.setSensorPhase(true);

        // Coast or break when speed is set to 0.0?
        left.setNeutralMode(NeutralMode.Brake);
        right.setNeutralMode(NeutralMode.Brake);

        // Use quad (relative) encoder
        left.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        right.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
       
        // Tie Slaves To Master
        left_slave.follow(left);
        right_slave.follow(right);
        left_slave.setSafetyEnabled(false);
        right_slave.setSafetyEnabled(false);

        // No deadband on differential drive to allow
        // even small PID-driven moves.
        // If joystick needs dead zone, put that into OI.
        drive.setDeadband(0.0);

        // Initially, set low gear
        setGear(false);

        // Create PID controller for position,
        // controlling speed based on encoder
        PIDSource pos_source = new PIDSource()
        {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource)
            {
                // Ignore                
            }

            @Override
            public PIDSourceType getPIDSourceType()
            {
                return PIDSourceType.kDisplacement;
            }
        
            @Override
            public double pidGet()
            {
                return getPosition();
            }        
        };
        position_pid = new PIDController(0.045, 0, 0.05  , pos_source, this::setSpeed);
        position_pid.setOutputRange(-0.5, 0.5);
        // Check https://frc-pdr.readthedocs.io/en/latest/control/using_WPILIB's_pid_controller.html#adding-ramping-for-motors
        // for more ideas on damping motor speed changes
   
        SmartDashboard.putData("Pos. PID", position_pid);

        // Create PID controller for heading,
        // controlling rotation based on gyro.
        // In principle this could use the 'continuous' mode
        // so 350 degrees + 10 = 0 degrees, wrapping at 360 degrees.
        // But gyro keeps going up/down across the 0 and 360 range,
        // so stay with range used by gyro.
        PIDSource heading_source = new PIDSource()
        {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource)
            {
                // Ignore                
            }

            @Override
            public PIDSourceType getPIDSourceType()
            {
                return PIDSourceType.kDisplacement;
            }
        
            @Override
            public double pidGet()
            {
                return getHeading();
            }        
        };
        heading_pid = new PIDController(0.03, 0.000, 0.045, heading_source, this::setRotation);
        // TODO Try continuous mode?
        // heading_pid.setInputRange(-180, 180);
        // heading_pid.setContinuous();
        // Limit output, otherwise robot is too agressive
        heading_pid.setOutputRange(-0.4, 0.4);
        SmartDashboard.putData("Heading PID", heading_pid);
    }

    @Override
    protected void initDefaultCommand() 
    {
        // Does nothing YETTTT
    }
    
    public boolean isHighGear()
    {
        return gearbox1.get();
    }

    public void setGear(final boolean high)
    {
        gearbox1.set(high);
        gearbox2.set(high);
        SmartDashboard.putBoolean("Gear", high);
    }
    
    public void setSpeed(final double speed)
    {
        this.speed = speed;
    }

    public double getSpeed()
    {
        return speed;
    }

    public void setRotation(final double new_rotation)
    {
        rotation = new_rotation;
    }

    /** Zero encoder positions and heading to zero */
    public void resetEncoders()
    {
        left.setSelectedSensorPosition(0);
        right.setSelectedSensorPosition(0);
        gyro.reset();
    }

    /** @return position Position in inches */ 
    public double getPosition()
    {
        // Get average with right encoder
        final int position = (left.getSelectedSensorPosition() +
                              right.getSelectedSensorPosition()) / 2;
        return position / COUNTS_PER_INCH;
    }

    /** @param inches Desired position in inches or NaN to disable PID */
    public void setPosition(final double inches)
    {
        if (Double.isNaN(inches))
            position_pid.setEnabled(false);
        else
        {
            position_pid.setSetpoint(inches);
            if (! position_pid.isEnabled())
                position_pid.setEnabled(true);
        }
    }

    /** @return Current heading in degrees */
    public double getHeading()
    {
        return gyro.getAngle();
    }

    /** @return Current turn rate in degrees per second */
    public double getTurnRate()
    {
        return gyro.getRate();
    }

    /** Set desired angle for PID control of heading
     *  or NaN to disable heading PID.
     * 
     *  Angle does not reset at 0 or 360, meaning:
     *  Assume you are at 350 degrees.
     *  Requesting 370 degrees will turn clockwise
     *  by another 20 degrees.
     *  On the other hand, requesting 10 degrees would
     *  rotate counter-clockwise by 340.
     * 
     * @param degrees Desired heading in degrees
     */
    public void setHeading(final double degrees)
    {
        if (Double.isNaN(degrees))
            heading_pid.setEnabled(false);
        else
        {
            heading_pid.setSetpoint(degrees);
            if (! heading_pid.isEnabled())
            {
                heading_pid.reset();
                heading_pid.setEnabled(true);
            }
        }
    }

    @Override
    public void periodic()
    {
        drive.arcadeDrive(speed, rotation, false);

        SmartDashboard.putNumber("Left Encoder", left.getSelectedSensorPosition());
        SmartDashboard.putNumber("RightEncoder", right.getSelectedSensorPosition());
        SmartDashboard.putNumber("Position", getPosition());
        SmartDashboard.putNumber("Heading", getHeading());
        SmartDashboard.putNumber("Turn Rate", getTurnRate());
    }
}
