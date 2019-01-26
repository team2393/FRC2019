package robot.deepspace;

import edu.wpi.first.wpilibj.command.InstantCommand;

/** Command to close grabber */
public class CloseGrabber extends InstantCommand
{
  private Grabber grabber;

  public CloseGrabber(final Grabber grabber)
  {
      this.grabber = grabber;
  }

  @Override
  protected void execute()
  {
      grabber.open(false);
  }
}
