package meetups;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Command;

/** Command that plays some beep pattern on a beeper */
public class FanfareCommand extends Command
{
    // Steps for a 'bip -- bip - bip - beeep - bip - beep':
    // "b--b-b-bbb-b-bbb".
    // For each 'b' character in the string,
    // we turn the beeper on, and for '-' we turn it off.
    private final String on_off;

    private final DigitalOutput beeper;

    private int step;
    private boolean done = false;

    public FanfareCommand(final DigitalOutput the_beeper, final String on_off_info)
    {
        beeper = the_beeper;
        on_off = on_off_info;
        // Typically, commands won't run in 'disabled' mode.
        // That's a safety mechanism:
        // All motors etc. must stop when disabled.
        // But beeping should be OK, so we allow
        // annoying people even when the robot is disabled.
        setRunWhenDisabled(true);
    }

    @Override
    public void execute()
    {
        // At which step are we?
        // timeSinceInitialized() tells us the seconds since the command was started.
        // Use 0.1 seconds per 'step',
        // truncating the exact double number to an integer
        step = (int) (timeSinceInitialized() / 0.1);
        // We're done when that step would be past the end of the on_off array
        done = step >= on_off.length();
        if (! done)
            beeper.set(on_off.charAt(step) == 'b');
    }

    @Override
    protected boolean isFinished()
    {
        return done;
    }

    @Override
    protected void end()
    {
        beeper.set(false);
    }
}
