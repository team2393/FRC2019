package meetups;

/** Logic for operating a 'grabber'
 *
 *  Has a motor that depending on direction
 *  pulls cubes in or pushes them out,
 *  and a sensor that detects if a cube
 *  is currently in the grabber.
 */
public class GrabberLogic
{
    private double speed = 0.0;

    public double getMotorSpeed()
    {
        return speed;
    }

    public void activate()
    {
        speed = -0.3;
    }

    public void setSensor(boolean cubein)
    {
        if (cubein)
            speed = 0.0;
        else
            speed = -0.3;
    }

    public void eject()
    {
        speed = 1;
    }
}
