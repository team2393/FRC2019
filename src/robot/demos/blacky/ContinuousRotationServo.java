package robot.demos.blacky;

import edu.wpi.first.wpilibj.PWMSpeedController;

/** Continuous rotation servo (pot detatched from gears)
 *  that acts like a speed controller
 */
public class ContinuousRotationServo extends PWMSpeedController
{
    public ContinuousRotationServo(int channel)
    {
        super(channel);
        
        // Values from Servo and Spark class
        setBounds(2.4, 1.52, 1.50, 1.48, 0.6);
        setPeriodMultiplier(PeriodMultiplier.k4X);
        setSpeed(0.0);
        setZeroLatch();
        setName("ContinuousRotationServo", getChannel());
    }
}
