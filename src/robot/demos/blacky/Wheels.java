package robot.demos.blacky;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.parts.ContinuousRotationServo;

/** Subsystem for the wheels
 * 
 *  Two motors, methods to drive() and stop()
 */
public class Wheels extends Subsystem
{
    private SpeedController left_motor = new ContinuousRotationServo(RobotMap.PWM_LEFT);
    private SpeedController right_motor = new ContinuousRotationServo(RobotMap.PWM_RIGHT);
    private DifferentialDrive drive = new DifferentialDrive(left_motor, right_motor);

    @Override
    protected void initDefaultCommand()
    {
    }

    public void drive(final double speed, final double rotation)
    {
        drive.arcadeDrive(speed, rotation, false);
        SmartDashboard.putNumber("speed", speed);
        SmartDashboard.putNumber("rotation", rotation);
    }
    
    public void stop()
    {
        drive(0.0, 0.0);
        drive.stopMotor();
    }
}
