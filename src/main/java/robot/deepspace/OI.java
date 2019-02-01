package robot.deepspace;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import robot.parts.PDPController;

/** Operator Interface Definitions
 * 
 *  One place to find which button does what
 */
public class OI
{
    private static final Joystick joystick = new Joystick(0);
    
    private static final Joystick buttonboard = new Joystick(1);
    
    public static final Button gearshift = new JoystickButton(joystick, PDPController.LEFT_FRONT_BUTTON);

    public static final Button set_lift_home = new JoystickButton(joystick, PDPController.LEFT_TOP_BUTTON);

    public static final Button set_lift_low = new JoystickButton(joystick, PDPController.X_BUTTON);

    public static final Button set_lift_med = new JoystickButton(joystick, PDPController.Y_BUTTON);

    public static final Button set_lift_high = new JoystickButton(joystick, PDPController.B_BUTTON);

    public static boolean isGrabberToggled()
    {
        return joystick.getRawButtonPressed(PDPController.A_BUTTON);
        //return buttonboard.getRawButtonPressed(1);
    }

    public static final double getSpeed()
    {
        double raw = joystick.getRawAxis(PDPController.LEFT_STICK_VERTICAL);
        return -square(raw);
    }

    public static final double getTurn() 
    {
        return square (joystick.getRawAxis(PDPController.RIGHT_STICK_HORIZONTAL));
    }

    // sqare velae to be mor sensitive around 0
    private static double square(double raw) 
    {
		double squareded = raw*raw;
        if (raw >= 0)
            return squareded;
        else    
            return -squareded;
    }        

    public static final double getLiftUpDown()
    {
        return -joystick.getRawAxis(PDPController.RIGHT_STICK_VERTICAL);
    }
}
