package robot.deepspace.grabber;

import edu.wpi.first.wpilibj.command.Command;

/** Command to wait for grabber to detect hatch */
public class WaitForCargo extends Command
{
    private final Grabber grabber;
    private boolean done;

    public WaitForCargo(final Grabber grabber)
    {
        this.grabber = grabber;
    }

    @Override
    protected void execute()
    {
        done = grabber.isCargoDetected();
    }

    @Override
    protected boolean isFinished()
    {
        return done;
    }
}
