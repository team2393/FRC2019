package robot.deepspace.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import robot.util.OnTargetCheck;

/** Move drivetrain to a desired position */
public class MoveToPosition extends Command
{
    private final DriveTrain drivetrain;
    private final double inches;
    private final double heading;
    private final OnTargetCheck check = new OnTargetCheck(2, 0.5);

    /** Move to position, ignore heading 
     *  @param drivetrain
     *  @param inches
    */
    public MoveToPosition(final DriveTrain drivetrain, final double inches)
    {
        this(drivetrain, inches, Double.NaN);
    }

    /** Move to position while holding heading
     *  @param drivetrain
     *  @param inches
     *  @param heading
     */
    public MoveToPosition(final DriveTrain drivetrain, final double inches, final double heading)
    {
        this.drivetrain = drivetrain;
        this.inches = inches;
        this.heading = heading;
        setTimeout(10);
        requires(drivetrain);
    }

    @Override
    protected void execute() 
    {
        drivetrain.setPosition(inches);
        drivetrain.setHeading(heading);
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