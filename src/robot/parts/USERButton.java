package robot.parts;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.RobotController;

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
        return RobotController.getUserButton();
    }

    @Override
    public boolean get()
    {
        return isPressed();
    }
}
