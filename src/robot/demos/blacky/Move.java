package robot.demos.blacky;

import edu.wpi.first.wpilibj.command.Command;

/** Command to move forward or back */
public class Move extends Command
{
    private double speed;

    /** @param speed How fast to move forward or back, -1..1
     *  @param duration How long [seconds]. -1 to keep going until stopped
     */
    public Move(double speed, double duration)
    {
        this.speed = speed;
        if (duration > 0)
            setTimeout(duration);
        doesRequire(Robot.wheels);
    }
    
    @Override
    protected void execute()
    {
        Robot.wheels.move(speed);
    }

    @Override
    protected boolean isFinished()
    {
        return isTimedOut();
    }

    @Override
    protected void end()
    {
        Robot.wheels.stop();
    }
}
