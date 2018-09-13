package robot.demos.blacky;

import edu.wpi.first.wpilibj.command.Command;

/** Command to rock the robot back and forth */
public class RocknRoll extends Command
{
    private Wheels wheels;
    private double max;
    private double speed = 0.0;
    private boolean forward = true;

    /** @param wheels {@link Wheels} that we use
     *  @param max How fast to rock/roll
     *  @param duration How long [seconds]. -1 to keep going until stopped
     */
    public RocknRoll(Wheels wheels, double max, double duration)
    {
        this.wheels = wheels;
        this.max = max;
        if (duration > 0)
            setTimeout(duration);
        doesRequire(wheels);
    }

    @Override
    protected void initialize()
    {
        speed = 0.0;
        forward = true;
    }

    @Override
    protected void execute()
    {
        wheels.move(speed);
        if (forward)
        {
            speed += 0.01;
            if (speed >= max)
            {
                speed = max;
                forward = false;
            }
        }
        else
        {
            speed -= 0.01;
            if (speed <= -max)
            {
                speed = -max;
                forward = true;
            }
        }
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
