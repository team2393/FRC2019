package robot.deepspace.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

/** Rotate drivetrain to a desired heading */
public class RotateToHeading extends Command
{
    private final DriveTrain drivetrain;
    private final double degrees;
    private long ms_since_ok = 0;

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
        // Close enough to desired heading?
        final double close_enough = 2;
        if (Math.abs(drivetrain.getHeading() - degrees)  <  close_enough)
        {
            // How long have we been at the desired heading?
            final long now = System.currentTimeMillis();
            if (ms_since_ok == 0)
                ms_since_ok = now;
            else
                if (now > ms_since_ok + 1000)
                    return true;
        }
        else
            ms_since_ok = 0;
        return false;
    }

    @Override
    protected void end()
    {
        // Disable heading PID
        drivetrain.setHeading(Double.NaN);
    }
}