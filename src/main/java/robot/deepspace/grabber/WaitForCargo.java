package robot.deepspace.grabber;

import edu.wpi.first.wpilibj.command.Command;

/** Command to wait for grabber to detect cargo */
public class WaitForCargo extends Command
{
    private final Grabber grabber;

    public WaitForCargo(final Grabber grabber)
    {
        requires(grabber);
        this.grabber = grabber;
    }

    @Override
    protected boolean isFinished()
    {
        return grabber.isCargoDetected();
    }
}
