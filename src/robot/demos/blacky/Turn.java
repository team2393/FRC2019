package robot.demos.blacky;

import edu.wpi.first.wpilibj.command.Command;

/** Command to rotate left/right */
public class Turn extends Command
{
    private double turn;

    /** @param turn How fast to rotate, left -1 to right 1
     *  @param duration How long [seconds]. -1 to keep going until stopped
     */
    public Turn(double turn, double duration)
    {
        this.turn = turn;
        if (duration > 0)
            setTimeout(duration);
        doesRequire(Robot.wheels);
    }
    
    @Override
    protected void execute()
    {
        Robot.wheels.drive(0.0, turn);
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
