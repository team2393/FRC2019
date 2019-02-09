package robot.deepspace;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.TimedCommand;

/** command to rumble for one second */
public class Rumble extends TimedCommand
{
    public Rumble()
    {
        super(1.0);
    }

    public void start(double duration)
    {
        setTimeout(duration);
        start();
    }

    @Override
    protected void execute()
    {
        OI.joystick.setRumble(RumbleType.kLeftRumble, 1);
        OI.joystick.setRumble(RumbleType.kRightRumble, 1);
    }

    @Override
    protected void end()
    {
        OI.joystick.setRumble(RumbleType.kLeftRumble, 0);
        OI.joystick.setRumble(RumbleType.kRightRumble, 0);
    }
}
