package robot.deepspace.grabber;

import edu.wpi.first.wpilibj.command.InstantCommand;

/** Command to close grabber */
public class SetSpinnerSpeed extends InstantCommand
{
    private Grabber grabber;
    private double speed;

  public SetSpinnerSpeed(final Grabber grabber, double speed)
  {
      this.grabber = grabber;
      this.speed = speed;
  }

  @Override
  protected void execute()
  {
      grabber.setSpinnerSpeed(speed);
  }
}
