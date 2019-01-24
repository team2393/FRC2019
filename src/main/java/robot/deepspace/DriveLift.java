package robot.deepspace;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import robot.parts.PDPController;

public class DriveLift extends Command 
{
  private final Joystick joystick;
  private final Lift lift;

  public DriveLift(Joystick joystick, Lift lift)
  {
      this.joystick = joystick;
      this.lift = lift;
      requires(lift);
  }

  @Override
  protected void execute() 
  {
      lift.drive(- joystick.getRawAxis(PDPController.LEFT_STICK_VERTICAL));
  }

  @Override
  protected boolean isFinished()
  {
    // Keep going until command is cancelled
    return false;
  }

  @Override
  protected void end() 
  {
      lift.drive(0);
  }
}
