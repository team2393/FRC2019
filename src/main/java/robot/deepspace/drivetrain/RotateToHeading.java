package robot.deepspace.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import robot.util.OnTargetCheck;

/** Rotate drivetrain to a desired heading */
public class RotateToHeading extends Command
{
    private final DriveTrain drivetrain;
    private final double degrees;
    private final OnTargetCheck check = new OnTargetCheck(2.0, 1.0);
    
    public RotateToHeading(final DriveTrain drivetrain, final double degrees)
    {
        this.drivetrain = drivetrain;
        this.degrees = degrees;
        requires(drivetrain);
    }

    @Override
    protected void initialize()
    {
        check.reset();
    }

    @Override
    protected void execute() 
    {
        drivetrain.setHeading(degrees);
    }

    @Override
    protected boolean isFinished()
    {
        // Close enough to desired heading?
        // TODO Could also use a combination of
        //      gyro.getAngle()     - close to desired position, as done now
        //      and gyro.getRate()  - still turning??
        return check.isFinished(degrees, drivetrain.getHeading());
    }

    @Override
    protected void end()
    {
        // Disable heading PID
        drivetrain.setHeading(Double.NaN);
    }
}