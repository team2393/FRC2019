package robot.deepspace.lift;

import edu.wpi.first.wpilibj.command.Command;
import robot.deepspace.OI;

/** Command to move Lift with joystick */
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
        double joystick_reading = OI.getLiftUpDown();
        if (Math.abs(joystick_reading) > 0.1)
            lift.drive(joystick_reading);
        else
            lift.drive(0.0);        
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
