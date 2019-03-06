package robot.deepspace.riser;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.deepspace.OI;

public class DropAll extends Command
{
    private final Riser riser;
    private final Accelerometer tilty;
    private double avg_tilt = 0.0;

    public DropAll(Riser riser, Accelerometer tilty) 
    { 
        requires(riser);
        this.riser = riser;
        this.tilty = tilty;
    }

    @Override
    protected void initialize()
    {
        OI.forward_only = true;
    }

    @Override
    protected void execute() 
    {
        // System.out.format("X: %.3f Y: %.3f Z: %.3f\n",  tilty.getX(), tilty.getY(), tilty.getZ());
        double tilt = Math.toDegrees(Math.atan2(tilty.getY(), tilty.getZ()));
        if (Math.abs(tilt) > 30)
            tilt = 0;
        avg_tilt = avg_tilt*0.5 + tilt*0.5;
        SmartDashboard.putNumber("Average Tilt", avg_tilt);                                                                                                                                                                                                                                             

        // Positive tilt angle: Front is up.
        // IF positive angle is too large, then front cylinders must be turned off
        // if the negative angle is too large, the back cylinder must be turned off
        // riser.dropFront(avg_tilt < 10);
        // riser.dropBack(avg_tilt > -10);

        riser.dropBack(true);
        riser.dropFront(true);

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
