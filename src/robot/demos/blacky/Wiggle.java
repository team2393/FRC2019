package robot.demos.blacky;

import edu.wpi.first.wpilibj.command.Command;

/** Command to wiggle the robot left/right */
public class Wiggle extends Command
{
    private Wheels wheels;
    private double max;
    private double turn = 0.0;
    private boolean left = true;

    /** @param wheels {@link Wheels} that we use
     *  @param max How fast to wiggle
     *  @param duration How long [seconds]. -1 to keep going until stopped
     */
    public Wiggle(Wheels wheels, double max, double duration)
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
        turn = 0.0;
        left = true;
    }

    @Override
    protected void execute()
    {
        wheels.turn(turn);
        if (left)
        {
            turn += 0.01;
            if (turn >= max)
            {
                turn = max;
                left = false;
            }
        }
        else
        {
            turn -= 0.01;
            if (turn <= -max)
            {
                turn = -max;
                left = true;
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
