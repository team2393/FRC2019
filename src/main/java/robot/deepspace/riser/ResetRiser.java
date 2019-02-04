package robot.deepspace.riser;

import edu.wpi.first.wpilibj.command.Command;

public class ResetRiser extends Command
{
    private final Riser riser;

    public ResetRiser(Riser riser) 
    { 
        this.riser = riser;
    }

    @Override
    protected void execute() 
    {
        riser.dropBack(false);
        riser.dropFront(false);
        riser.setSpeed(0);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished()
    {
        return true;
    }
}
