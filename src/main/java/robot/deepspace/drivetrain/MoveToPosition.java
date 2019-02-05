package robot.deepspace.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import robot.util.OnTargetCheck;

/** Move drivetrain to a desired position */
public class MoveToPosition extends Command
{
    private final DriveTrain drivetrain;
    private final double inches;
    private final OnTargetCheck check = new OnTargetCheck(2, 0.5);

    // TODO Hold heading?

    public MoveToPosition(final DriveTrain drivetrain, final double inches)
    {
        this.drivetrain = drivetrain;
        this.inches = inches;
        setTimeout(10);
        requires(drivetrain);
    }

    @Override
    protected void initialize()
    {
        // TODO Store heading        drivetrain.getHeading();
    }

    @Override
    protected void execute() 
    {
        drivetrain.setPosition(inches);
        // drivetrain.setHeading(heading);
    }

    @Override
    protected boolean isFinished()
    {
        if (isTimedOut())
            return true;
        return check.isFinished(inches, drivetrain.getPosition());
    }

    @Override
    protected void end()
    {
        // Disable position PID
        drivetrain.setPosition(Double.NaN);
        drivetrain.setHeading(Double.NaN);
    }
}