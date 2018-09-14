package robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import robot.subsystems.DriveSubsystem;

/** Command that controls a {@link DriveSubsystem} from the Joystick */
public class DriveByJoystick extends Command
{
    private final DriveSubsystem drive_subsys;
    private final Joystick joystick;
    
    public DriveByJoystick(DriveSubsystem drive_subsys, Joystick joystick)
    {
        this.drive_subsys = drive_subsys;
        this.joystick = joystick;
        requires(drive_subsys);
    }
    
    @Override
    protected void execute()
    {
        drive_subsys.drive(joystick.getRawAxis(5), joystick.getRawAxis(4));
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
