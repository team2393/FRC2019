package robot.deepspace.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import robot.util.OnTargetCheck;

/** Rotate drivetrain to a desired heading */
public class RotateToHeading extends Command
{
    private final DriveTrain drivetrain;
    private final double degrees;
    
    public RotateToHeading(final DriveTrain drivetrain, final double degrees)
    {
        this.drivetrain = drivetrain;
        this.degrees = degrees;
        setTimeout(10.0);
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
        // Give up after timeout
        if (isTimedOut())
            return true;
        // Close enough to desired heading and no longer turning?
        return Math.abs(degrees - drivetrain.getHeading()) < 2  &&
               drivetrain.getTurnRate() < 5.0;
    }

    @Override
    protected void end()
    {
        // Disable heading PID
        drivetrain.setHeading(Double.NaN);
    }
}