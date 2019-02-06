package robot.deepspace.riser;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class ResetRiser extends InstantCommand
{
    private final Riser riser;

    public ResetRiser(Riser riser) 
    { 
        requires(riser);
        this.riser = riser;
    }

    @Override
    protected void execute() 
    {
        riser.dropBack(false);
        riser.dropFront(false);
        riser.setSpeed(0);
    }
}
