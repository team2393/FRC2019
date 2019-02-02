package robot.deepspace.grabber;

import edu.wpi.first.wpilibj.command.InstantCommand;

/** Command to open grabber */
public class OpenGrabber extends InstantCommand
{
    private final Grabber grabber;

    public OpenGrabber(final Grabber grabber)
    {
        // Would be shorter with lambda:
        //     super(() -> grabber.open(true));
        this.grabber = grabber;
    }

    @Override
    protected void execute()
    {
        grabber.open(true);
    }
}
