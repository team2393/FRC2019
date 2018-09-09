package robot.demos.blacky;

import edu.wpi.first.wpilibj.command.Command;

/** Command to wiggle the robot left/right */
public class Wiggle extends Command
{
    private double max;
    private double turn = 0.0;
    private boolean left = true;
    
    /** @param max How fast to wiggle
     *  @param duration How long [seconds]. -1 to keep going until stopped
     */
    public Wiggle(double max, double duration)
    {
        this.max = max;
        if (duration > 0)
            setTimeout(duration);
        doesRequire(Robot.wheels);
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
        Robot.wheels.drive(0.0, turn);
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
        Robot.wheels.stop();
    }
}
