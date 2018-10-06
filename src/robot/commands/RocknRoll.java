package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.subsystems.DriveSubsystem;

/** Command to rock the robot back and forth */
public class RocknRoll extends Command
{
    private DriveSubsystem drive_sub;
    private double max;
    private double speed = 0.0;
    private boolean forward = true;

    /** @param drive_sub {@link DriveSubsystem} that we use
     *  @param max How fast to rock/roll
     *  @param duration How long [seconds]. -1 to keep going until stopped
     */
    public RocknRoll(DriveSubsystem drive_sub, double max, double duration)
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
        speed = 0.0;
        forward = true;
    }

    @Override
    protected void execute()
    {
        drive_sub.move(speed);
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
        drive_sub.stop();
    }
}
