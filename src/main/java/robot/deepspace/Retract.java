package robot.deepspace;

import edu.wpi.first.wpilibj.command.InstantCommand;

/** Command to retract grabber */
public class Retract extends InstantCommand
{
    private final Grabber grabber;

    public Retract(final Grabber grabber)
    {
        this.grabber = grabber;
    }

    @Override
    protected void execute()
    {
        grabber.extend(false);
    }
}
