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
    @Override
    public boolean get()
    {
        return RobotController.getUserButton();
    }
}
