package robot.deepspace.riser;

import edu.wpi.first.wpilibj.command.Command;
import robot.deepspace.OI;
import robot.deepspace.drivetrain.DriveTrain;

public class DropAll extends Command
{
    private final static double ABORT_ANGLE = 15;
    private final static double CONTROL_ANGLE = 3;
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
        abort = false;
        blipping = 0;
    }

    /** Rise up unless we're tilted too far, in which case we abort */
    private void rise_or_abort()
    {
        final double tilt = drive.getTilt();
        // Abort if tilted too far front or back
        if (Math.abs(tilt) > ABORT_ANGLE)
        {
            riser.dropBack(false);
            riser.dropFront(false);
            abort = true;
            return;
        }
        riser.dropBack(true);
        riser.dropFront(true);
    }

    /** Pause front when it's too high */
    private void control_front()
    {
        final double tilt = drive.getTilt();
        // Abort if tilted too far front or back
        if (Math.abs(tilt) > ABORT_ANGLE)
        {
            riser.dropBack(false);
            riser.dropFront(false);
            abort = true;
            return;
        }

        // Positive tilt angle, front is up too high
        if (tilt > CONTROL_ANGLE)
            riser.pauseFront();
        else
            riser.dropFront(true);
        riser.dropBack(true);
    }

    /** Rise up.
     *  Abort when tilted too far.
     *  If tilted a little, pull the 'high' front or back riser
     *  up for N periods, then check again.
     * 
     *  Determine N=1, 2, ... such that it just about pauses the rise.
     *  When blipping too long, it will pull the riser in too far
     *  and start a crazy oscillation.
     */
    private void blip_if_tilted()
    {
        final double tilt = drive.getTilt();

        // Abort if tilted too far
        if (Math.abs(tilt) > ABORT_ANGLE)
        {
            riser.dropBack(false);
            riser.dropFront(false);
            abort = true;
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
        if (tilt > CONTROL_ANGLE)
        {   // Stop front for one 
            riser.dropFront(false);
            blipping = 1;
        }
        else if (tilt < -CONTROL_ANGLE)
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
        // blip_if_tilted();
        // rise_or_abort();
        control_front();

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
