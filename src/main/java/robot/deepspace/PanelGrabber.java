package robot.deepspace;

import edu.wpi.first.wpilibj.Solenoid;

/** Grabber for the hatch panel has solenoid
 */
public class PanelGrabber 
{
    private final Solenoid grabber = new Solenoid(RobotMap.GRABBER_SOLENOID);

    public void open(boolean is_open)
    {
        grabber.set(is_open);
    }
}
