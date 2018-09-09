package robot.demos.ledcmd;

import edu.wpi.first.wpilibj.command.Command;

/** Command that causes the robot to be "active" for a while */
public class LookBusy extends Command
{
    /** @param duration How long do we want to be active [seconds]? */
    public LookBusy(double duration)
    {
        // After the desired duration for being active, time out
        setTimeout(duration);
        // This command needs the robot's activity indicator.
        requires(LEDRobot.activity_indicator);
        // It's OK to run this command even when the robot is disabled
        setRunWhenDisabled(true);
    }
    
    // Called repeatedly when this Command is running
    @Override
    protected void execute()
    {
        LEDRobot.activity_indicator.setActive(true);
    }
    
    // While running, this is called to check if we're done 
    @Override
    protected boolean isFinished()
    {
        // We're done when the timeout expires 
        return isTimedOut();
    }

    // Called once when we stop running (isFinished, cancelled)
    @Override
    protected void end()
    {
        LEDRobot.activity_indicator.setActive(false);
    }
}
