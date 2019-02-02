package robot.deepspace.grabber;

import edu.wpi.first.wpilibj.command.InstantCommand;

/** Command to toggle grabber open/close */
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
