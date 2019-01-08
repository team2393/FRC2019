package robot.util;

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
     *  @param v_max Maximum speed -1 .. 0 .. 1 units/sec.
     *               Negative for going backwards,
     *               positive to run forward.
     *  @param duration Time in seconds for the total move
     */
    public MotionCurve(double accel, double v_max, double duration)
    {
        this.accel = accel;

        // Beware that v_max may be negative:
        // We may go at v_max = 1.0, i.e. full speed forward,
        // but also v_max = -1.0, i.e. full speed backwards.
        // t_accel will always be positive,
        // and we then check the sign of v_max to determine
        // which way we're running.

        // We start at speed v=0, then accelerate to v(t) = a*t.
        // So we reach v_max at t_accel
        t_accel = Math.abs(v_max) / accel;

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

    /** Get speed based on motion curve
     *  @param t Time in seconds
     *  @return Speed at that time
     */
    public double getSpeed(double t)
    {
        // Outside of the time range, we're not moving at all
        if (t <= 0.0 || t >= t_end)
            return 0.0;
        // For t in  (0, t_accel), we accelerate
        if (t < t_accel)
            return Math.signum(v_max) * accel * t;
        // From [t_accel, t_break) we run at the full speed
        else if (t < t_break)
            return v_max;
        // For [t_break, t_end) we slow down from full speed
        else
            return v_max - Math.signum(v_max) * accel * (t - t_break);
    }
}
