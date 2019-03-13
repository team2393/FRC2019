package robot.deepspace.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

/** Move drivetrain to a desired position */
public class MoveToPosition extends Command
{
    private final DriveTrain drivetrain;
    private final double inches;
    private final double heading;
    private final boolean high_speed;

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
        this(drivetrain, inches, heading, true);
    }

    /** Move to position while holding heading
     *  @param drivetrain
     *  @param inches
     *  @param heading
     *  @param high_speed
     */
    public MoveToPosition(final DriveTrain drivetrain, final double inches, final double heading, final boolean high_speed)
    {
        this.drivetrain = drivetrain;
        this.inches = inches;
        this.heading = heading;
        this.high_speed = high_speed;
        setTimeout(10);
        requires(drivetrain);
    }

    @Override
    protected void initialize()
    {
        drivetrain.setPositionSpeed(high_speed);
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
       // return check.isFinished(inches, drivetrain.getPosition());
        return Math.abs(inches-drivetrain.getPosition()) < 2.5 && 
               drivetrain.getSpeed() < 0.2;
    }

    @Override
    protected void end()
    {
        // Assert default
        drivetrain.setPositionSpeed(true);

        // Disable position PID
        drivetrain.setPosition(Double.NaN);
        drivetrain.setHeading(Double.NaN);
    }
}