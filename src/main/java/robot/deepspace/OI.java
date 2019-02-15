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
    public static final Joystick joystick = new Joystick(0);
    
    private static final Joystick buttonboard = new Joystick(1);
    
    public static final Button gearshift = new JoystickButton(joystick, PDPController.RIGHT_FRONT_BUTTON);

    public static final boolean isToggleHeadingholdPressed()
    {
        return joystick.getRawButtonPressed(PDPController.A_BUTTON);
    }
    
    // Hatch 'button' is actually a lever,
    // so we need to handle the has-it-been-pressed logic ourselves
    private static boolean hatch_button_was_pressed = false;
    
    public static final boolean isReleaseButtonPressed()
    {
        final boolean is_pressed = joystick.getRawAxis(PDPController.LEFT_FRONT_LEVER) > 0.8;
        final boolean was_pressed_since_last_checked = is_pressed  &&  ! hatch_button_was_pressed;
        hatch_button_was_pressed = is_pressed;
        return was_pressed_since_last_checked;
    }
    
    public static boolean isReleaseButtonReleased()
    {
        final boolean is_pressed = joystick.getRawAxis(PDPController.LEFT_FRONT_LEVER) > 0.8;
        final boolean was_released = hatch_button_was_pressed   &&  !is_pressed;
        hatch_button_was_pressed = is_pressed;
        return was_released;
    }

    public static final boolean isGetButtonPressed()
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
        // "Forward" should be positive
        double raw = -joystick.getRawAxis(PDPController.LEFT_STICK_VERTICAL);
        // Button reduces turn rate
        if (joystick.getRawAxis(PDPController.RIGHT_FRONT_LEVER) > 0.5)
            raw = raw/2;
        return square(raw);
    }

    public static final double getTurn() 
    {
        // Full turn speed tends to be too much, so calm by 0.9
        double raw = 0.9 * joystick.getRawAxis(PDPController.RIGHT_STICK_HORIZONTAL);
        // Button reduces turn rate
        if (joystick.getRawAxis(PDPController.RIGHT_FRONT_LEVER) > 0.5)
            raw = raw/2;
        return square(raw);
    }

    // square value to be more sensitive around 0
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

    public static final boolean isCargoModeEnabled()
    {
        return buttonboard.getRawButton(1);
    }

    public static final boolean isPickUpPressed()
    {
        return buttonboard.getRawButtonPressed(2);
    }    

    public static final boolean isCargoShipPressed()
    {
        return buttonboard.getRawButtonPressed(3);
    }

    public static final boolean isRocketLowPressed()
    {
        return buttonboard.getRawButtonPressed(4);
    }

    public static final boolean isRocketMedPressed()
    {
        return buttonboard.getRawButtonPressed(5);
    }

    public static final boolean isRocketHighPressed()
    {
        return buttonboard.getRawButtonPressed(8);
    }

    public static final boolean isRiserAllDownPressed()
    {
        return buttonboard.getRawButtonPressed(6);
    }

    public static final boolean isRiserFrontUpPressed()
    {
        return buttonboard.getRawButtonPressed(10);
    }

    public static final boolean isRiserAllUpPressed()
    {
        return buttonboard.getRawButtonPressed(9);
    }
}
