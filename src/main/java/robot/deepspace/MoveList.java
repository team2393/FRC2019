package robot.deepspace;

import edu.wpi.first.wpilibj.command.Command;

/** Command to move Lift with joystick */
public class MoveList extends Command 
{
    private final Lift lift;

    public MoveList(final Lift lift)
    {
        this.lift = lift;
        requires(lift);
    }

    @Override
    protected void execute() 
    {
        lift.drive(OI.getLiftUpDown());
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
        // Stop movement
        lift.drive(0);
    }
}
