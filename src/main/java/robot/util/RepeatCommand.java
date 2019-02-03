package robot.util;

import edu.wpi.first.wpilibj.command.Command;

/** Command that keeps repeating another command */
public class RepeatCommand extends Command
{
    private final Command other;

    /** @param other Command to repeat */ 
    public RepeatCommand(final Command other)
    {
        this.other = other;
    }

    @Override
    protected void execute()
    {
        if (! other.isRunning())
            other.start();
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }

    @Override
    protected void end()
    {
        other.cancel();
    }
}
