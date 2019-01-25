package robot.demos.subsystems;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Subsystem for a drive
 *
 *  Wraps a {@link DifferentialDrive} as a {@link Subsystem}
 *  to allow use with {@link Command}s.
 *
 *  Remembers the last requested speed and rotation,
 *  keeps sending them to drive to satisfy MotorSafety.
 *  This allows for example one command to control the speed,
 *  while another command controls the rotation.
 */
public class DriveSubsystem extends Subsystem
{
    private final DifferentialDrive drive;
    private double speed = 0.0, rotation = 0.0;

    public DriveSubsystem(final DifferentialDrive drive)
    {
        this.drive = drive;
    }

    @Override
    protected void initDefaultCommand()
    {
    }

    public double getSpeed()
    {
        return speed;
    }

    public double getTurn()
    {
        return rotation;
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
