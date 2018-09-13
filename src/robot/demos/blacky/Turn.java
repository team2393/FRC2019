package robot.demos.blacky;

import edu.wpi.first.wpilibj.command.Command;

/** Command to rotate left/right */
public class Turn extends Command
{
    private Wheels wheels;

    private double turn;

    /** @param wheels {@link Wheels} that we use
     *  @param turn How fast to rotate, left -1 to right 1
     *  @param duration How long [seconds]. -1 to keep going until stopped
     */
    public Turn(Wheels wheels, double turn, double duration)
    {
        this.wheels = wheels;
        this.turn = turn;
        if (duration > 0)
            setTimeout(duration);
        doesRequire(wheels);
    }

    @Override
    protected void execute()
    {
        wheels.turn(turn);
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
