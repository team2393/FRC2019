package robot.deepspace;

import edu.wpi.first.wpilibj.command.InstantCommand;

/** Command to close grabber */
public class CloseGrabber extends InstantCommand
{
  private PanelGrabber grabber;

  public CloseGrabber(final PanelGrabber grabber)
  {
      this.grabber = grabber;
  }

  @Override
  protected void execute()
  {
      grabber.open(false);
  }
}
