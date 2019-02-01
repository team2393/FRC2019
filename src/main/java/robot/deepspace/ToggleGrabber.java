package robot.deepspace;

import edu.wpi.first.wpilibj.command.InstantCommand;

/** Command to open grabber */
public class ToggleGrabber extends InstantCommand
{
    private final Grabber grabber;

    public ToggleGrabber(final Grabber grabber)
    {
        this.grabber = grabber;
    }

    @Override
    protected void execute()
    {
        grabber.open(! grabber.isOpen());
    }
}
