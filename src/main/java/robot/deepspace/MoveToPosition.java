package robot.deepspace;

import edu.wpi.first.wpilibj.command.Command;

/** Move drivetrain to a desired position */
public class MoveToPosition extends Command
{
    private final DriveTrain drivetrain;
    private final double inches;

    public MoveToPosition(final DriveTrain drivetrain, final double inches)
    {
        this.drivetrain = drivetrain;
        this.inches = inches;
        requires(drivetrain);
    }

    @Override
    protected void execute() 
    {
        drivetrain.setPosition(inches);
    }

    @Override
    protected boolean isFinished()
    {
        // Stop when we're close enough to desired position
        final double close_enough = 2;
        return Math.abs(drivetrain.getPosition() - inches) < close_enough;
    }

    @Override
    protected void end()
    {
        // Disable position PID
        drivetrain.setPosition(Double.NaN);
    }
}