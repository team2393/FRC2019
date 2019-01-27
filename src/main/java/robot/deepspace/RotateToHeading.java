package robot.deepspace;

import edu.wpi.first.wpilibj.command.Command;

/** Rotate drivetrain to a desired heading */
public class RotateToHeading extends Command
{
    private final DriveTrain drivetrain;
    private final double degrees;

    public RotateToHeading(final DriveTrain drivetrain, final double degrees)
    {
        this.drivetrain = drivetrain;
        this.degrees = degrees;
        requires(drivetrain);
    }

    @Override
    protected void execute() 
    {
        drivetrain.setHeading(degrees);
    }

    @Override
    protected boolean isFinished()
    {
        // Stop when we're close enough to desired heading
        final double close_enough = 2;
        return Math.abs(drivetrain.getHeading() - degrees)  <  close_enough;
    }

    @Override
    protected void end()
    {
        // Disable heading PID
        drivetrain.setHeading(Double.NaN);
    }
}