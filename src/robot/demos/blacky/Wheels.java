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
    private double speed = 0.0, rotation = 0.0;
    
    public Wheels()
    {
        left_motor.setInverted(true);
        right_motor.setInverted(true);
    }
    
    @Override
    protected void initDefaultCommand()
    {
    }

    // Update the speed & rotation which periodic() will then send to drive
    /** @param speed Forward (0..1) or backward (-1..0) speed */
    public void move(final double speed)
    {
        this.speed = speed;
    }

    /** @param rotation Rotation, -1 (left) .. 1 (right)  */
    public void turn(final double rotation)
    {
        this.rotation = rotation;
    }
    
    /** @param speed Forward (0..1) or backward (-1..0) speed
     *  @param rotation Rotation, -1 (left) .. 1 (right)
     */
    public void drive(final double speed, final double rotation)
    {
        move(speed);
        turn(rotation);
    }
    
    // Called by Scheduler all the time
    @Override
    public void periodic()
    {
        // Update the drive (which will otherwise stop because of MotorSafety)
        drive.arcadeDrive(speed, rotation, false);
        // Display speed and rotation
        SmartDashboard.putNumber("speed", speed);
        SmartDashboard.putNumber("rotation", rotation);
    }

    public void stop()
    {
        drive(0.0, 0.0);
        drive.stopMotor();
    }
}
