package robot.commands;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/** Command that blinks an LED */
public class Blink extends Command
{
    private DigitalOutput led;
    private Timer timer = new Timer();
    private double period;

    /** @param channel {@link DigitalOutput} channel for LED
     *  @param period Period in seconds
     */
    public Blink(DigitalOutput channel, double period)
    {
        led = channel;
        this.period = period;
    }

    @Override
    protected void initialize()
    {
        // Turn LED on
        timer.start();
        led.set(true);
    }

    @Override
    protected void execute()
    {
        // Whenever the timer expires...
        if (timer.hasPeriodPassed(period))
        {
            // toggle the LED on/off..
            led.set(! led.get());
            // and reset (restart) the timer
            timer.reset();
        }
    }

    @Override
    protected void end()
    {
        // Turn LED off. Stop timer.
        led.set(false);
        timer.stop();
    }

    @Override
    protected boolean isFinished()
    {
        // We're never finished on our own
        // (but command can still be cancelled,
        //  for example when robot gets disabled)
        return false;
    }
}
