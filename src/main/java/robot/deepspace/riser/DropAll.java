package robot.deepspace.riser;

import edu.wpi.first.wpilibj.command.Command;
import robot.deepspace.OI;
import robot.deepspace.drivetrain.DriveTrain;

public class DropAll extends Command
{
    private final Riser riser;
    private final DriveTrain drive;

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
    }

    @Override
    protected void execute() 
    {
        final double tilt = drive.getTilt();
        
        // Positive tilt angle: Front is up.
        // IF positive angle is too large, then front cylinders must be turned off
        // if the negative angle is too large, the back cylinder must be turned off
        riser.dropFront(tilt < 15);
        riser.dropBack(tilt > -15);

        // riser.dropBack(true);
        // riser.dropFront(true);

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
        return false;
    }
}
