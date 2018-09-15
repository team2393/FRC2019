package robot.blacky;

import edu.wpi.first.wpilibj.command.Command;
import robot.subsystems.DriveSubsystem;

/** Command to wiggle the robot left/right */
public class Wiggle extends Command
{
    private DriveSubsystem drive_sub;
    private double max;
    private double turn = 0.0;
    private boolean left = true;

    /** @param drive_sub {@link DriveSubsystem} that we use
     *  @param max How fast to wiggle
     *  @param duration How long [seconds]. -1 to keep going until stopped
     */
    public Wiggle(DriveSubsystem drive_sub, double max, double duration)
    {
        this.drive_sub = drive_sub;
        this.max = max;
        if (duration > 0)
            setTimeout(duration);
        doesRequire(drive_sub);
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
        drive_sub.turn(turn);
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
        drive_sub.stop();
    }
}
