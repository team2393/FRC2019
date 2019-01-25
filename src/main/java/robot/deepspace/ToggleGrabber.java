package robot.deepspace;

import edu.wpi.first.wpilibj.command.InstantCommand;

/** Command to open grabber */
public class ToggleGrabber extends InstantCommand
{
    private final PanelGrabber grabber;

    public ToggleGrabber(final PanelGrabber grabber)
    {
        this.grabber = grabber;
    }

    @Override
    protected void execute()
    {
        if (grabber.isOpen())
        {
            grabber.open(false);
        }

        else
        {
            grabber.open(true);
        }
    }
}
