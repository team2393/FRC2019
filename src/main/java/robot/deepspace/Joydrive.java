package robot.deepspace;

import edu.wpi.first.wpilibj.command.Command;

/** Command to drive with joysick */
public class Joydrive extends Command
{
    private final DriveTrain drivetrain;

    public Joydrive(final DriveTrain drivetrain)
    {
        this.drivetrain = drivetrain;
        requires(drivetrain);
    }

    @Override
    protected void execute()
    {
        drivetrain.setSpeed(OI.getSpeed());
        drivetrain.setRotation(OI.getTurn());
    }

    @Override
    protected boolean isFinished()
    {
        // Keep running until cancelled
        return false;
    }

    @Override
    protected void end()
    {
        drivetrain.setSpeed(0);
        drivetrain.setRotation(0);
    }
}
