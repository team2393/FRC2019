package robot.demos.blacky;

import edu.wpi.first.wpilibj.command.TimedCommand;

/** Command to move forward or back with a motion curve */
public class SmoothMove extends TimedCommand
{
    private MotionCurve motion;

    /** @param acceleration How far to accelerate/break
     *  @param speed How fast to move forward or back, -1..1
     *  @param duration How long [seconds]. -1 to keep going until stopped
     */
    public SmoothMove(double acceleration, double speed, double duration)
    {
        super(duration);
        motion = new MotionCurve(acceleration, speed, duration);
        doesRequire(Robot.wheels);
    }
    
    @Override
    protected void execute()
    {
        Robot.wheels.move(motion.getSpeed(timeSinceInitialized()));
    }

    @Override
    protected void end()
    {
        Robot.wheels.move(0.0);
    }
}
