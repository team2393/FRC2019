package robot.deepspace;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import robot.parts.PDPController;

/** Command to drive Lift with joystick */
public class DriveLift extends Command 
{
    private final Lift lift;

    public DriveLift(final Lift lift)
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
        lift.drive(0);
    }
}
