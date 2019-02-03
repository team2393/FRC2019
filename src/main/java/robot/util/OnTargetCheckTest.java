package robot.util;

import java.util.concurrent.TimeUnit;

/** Test/demo of {@link OnTargetCheck} */
public class OnTargetCheckTest
{
    public static void main(String[] args) throws InterruptedException
    {
        // Need to be withing 5 counts for 2 seconds
        final OnTargetCheck on_target = new OnTargetCheck(5, 2);

        final double desired = 10.0;
        System.out.println("Too far from desired value");
        System.out.println(on_target.isFinished(desired, 20.0));
        TimeUnit.SECONDS.sleep(3);
        System.out.println(on_target.isFinished(desired, 20.0));

        System.out.println("Within tolerance, but need to wait 2 seconds");
        System.out.println(on_target.isFinished(desired, 12.0));
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println(on_target.isFinished(desired, 13.0));
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println(on_target.isFinished(desired, 11.0));
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println(on_target.isFinished(desired, 10.5));
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println(on_target.isFinished(desired, 10.5));
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println(on_target.isFinished(desired, 10.5));
    }
}
