package robot.util;

/** Helper for checking if some readback
 *  has been within tolerance of the desired setpoint
 *  for some time.
 */
public class OnTargetCheck
{
    private final double tolerance;
    private final long required_ms;
    private long ms_since_ok = 0;

    public OnTargetCheck(final double the_tolerance,
                         final double required_seconds)
    {
        tolerance = the_tolerance;
        required_ms = Math.round(required_seconds * 1000);
    }

    public void reset()
    {
        ms_since_ok = 0;
    }

    public boolean isFinished(final double desired,
                              final double actual)
    {
        if (Math.abs(desired - actual) > tolerance)
            reset();
        else
        {
            final long now = System.currentTimeMillis();
            // Is this the first time close to the desired value?
            if (ms_since_ok == 0)
                ms_since_ok = now;
            else
                // Long enough at the desired value?
                if (now > ms_since_ok + required_ms)
                    return true;
        }

        return false;
    }
}
