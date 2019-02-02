package robot.deepspace;

import edu.wpi.first.wpilibj.command.Command;

/** Home the lift: Drive down until hitting swtich,
 *  which resets the encoder to zero
 */
public class HomeLift extends Command
{
    private final Lift lift;
    private boolean done = false;

    public HomeLift(Lift lift)
    {
        this.lift = lift;
        requires(lift);
    }

    @Override
    protected void execute()
    {
        // Drive down, slowly.
        // If that returns false, i.e. it didn't move further
        // because we hit the limit switch, we're done
        done = ! lift.drive(-0.1);
    }

    @Override
    protected boolean isFinished()
    {
        return done;
    }

    @Override
    protected void end() 
    {
        // Stop movement
        lift.drive(0);
    }
}
