package robot.deepspace.riser;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import robot.deepspace.RobotMap;

/** Riser, Push-up mechanism
 * 
 *  Idea:
 *  Command to lower 2 front and 1 back cylinder,
 *  and now bottom drive will move with other wheels forward/backward.
 *  Next command raises 2 front cylinders back up, bottom drive still follows main wheels.
 *  Finally, raise back cylinder, bottom drive off.
 */
public class Riser extends Subsystem
{
    private final Solenoid front_riser1 = new Solenoid(RobotMap.RISER_PCM, RobotMap.FRONT_RISER_SOLENOID1);
    private final Solenoid front_riser2 = new Solenoid(RobotMap.RISER_PCM, RobotMap.FRONT_RISER_SOLENOID2);
    private final Solenoid back_riser = new Solenoid(RobotMap.RISER_PCM, RobotMap.BACK_RISER_SOLENOID);
    private final Victor drive = new Victor(RobotMap.RISER_MOTOR);

    @Override
    protected void initDefaultCommand()
    {
        // Doesn't Do Anything
    }

    public void dropFront(boolean down)
    {
        front_riser1.set(down);
        front_riser2.set(down);
    }

    public void pauseFront()
    {
        // Assuming sol2 is the one that pauses air to the front
        front_riser2.set(false);
    }

    public void dropBack(boolean down)
    {
        back_riser.set(down);
    }

    public boolean isBackDown()
    {
        return back_riser.get();
    }

    public void setSpeed(double speed)
    {
        //Sets riser motor to opposite speed
        drive.set(-speed);
    }
}
