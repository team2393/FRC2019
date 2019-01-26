package robot.deepspace;

import edu.wpi.first.wpilibj.command.InstantCommand;

/** Command to open grabber */
public class OpenGrabber extends InstantCommand
{
    private final Grabber grabber;

    public OpenGrabber(final Grabber grabber)
    {
        this.grabber = grabber;
    }

    @Override
    protected void execute()
    {
        grabber.open(true);
    }
}
