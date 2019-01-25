package robot.demos.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import robot.parts.PDPController;
import robot.demos.subsystems.DriveSubsystem;

/** Command that controls a {@link DriveSubsystem} from the Joystick
 *
 *  Two-speed mode: Holde buttons 4 or 5 for full speed.
 *
 *  Applies a deadband to joystick readings:
 *  Small values are ignored to avoid moving based on
 *  noise around the 'stick center' of the joystick.
 */
public class DriveByJoystick extends Command
{
    private final DriveSubsystem drive_subsys;
    private final Joystick joystick;

    /** Speed and turn values below this threshold are ignored */
    private double deadband = 0.2;

    /** Factor by which we reduce speed & turn values in slow mode */
    private double slow = 0.2;

    private boolean was_pushed = false;
    private boolean full_speed = false;

    public DriveByJoystick(DriveSubsystem drive_subsys, Joystick joystick)
    {
        this.drive_subsys = drive_subsys;
        this.joystick = joystick;
        requires(drive_subsys);
    }

    @Override
    protected void execute()
    {
        // Positive speed = forward, positive turn = right
        double speed = -joystick.getRawAxis(PDPController.RIGHT_STICK_VERTICAL);
        double turn = joystick.getRawAxis(PDPController.RIGHT_STICK_HORIZONTAL);

        // Ignore small joystick values caused by noise around the stick center
        if (Math.abs(speed) < deadband)
            speed = 0.0;
        if (Math.abs(turn) < deadband)
            turn = 0.0;

        // Idea 1: Turn on/off via separate buttons
        // Works fine, but required two buttons
        //        if (joystick.getRawButton(PDPController.LEFT_FRONT_BUTTON)
        //            full_speed = false;
        //        if (joystick.getRawButton(PDPController.RIGHT_FRONT_BUTTON)
        //                full_speed = true;

        // Idea 2: Toggle full speed on/off via one button
        // Full speed when any of the push-buttons at the front are pressed.
        // Consumes just one joystick button.
        // Requires a 'was_pushed' type variable to remember if the button
        // was previously pressed.
        // Then check if button is right now pressed:
        //        boolean is_pushed = joystick.getRawButton(PDPController.RIGHT_FRONT_BUTTON);
        // If pressed, but wasn't last time we checked, toggle the full speed mode on/off
        //        // if (!was_pushed      &&   is_pushed)
        //        if (was_pushed==false   &&   is_pushed==true)
        //            full_speed = ! full_speed;
        // Remember if button was pushed
        //        was_pushed = is_pushed;

        // Idea 2.1: Use joystick's built-in was-button-pressed logic
        // The Joystick class has such logic already built in.
        if (joystick.getRawButtonPressed(PDPController.RIGHT_FRONT_BUTTON))
            full_speed = ! full_speed;

        if (full_speed)
            drive_subsys.drive(speed, turn);
        else
            drive_subsys.drive(slow*speed, slow*turn);
    }

    @Override
    protected void end()
    {
        drive_subsys.stop();
    }

    @Override
    protected boolean isFinished()
    {
        // Don't finish (but may be cancelled or interrupted)
        return false;
    }
}
