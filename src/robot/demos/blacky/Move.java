package robot.demos.blacky;

import edu.wpi.first.wpilibj.command.Command;

/** Command to move forward or back */
public class Move extends Command
{
    private Wheels wheels;
    private double speed;

    /** @param wheels {@link Wheels} that we use
     *  @param speed How fast to move forward or back, -1..1
     *  @param duration How long [seconds]. -1 to keep going until stopped
     */
    public Move(Wheels wheels, double speed, double duration)
    {
        this.wheels = wheels;
        this.speed = speed;
        if (duration > 0)
            setTimeout(duration);
        doesRequire(wheels);
    }

    @Override
    protected void execute()
    {
        wheels.move(speed);
    }

    @Override
    protected boolean isFinished()
    {
        return isTimedOut();
    }

    @Override
    protected void end()
    {
        wheels.stop();
    }
}
