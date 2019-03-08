package robot.deepspace.drivetrain;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.sensors.PigeonIMU.FusionStatus;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
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

    private final PigeonIMU pigeon = new PigeonIMU(left_slave);

    private final DifferentialDrive drive = new DifferentialDrive(left, right);
    private final Solenoid gearbox = new Solenoid(RobotMap.GEARBOX_SOLENOID);
    private double speed = 0;
    private double rotation = 0;

    private PIDController position_pid;
    private PIDController heading_pid;

    public DriveTrain()
    {
        // Resets Everything To Default
        left.configFactoryDefault();
        right.configFactoryDefault();
        left_slave.configFactoryDefault();
        right_slave.configFactoryDefault();

        pigeon.configFactoryDefault();

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
        // TODO Neutral mode needs to be set for both master and follower

        // Use quad (relative) encoder
        left.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        right.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
       
        // TODO Slow down acceleration
        // left.configOpenloopRamp(0.15);
        // right.configOpenloopRamp(0.15);
        // left_slave.configOpenloopRamp(0.15);
        // right_slave.configOpenloopRamp(0.15);

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
   
        // SmartDashboard.putData("Pos. PID", position_pid);

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
        // SmartDashboard.putData("Heading PID", heading_pid);
    }

    @Override
    protected void initDefaultCommand() 
    {
        // Does nothing YETTTT
    }
    
    public boolean isHighGear()
    {
        return gearbox.get();
    }

    public void setGear(final boolean high)
    {
        gearbox.set(high);
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
        pigeon.setFusedHeading(0.0);
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

    private double[] ypr_deg = new double[3];

    /** @return Tilt angle. Positive: Front is up */
    public double getTilt()
    {
        final ErrorCode error = pigeon.getYawPitchRoll(ypr_deg);
        if (error == ErrorCode.OK)
            return ypr_deg[0];
        return 0.0;
    }
    
    private FusionStatus heading_state = new FusionStatus();

    /** @return Current heading in degrees */
    public double getHeading()
    {
        pigeon.getFusedHeading(heading_state);
        if (heading_state.bIsValid)
            return heading_state.heading;
        return 0.0;
    }

    private double[] xyz_dps = new double[3];

    /** @return Current turn rate in degrees per second */
    public double getTurnRate()
    {
        if (pigeon.getRawGyro(xyz_dps) == ErrorCode.OK)
            return xyz_dps[2];
        return 0.0;
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

    /** Counter to slow updates of position display */
    private int updates = 0;

    @Override
    public void periodic()
    {
        drive.arcadeDrive(speed, rotation, false);

        // 'periodic' runs 50 times per second (every 0.02 secs)
        // The 'getPosition()' call out to the CAN bus is slow,
        // so only do that once per second
        if (++updates > 50)
        {
            // SmartDashboard.putNumber("Left Encoder", left.getSelectedSensorPosition());
            // SmartDashboard.putNumber("RightEncoder", right.getSelectedSensorPosition());
            SmartDashboard.putNumber("Position", getPosition());
            SmartDashboard.putNumber("Heading", getHeading());
            SmartDashboard.putNumber("Tilt", getTilt());
            // SmartDashboard.putNumber("Turn Rate", getTurnRate());

            updates = 0;
        }
    }
}
