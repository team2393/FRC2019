package robot.deepspace.lift;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.deepspace.RobotMap;

/** This class handles our lift
 * 
 *  Motor to drive up and down,
 *  encoder to know about position,
 *  limit switch at lower end to calibrate encoder,
 *  PID to move to specific position.
 */
public class Lift extends Subsystem
{
    // TODO Measure how many inches lift moves per count
    private final double COUNTS_PER_INCH = 4096.0;
    private final TalonSRX motor = new TalonSRX(RobotMap.LIFT_MOTOR);
    private final DigitalInput limit_switch = new DigitalInput(RobotMap.LIMIT_SWITCH);

    public Lift()
    {
        // Basic motor configuration
        motor.configFactoryDefault();
        motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        // TODO Depending on the actual lift mechanics, might need to invert
        motor.setInverted(false);
         // Does the sensor rotate in opposite direction compared to motor?
        motor.setSensorPhase(true);

        // Configure PID
        motor.config_kP(0, 0.5);
        motor.config_kI(0, 0.01);
        motor.config_kD(0, 5.0);
        motor.config_IntegralZone(0, 5000);
        motor.configMaxIntegralAccumulator(0, 5000);

        // Configure Motion Magic
        int max_speed = 17200;
        motor.config_kF(0, 1023.0/max_speed);
        // Set max speed to about 75% of max speed in tics/100ms
        motor.configMotionCruiseVelocity(max_speed*3/4);
        // Accelerate twice as fast to reach cruise velocity in half a second
        motor.configMotionAcceleration(2*max_speed*3/4);
        
        // TODO Slow down?
        // motor.configOpenloopRamp(0.15);
        // motor.configClosedloopRamp(0.15);
        // motor.configContinuousCurrentLimit(40);
		// motor.configPeakCurrentLimit(60);
		// motor.configPeakCurrentDuration(100);
        // motor.enableCurrentLimit(true);
    }

    @Override
    protected void initDefaultCommand()
    {
        // We have no default command, yet
    }

    /** Drive the lift up and down
     *  @param speed -1 to 1, positive is up
     *  @return true if OK, false if we hit the limit switch
     */
    public boolean drive(double speed)
    {
        boolean at_limit = limit_switch.get();
        if (at_limit)
        {
            // Reset position when hitting limit
            motor.setSelectedSensorPosition(0);
            // Prohibit moving down by setting motor to 0 once switch is hit
            if (speed < 0)
                speed = 0.0;
        }
        motor.set(ControlMode.PercentOutput, speed);
 
        SmartDashboard.putNumber("Lift Position", motor.getSelectedSensorPosition());

        return ! at_limit;
    }

    /** @param position Desired position in encoder counts */
    public void setPosition(final double position)
    {
        // To Tune PID, use
        // motor.set(ControlMode.Position, position);
        motor.set(ControlMode.MotionMagic, position);
        int pos = motor.getSelectedSensorPosition();
        SmartDashboard.putNumber("Lift Position", pos);
        SmartDashboard.putNumber("Lift Pos Error", motor.getClosedLoopError());
        SmartDashboard.putNumber("Lift Height", pos / COUNTS_PER_INCH);
    }

    /** @param inches Desired position */
    public void setHeight(final double inches)
    {
         setPosition(inches * COUNTS_PER_INCH);
    }
}
