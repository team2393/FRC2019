package robot.parts;

import edu.wpi.first.wpilibj.hal.HALUtil;

/** Support for the 'USER' Button on the roboRIO */
public class USERButton
{
    /** @return <code>true</code> when the 'USER' button is pressed, otherwise <code>false</code> */
    public static boolean isPressed()
    {
        // It's a little hard to guess that this is the way to read the 'USER' button,
        // which is why this USERButton class was created
        return HALUtil.getFPGAButton();
    }
}
