package robot.deepspace.riser;

import edu.wpi.first.wpilibj.command.Command;
import robot.deepspace.OI;

public class DropAll extends Command
{
    private final Riser riser;

    public DropAll(Riser riser) 
    { 
        this.riser = riser;
    }

    @Override
    protected void execute() 
    {
        riser.dropBack(true);
        riser.dropFront(true);
        riser.setSpeed(OI.getSpeed());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished()
    {
        return false;
    }
}
