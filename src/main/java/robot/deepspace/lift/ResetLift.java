package robot.deepspace.lift;

import edu.wpi.first.wpilibj.command.InstantCommand;

/** Reset Lift Position */
public class ResetLift extends InstantCommand
{
    private final Lift lift;

    public ResetLift(final Lift lift)
    {
        this.lift = lift;
        requires(lift);
    }

    @Override
    protected void execute() 
    {
    lift.resetLift();     
    }
}
