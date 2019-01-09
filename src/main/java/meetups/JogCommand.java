package meetups;

import edu.wpi.first.wpilibj.command.TimedCommand;
import robot.subsystems.DriveSubsystem;

public class JogCommand extends TimedCommand
{
    private DriveSubsystem thing;

    public JogCommand(DriveSubsystem the_thing, double seconds)
    {
        super(seconds);
        thing = the_thing;
    }

    @Override
    public void execute()
    {
        thing.move(0.3);
    }

    @Override
    protected void end()
    {
        thing.move(0.0);
    }
}
