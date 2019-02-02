package robot.deepspace.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import robot.deepspace.OI;

/** Command to drive for/back with joysick but hold heading */
public class HeadingHoldJoydrive extends Command
{
    private final DriveTrain drivetrain;
    private double heading;

    public HeadingHoldJoydrive(final DriveTrain drivetrain)
    {
        this.drivetrain = drivetrain;
        requires(drivetrain);
    }

    @Override
    public void initialize()
    {
        // Lock onto current heading when command starts
        heading = drivetrain.getHeading();
    }

    @Override
    protected void execute()
    {
        drivetrain.setSpeed(OI.getSpeed());
        drivetrain.setHeading(heading);
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
        // When command is cancelled, stop the drivetrain and cancel PID
        drivetrain.setSpeed(0);
        drivetrain.setHeading(Double.NaN);
    }
}
