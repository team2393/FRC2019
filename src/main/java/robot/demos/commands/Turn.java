package robot.demos.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.demos.subsystems.DriveSubsystem;

/** Command to rotate left/right */
public class Turn extends Command
{
    private DriveSubsystem drive_sub;

    private double turn;

    /** @param drive_sub {@link DriveSubsystem} that we use
     *  @param turn How fast to rotate, left -1 to right 1
     *  @param duration How long [seconds]. -1 to keep going until stopped
     */
    public Turn(DriveSubsystem drive_sub, double turn, double duration)
    {
        this.drive_sub = drive_sub;
        this.turn = turn;
        if (duration > 0)
            setTimeout(duration);
    }

    @Override
    protected void execute()
    {
        drive_sub.turn(turn);
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
