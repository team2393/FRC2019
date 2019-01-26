package robot.deepspace;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Grabber for the hatch panel
 *  with solenoid to open/close
 */
public class PanelGrabber 
{
    private final Solenoid grabber = new Solenoid(RobotMap.GRABBER_SOLENOID);
    // TODO Solenoid for pushing panel against rocket
    // TODO Sensor to tell if a hatch is on the grabber, should automtically close

    //TODO 2 motors for intake wheels 
    //TODO One sensor in intake, should stop motors once cargo is detected
    //TODO Eject motor

    public PanelGrabber() 
    {
        open(false);
    }

    public boolean isOpen()
    {
        return grabber.get();
    }

    /** Open/close
     *  @param is_open true to open, false to close
     */
    public void open(final boolean is_open)
    {
        grabber.set(is_open);

        SmartDashboard.putBoolean("Grabber Is Open", isOpen());
    }
}
