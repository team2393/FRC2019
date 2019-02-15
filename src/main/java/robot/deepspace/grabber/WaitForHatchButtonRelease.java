package robot.deepspace.grabber;

import edu.wpi.first.wpilibj.command.Command;
import robot.deepspace.OI;

/** Command to wait until the 'get' button is released
 *  Meant to hold on to the hatch until the button is released
 */
public class WaitForHatchButtonRelease extends Command
{
    @Override
    protected boolean isFinished()
    {
        return OI.isReleaseButtonReleased();
    }
}
