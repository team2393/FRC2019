package robot.deepspace;

import edu.wpi.first.wpilibj.Solenoid;

/** Grabber for the hatch panel
 *  with solenoid to open/close
 */
public class PanelGrabber 
{
    private final Solenoid grabber = new Solenoid(RobotMap.GRABBER_SOLENOID);

    /** Open/close
     *  @param is_open true to open, false to close
     */
    public void open(final boolean is_open)
    {
        grabber.set(is_open);
    }
}
