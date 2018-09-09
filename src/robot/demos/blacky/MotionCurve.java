package robot.demos.blacky;

/** A smooth motion curve
 * 
 *  Accelerate for some time until reaching a desired speed,
 *  then run at that speed,
 *  then decelerate down,
 *  and all that within a given duration.
 */
public class MotionCurve
{
    private double accel;
    private double v_max;
    private double t_accel;
    private double t_break;
    private double t_end;
    
    /** @param accel Acceleration in units/[sec*sec]
     *  @param v_max Maximum speed 0 .. 1 units/sec
     *  @param duration Time in seconds for the total move
     */
    public MotionCurve(double accel, double v_max, double duration)
    {
        this.accel = accel;
        
        // We start at speed v=0, then accelerate to v(t) = a*t.
        // So we reach v_max at t_accel
        t_accel = Math.abs(v_max) / accel;
        // Beware that v_max may be negative, but t_accel will be positive.
        
        // Then we run at v_max.
        // Deceleration will take as long as acceleration, i.e. has to start at
        t_break = duration - t_accel;
        
        // If the overall duration was too short, we won't be able to accelerate
        // to v_max and then back to zero.
        if (t_break < t_accel)
        {
            // Best we can do is accelerate half of the given time and then start breaking:
            t_accel = t_break = duration / 2;
            // And this is how fast we'll get
            this.v_max = Math.signum(v_max) * accel * t_accel;
        }
        else
            this.v_max = v_max;
        t_end = duration;
    }

    public double getSpeed(double t)
    {
        if (t <= 0.0 || t >= t_end)
            return 0.0;
        if (t < t_accel)
            return Math.signum(v_max) * accel * t;
        else if (t < t_break)
            return v_max;
        else
            return v_max - Math.signum(v_max) * accel * (t - t_break);
    }
}
