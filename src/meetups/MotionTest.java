package meetups;

public class MotionTest
{
    public static void main(String[] args)
    {
        // Accelerate at 0.5 per sec, full speed 1.0, run for total of 10 secs
        MotionCurve motion = new MotionCurve(0.5, 1.0, 10.0);
        for (double t=0.0; t<=10.1; t += 0.1)
            System.out.format("%5.2f %5.3f\n", t, motion.getSpeed(t));

        // Initial speed should be 0
        compare(motion.getSpeed(0), 0.0);

        // After 1 second, we should be at speed 0.5
        compare(motion.getSpeed(1), 0.5);

        // After 2 seconds, we should be at full speed
        compare(motion.getSpeed(2), 1.0);

        // We needed 2 seconds to get to full speed,
        // which also means we'll need 2 seconds to slow down from full speed to 0.
        // Running for a total of 10 seconds means we should
        // run full speed until 8 seconds, then slow down in
        // the last 2 seconds.
        // Check that we are at full speed from 2 .. 8 secs
        compare(motion.getSpeed(3), 1.0);
        compare(motion.getSpeed(5), 1.0);
        compare(motion.getSpeed(7), 1.0);
        compare(motion.getSpeed(8), 1.0);

        // At 9 seconds, we should have slowed down
        // to the same speed we had at 1 second
        compare(motion.getSpeed(9), 0.5);

        // From 10 secs on, we should stand still
        compare(motion.getSpeed(10), 0.0);
        compare(motion.getSpeed(15), 0.0);
        compare(motion.getSpeed(2570), 0.0);
    }

    /** Check if we are at the desired speed
     *  @param actual Motor speed reported by the grabber
     *  @param desired Value that we expected to see
     */
    private static void compare(double actual, double desired)
    {
        if(actual != desired)
            throw new Error("Expected " + desired + " but got " + actual + " :(");
        else
            System.out.println("Got expected value of " + actual + " :) :) :)");
    }
}
