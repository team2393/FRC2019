package robot.deepspace;

import edu.wpi.first.wpilibj.command.InstantCommand;

/** Command to drive with joysick */
public class Joydrive extends InstantCommand
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
    protected void end()
    {
        drivetrain.setSpeed(0);
        drivetrain.setRotation(0);
    }
}
