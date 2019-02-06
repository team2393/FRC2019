package robot.deepspace.grabber;

import edu.wpi.first.wpilibj.command.Command;

/** Command to wait for grabber to detect hatch */
public class WaitForHatch extends Command
{
    private final Grabber grabber;

    public WaitForHatch(final Grabber grabber)
    {
        requires(grabber);
        this.grabber = grabber;
    }

    @Override
    protected boolean isFinished()
    {
        return grabber.isHatchDetected();
    }
}
