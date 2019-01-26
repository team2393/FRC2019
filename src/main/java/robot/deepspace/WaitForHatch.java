package robot.deepspace;

import edu.wpi.first.wpilibj.command.Command;

/** Command to wait for grabber to detect hatch */
public class WaitForHatch extends Command
{
    private final Grabber grabber;
    private boolean done;

    public WaitForHatch(final Grabber grabber)
    {
        this.grabber = grabber;
    }

    @Override
    protected void execute()
    {
        done = grabber.isHatchDetected();
    }

    @Override
    protected boolean isFinished()
    {
        return done;
    }
}
