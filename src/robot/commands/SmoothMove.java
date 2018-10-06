package robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;
import robot.subsystems.DriveSubsystem;
import robot.util.MotionCurve;

/** Command to move forward or back with a motion curve */
public class SmoothMove extends TimedCommand
{
    private DriveSubsystem drive_sub;
    private MotionCurve motion;

    /** @param drive_sub {@link DriveSubsystem} that we use
     *  @param acceleration How far to accelerate/break
     *  @param speed How fast to move forward or back, -1..1
     *  @param duration How long [seconds]. -1 to keep going until stopped
     */
    public SmoothMove(DriveSubsystem drive_sub, double acceleration, double speed, double duration)
    {
        super(duration);
        this.drive_sub = drive_sub;
        motion = new MotionCurve(acceleration, speed, duration);
    }

    @Override
    protected void execute()
    {
        drive_sub.move(motion.getSpeed(timeSinceInitialized()));
    }

    @Override
    protected void end()
    {
        drive_sub.move(0.0);
    }
}
