package robot.deepspace.riser;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import robot.deepspace.RobotMap;

/** Riser, Push-up mechanism
 * 
 *  Idea:
 *  Command to lower 2 front and 1 back cylinder,
 *  and now bottom drive will move with other wheels forward/backward.
 *  Next command raises 2 front cylinders back up, bottom drive still follows main wheels.
 *  Finally, raise back cylinder, bottom drive off.
 */
public class Riser
{
    private final Solenoid front_riser = new Solenoid(RobotMap.FRONT_RISER_SOLENOID);
    private final Solenoid back_riser = new Solenoid(RobotMap.BACK_RISER_SOLENOID);
    private final Victor drive = new Victor(RobotMap.RISER_MOTOR);

    public void dropFront(boolean down)
    {
        front_riser.set(down);
    }

    public void dropBack(boolean down)
    {
        back_riser.set(down);
    }

    public void setSpeed(double speed)
    {
        drive.set(speed);
    }
}
