package robot.parts;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.hal.HALUtil;

/** Support for the 'USER' Button on the roboRIO.
 * 
 *  <p>
 *  Example Usage:
 *  
 *  <pre>
 *  Command command = new SomeCommand();
 *  Button user = new USERButton();
 *  // Start command when button is pressed,
 *  // stop command when button is released
 *  user.whenActive(command);
 *  </pre>
 */
public class USERButton extends Button
{
    /** @return <code>true</code> when the 'USER' button is pressed, otherwise <code>false</code> */
    public static boolean isPressed()
    {
        // It's a little hard to guess that this is the way to read the 'USER' button,
        // which is why this USERButton class was created
        return HALUtil.getFPGAButton();
    }

    @Override
    public boolean get()
    {
        return isPressed();
    }
}
