package meetups;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.TimedCommand;

public class BeepCommand extends TimedCommand
{
    private DigitalOutput thing;

    /** Constructor:
     *
     *  Somebody who wants to use this needs to create
     *  an instance via
     *
     *  DigitalOutput beeper = new DigitalOutput(1);
     *  Command short_beep   = new BeepCommand(beeper, 0.2);
     *  Command long_beep    = new BeepCommand(beeper, 2.0);
     *
     *  @param the_thing The Dig.Out. to use
     *  @param seconds   How long to beep
     */
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
