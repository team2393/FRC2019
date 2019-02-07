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
    
    public static final Button gearshift = new JoystickButton(joystick, PDPController.RIGHT_FRONT_BUTTON);
    public static final boolean isToggleHeadingholdPressed()
    {
        return joystick.getRawButtonPressed(PDPController.A_BUTTON);
    }

    private static boolean hatch_button_was_pressed = false;

    public static final boolean isHatchButtonPressed()
    {
        final boolean is_pressed = joystick.getRawAxis(PDPController.LEFT_FRONT_LEVER) > 0.8;
        final boolean was_pressed_since_last_checked = is_pressed  &&  ! hatch_button_was_pressed;
        hatch_button_was_pressed = is_pressed;
        return was_pressed_since_last_checked;
    }

    public static final boolean isCargoButtonPressed()
    {
        return joystick.getRawButtonPressed(PDPController.LEFT_FRONT_BUTTON);
    }
    public static final Button togglegrabber = new JoystickButton(joystick, PDPController.LEFT_FRONT_BUTTON);

    public static final Button set_lift_home = new JoystickButton(joystick, PDPController.LEFT_TOP_BUTTON);
    public static final Button set_lift_low = new JoystickButton(joystick, PDPController.X_BUTTON);
    public static final Button set_lift_med = new JoystickButton(joystick, PDPController.Y_BUTTON);
    public static final Button set_lift_high = new JoystickButton(joystick, PDPController.B_BUTTON);

    public static final double getSpeed()
    {
        final double raw = joystick.getRawAxis(PDPController.LEFT_STICK_VERTICAL);
        // "Forward" should be positive
        return -square(raw);
    }

    public static final double getTurn() 
    {
        // Full turn speed tends to be too much
        // TODO Have a button that toggles between full turn speed
        //      and reduced speed?
        return square (0.5 * joystick.getRawAxis(PDPController.RIGHT_STICK_HORIZONTAL));
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
        // "Up" should be positive
        return -joystick.getRawAxis(PDPController.RIGHT_STICK_VERTICAL);
    }
}
