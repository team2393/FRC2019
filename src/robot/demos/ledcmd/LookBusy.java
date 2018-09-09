package robot.demos.ledcmd;

import edu.wpi.first.wpilibj.command.TimedCommand;

/** Command that causes the robot to be "active" for a while */
public class LookBusy extends TimedCommand
{
    /** @param duration How long do we want to be active [seconds]? */
    public LookBusy(double duration)
    {
        // After the desired duration for being active, time out
        super(duration);
        // This command needs the robot's activity indicator.
        requires(LEDRobot.activity_indicator);
    }
    
    // Called once when the command starts
    @Override
    protected void initialize()
    {
        LEDRobot.activity_indicator.setActive(true);
    }

    // Called repeatedly while this Command is running, until duration passes
    @Override
    protected void execute()
    {
        // Could keep calling LEDRobot.activity_indicator.setActive(true), but not necessary

    }
    
    // Called once when we stop running (isFinished, cancelled)
    @Override
    protected void end()
    {
        LEDRobot.activity_indicator.setActive(false);
    }
}
