package robot.demos.blacky;

import edu.wpi.first.wpilibj.command.TimedCommand;

/** Command to move forward or back with a motion curve */
public class SmoothMove extends TimedCommand
{
    private Wheels wheels;
    private MotionCurve motion;

    /** @param wheels {@link Wheels} that we use
     *  @param acceleration How far to accelerate/break
     *  @param speed How fast to move forward or back, -1..1
     *  @param duration How long [seconds]. -1 to keep going until stopped
     */
    public SmoothMove(Wheels wheels, double acceleration, double speed, double duration)
    {
        super(duration);
        this.wheels = wheels;
        motion = new MotionCurve(acceleration, speed, duration);
        doesRequire(wheels);
    }

    @Override
    protected void execute()
    {
        wheels.move(motion.getSpeed(timeSinceInitialized()));
    }

    @Override
    protected void end()
    {
        wheels.move(0.0);
    }
}
