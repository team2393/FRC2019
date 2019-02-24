package robot.deepspace.lift;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

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
    // Measure how many inches lift moves per count
    private final double COUNTS_PER_INCH = 16127.0/(47.5-16);
    
    private final TalonSRX motor = new TalonSRX(RobotMap.LIFT_MOTOR);

    public Lift()
    {
        // Basic motor configuration
        motor.configFactoryDefault();
        motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

        motor.setNeutralMode(NeutralMode.Brake);

        motor.setInverted(true);
         // Does the sensor rotate in opposite direction compared to motor?
        motor.setSensorPhase(true);

        // Configure PID
        motor.config_kP(0, 0.2);
        motor.config_kI(0, 0.05);
        motor.config_kD(0, 0.1);
        motor.config_IntegralZone(0, 2000);
        motor.configMaxIntegralAccumulator(0, 2000);

        // Configure Motion Magic
        int max_speed = 1400;
        motor.config_kF(0, 1023.0/max_speed);
        // Set max speed to about 75% of max speed in tics/100ms
        motor.configMotionCruiseVelocity(max_speed*3/4);
        // Accelerate twice as fast to reach cruise velocity in half a second
        motor.configMotionAcceleration(2*max_speed*3/4);
        
        // Slow down
        motor.configClosedloopRamp(0.25);
        motor.configClosedLoopPeakOutput(0, 0.6);
        // motor.configContinuousCurrentLimit(40);
		// motor.configPeakCurrentLimit(60);
		// motor.configPeakCurrentDuration(100);
        // motor.enableCurrentLimit(true);

        // TODO Use max of 11.5V, so behaves same with full/weaker battery 
        // motor.configVoltageCompSaturation(11.5);
        // motor.enableVoltageCompensation(true);

        resetLift();
    }

    @Override
    protected void initDefaultCommand()
    {
        // We have no default command, yet
    }

    @Override
    public void periodic() 
    {
        final int pos = motor.getSelectedSensorPosition();
        // SmartDashboard.putNumber("Lift Position", pos);
        SmartDashboard.putNumber("Lift Height (in)", pos / COUNTS_PER_INCH);
    }

    /** Zero lift position encoder */
    public void resetLift()
    {
        motor.setSelectedSensorPosition(0);
    }

    /** Drive the lift up and down
     *  @param speed -1 to 1, positive is up
     *  @return true if OK, false if we bottomed out
     */
    public boolean drive(double speed)
    {
        final boolean at_limit = motor.getSelectedSensorPosition() <= 0;
        if (at_limit)
        {
            // Prohibit moving further down by setting motor to 0
            if (speed < 0)
                speed = 0.0;
        }
        motor.set(ControlMode.PercentOutput, speed);
        
        return ! at_limit;
    }
    
    /** @param position Desired position in encoder counts */
    public void setPosition(final double position)
    {
        // To Tune PID, use
        // motor.set(ControlMode.Position, position);
        motor.set(ControlMode.MotionMagic, position);
    }
    
    /** @param inches Desired position */
    public void setHeight(final double inches)
    {
        setPosition(inches * COUNTS_PER_INCH);
    }
}
