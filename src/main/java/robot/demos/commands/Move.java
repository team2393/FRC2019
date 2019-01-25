package robot.demos.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.demos.subsystems.DriveSubsystem;

/** Command to move forward or back */
public class Move extends Command
{
    private DriveSubsystem drive_sub;
    private double speed;

    /** @param drive_sub {@link DriveSubsystem} that we use
     *  @param speed How fast to move forward or back, -1..1
     *  @param duration How long [seconds]. -1 to keep going until stopped
     */
    public Move(DriveSubsystem drive_sub, double speed, double duration)
    {
        this.drive_sub = drive_sub;
        this.speed = speed;
        if (duration > 0)
            setTimeout(duration);
    }

    @Override
    protected void execute()
    {
        drive_sub.move(speed);
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
