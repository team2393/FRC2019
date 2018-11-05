package robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import robot.parts.PDPController;
import robot.subsystems.DriveSubsystem;

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

        // Full speed?
        // Buttons 5 and 6 are the two push-buttons at the front.
        // Holding at least one of them enables full speed
        if (joystick.getRawButton(PDPController.LEFT_FRONT_BUTTON)  ||
            joystick.getRawButton(PDPController.RIGHT_FRONT_BUTTON))
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
