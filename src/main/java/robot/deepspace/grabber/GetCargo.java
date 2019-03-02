package robot.deepspace.grabber;

import edu.wpi.first.wpilibj.command.Command;

public class GetCargo extends Command
{
    private Grabber grabber;

    public GetCargo(Grabber grabber) 
    {
        requires(grabber);
        this.grabber = grabber;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() 
    {
        if (grabber.isCargoDetected())
            grabber.setSpinnerSpeed(0);
        else grabber.setSpinnerSpeed(0.3);
    }


    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() 
    {
      return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end()
    {
      grabber.setSpinnerSpeed(0);
    }
}
