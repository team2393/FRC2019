package robot.deepspace.riser;

import edu.wpi.first.wpilibj.command.Command;
import robot.deepspace.OI;
import robot.deepspace.drivetrain.DriveTrain;

public class DropAll extends Command
{
    private final Riser riser;
    private final DriveTrain drive;
    private boolean abort = false;
    private int blipping = 0;
    
    public DropAll(final Riser riser, final DriveTrain drive) 
    { 
        requires(riser);
        this.riser = riser;
        this.drive = drive;
    }

    @Override
    protected void initialize()
    {
        OI.forward_only = true;
        abort = false;
        blipping = 0;
    }

    /** Rise up unless we're tilted too far, in which case we abort */
    private void rise_or_abort()
    {
        final double tilt = drive.getTilt();
        // Abort if tilted too far front or back
        if (Math.abs(tilt) > 10)
        {
            riser.dropBack(false);
            riser.dropFront(false);
            abort = true;
            OI.forward_only = false;
            return;
        }
        riser.dropBack(true);
        riser.dropFront(true);
    }

    /** Rise up.
     *  Abort when tilted too far.
     *  If tilted a little, pull the 'high' front or back riser
     *  up for N periods, then check again.
     * 
     *  TODO Check if this helps.
     *  Determine N=1, 2, ... such that it just about pauses the rise.
     *  When blipping too long, it will pull the riser in too far
     *  and start a crazy oscillation.
     */
    private void blip_if_tilted()
    {
        final double tilt = drive.getTilt();

        // Abort if tilted too far
        if (Math.abs(tilt) > 20)
        {
            riser.dropBack(false);
            riser.dropFront(false);
            abort = true;
            OI.forward_only = false;
            return;
        }

        // Last time around, did we blip the front or back for N periods?
        if (blipping > 0)
        {
            --blipping;
            if (blipping <= 0)
            {   // Back to rising both for one period, don't check tilt, wait for next reading
                riser.dropBack(true);
                riser.dropFront(true);
            }
            return;
        }
        // Not blipping. Check if we should.
        // Positive tilt angle: Front is up.
        if (tilt > 12)
        {   // Stop front for one 
            riser.dropFront(false);
            blipping = 1;
        }
        else if (tilt < -12)
        {
            riser.dropBack(false);
            blipping = 1;
        }
        else
        {
            riser.dropBack(true);
            riser.dropFront(true);
        }
    }

    @Override
    protected void execute() 
    {
        // Pick one of the next:
        blip_if_tilted();
        // rise_or_abort();

        double joystick_reading = OI.getSpeed();
        if (Math.abs(joystick_reading) > 0.1)
            riser.setSpeed(joystick_reading);
        else
            riser.setSpeed(0);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished()
    {
        return abort;
    }
}
