package meetups;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.demos.subsystems.DriveSubsystem;

public class EncoderMoveCommand extends Command
{
    private final DriveSubsystem drive;
    private final Encoder encoder;
    private final double position;

    /** Command that moves to desired position
     *  @param drive Motors
     *  @param encoder Encoder that tells us about current position
     *  @param position Desired position
     */
    public EncoderMoveCommand(DriveSubsystem drive, Encoder encoder, double position)
    {
        this.drive = drive;
        this.encoder = encoder;
        this.position = position;

        // Allow setting the gain on dashboard
        SmartDashboard.setDefaultNumber("gain", 0.02);
    }

    @Override
    protected void execute()
    {
        // Compute drive speed based on encoder and desired position
        // based on error and proportional gain
        double actual = encoder.getDistance();
        double gain = SmartDashboard.getNumber("gain", 0.02);
        double error = actual - position;
        drive.move(- error * gain);
    }

    @Override
    protected boolean isFinished()
    {
        // What should we return here?
        // Could return true once we're close enough to the desired location.
        // But we return false so we continue to adjust the position,
        // in case somebody bumps the robot we'll always move back.
        return false;
    }

    @Override
    protected void end()
    {
        // Turn motors off
        drive.stop();
    }
}
