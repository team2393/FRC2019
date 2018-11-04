package meetups;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import robot.subsystems.DriveSubsystem;

public class EncoderMoveCommand extends Command
{
    private DriveSubsystem drive;
    private Encoder encoder;
    private double position;

    public EncoderMoveCommand(DriveSubsystem drive, Encoder encoder, double position)
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
