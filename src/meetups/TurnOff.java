package meetups;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class TurnOff extends InstantCommand
{
    private DigitalOutput output;

    public TurnOff(DigitalOutput the_output)
    {
        output = the_output;
    }

    @Override
    protected void execute()
    {
        output.set(false);
    }
}
