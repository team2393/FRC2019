package robot.deepspace;

import edu.wpi.first.wpilibj.Joystick;
import robot.parts.PDPController;

/** Operator Interface Definitions
 * 
 *  One place to find which button does what
 */
public class OI
{
    private static final Joystick joystick = new Joystick(0);

    private static final Joystick buttonboard = new Joystick(1);


    // public static final Button button = new JoystickButton(stick, buttonNumber);

    public static final double getSpeed()
    {
        double raw = joystick.getRawAxis(PDPController.LEFT_STICK_VERTICAL);
        return -square(raw);
    }

    //sqare velae to be mor sensitive around 0
    private static double square(double raw) 
    {
		double squareded = raw*raw;
        if (raw >= 0)
            return squareded;
        else
            return -squareded;
    }

    public static final double getTurn() 
    {
        return square (joystick.getRawAxis(PDPController.RIGHT_STICK_HORIZONTAL));
    }

    public static final double getLiftUpDown()
    {
        return -joystick.getRawAxis(PDPController.RIGHT_STICK_VERTICAL);
    }

    public static boolean isGrabberOpenPushed()
    {
        return joystick.getRawButtonPressed(PDPController.A_BUTTON);

        //return buttonboard.getRawButtonPressed(1);
    }
}
