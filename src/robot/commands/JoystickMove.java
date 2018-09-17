package robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import robot.subsystems.DriveSubsystem;

/** Command to drive via joystick */
public class JoystickMove extends Command
{
    private final DriveSubsystem drive_subsys;
    private final Joystick joystick;

    public JoystickMove(DriveSubsystem drive_subsys, Joystick joystick)
    {
        this.drive_subsys = drive_subsys;
        this.joystick = joystick;
        doesRequire(drive_subsys);
    }

    @Override
    protected void execute()
    {
        double speed = joystick.getRawAxis(5);
        double turn = joystick.getRawAxis(4);
        // We use positive speed as "forward", but joystick is reversed/
        // Use slow movements (/5)
        drive_subsys.drive(- speed/5, turn/5);
    }

    @Override
    protected boolean isFinished()
    {
        // Don't finish (but may be cancelled or interrupted)
        return false;
    }

    @Override
    protected void end()
    {
        drive_subsys.stop();
    }
}
