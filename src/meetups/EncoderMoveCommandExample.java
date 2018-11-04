package meetups;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import robot.subsystems.DriveSubsystem;

public class EncoderMoveCommandExample extends Command
{
    private final DriveSubsystem drive;
    private final Encoder encoder;
    private final double position;

    /** Command that moves to desired position
     *  @param drive Motors
     *  @param encoder Encoder that tells us about current position
     *  @param position Desired position
     */
    public EncoderMoveCommandExample(DriveSubsystem drive, Encoder encoder, double position)
    {
        this.drive = drive;
        this.encoder = encoder;
        this.position = position;
    }

    @Override
    protected void execute()
    {
        double error = position - encoder.getDistance();
        drive.move(0.01 * error);
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        drive.stop();
    }
}
