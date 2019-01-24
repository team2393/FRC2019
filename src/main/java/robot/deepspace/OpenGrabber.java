package robot.deepspace;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Add your docs here.
 */
public class OpenGrabber extends InstantCommand
{
  private PanelGrabber grabber;

  public OpenGrabber(PanelGrabber grabber)
  {
    this.grabber = grabber;
  }

  @Override
  protected void execute()
  {
    grabber.open(true);
  }
}
