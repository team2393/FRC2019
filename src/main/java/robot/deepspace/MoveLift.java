package robot.deepspace;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MoveLift extends Command
{
  private final String name;
  private final Lift lift;
  private final double height;

  public MoveLift(String name, Lift lift, double height)
  {
    this.name = name;
    this.lift = lift;
    this.height = height;
    requires(lift);

    SmartDashboard.setDefaultNumber(name, height);
  }

  @Override
  protected void execute()
  {
    lift.setheight(SmartDashboard.getNumber(name, height));
  }

  @Override
  protected boolean isFinished()
  {
    return false;
  }
}
