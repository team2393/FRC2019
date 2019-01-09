package meetups;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class TurnOn extends InstantCommand
{
    private DigitalOutput output;

    public TurnOn(DigitalOutput the_output)
    {
        output = the_output;
    }

    @Override
    protected void execute()
    {
        output.set(true);
    }
}
