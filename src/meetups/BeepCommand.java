package meetups;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class BeepCommand extends TimedCommand
{
    private DigitalOutput thing;

    public BeepCommand(DigitalOutput the_thing, double seconds)
    {
        super(seconds);
        thing = the_thing;
    }

    @Override
    public void execute()
    {
        thing.set(true);
    }

    @Override
    protected void end()
    {
        thing.set(false);
    }
}
