package meetups;

/** Test of the {@link GrabberLogic}
 *
 *  Allows testing of the logic without
 *  having the robot available.
 *  Also documents what we think the grabber logic
 *  should do.
 */
public class GrabberTest
{
    public static void main(String[] args)
    {
        GrabberLogic grab = new GrabberLogic();

        // Initially, Grabber Should Not Do Anything
        compare(grab.getMotorSpeed(), 0);

        // When activated, Grabber Pulls In by slowly
        // turning the motor in negative direction
        grab.activate();
        compare(grab.getMotorSpeed(), -0.3);

        // When Sensor Detects Something It Should Stop
        grab.setSensor(true);
        compare(grab.getMotorSpeed(), 0);

        // Simulate That Cube Is Lost
        grab.setSensor(false);
        compare(grab.getMotorSpeed(), -0.3);

        // Pushing the 'Eject' button Should Eject The Cube
        // by running motor full speed in positive direction
        grab.eject();
        compare(grab.getMotorSpeed(), 1);
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
