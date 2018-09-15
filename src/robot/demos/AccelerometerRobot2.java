package robot.demos;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

/** Robot that integrates 'Y' axis accelerometer reading to estimate forward/backward motion.
 *  Not practical, see also http://www.chrobotics.com/library/accel-position-velocity
 */
public class AccelerometerRobot2 extends BasicRobot
{
    // Default range and max. range is 8G
    private Accelerometer accel = new BuiltInAccelerometer(Range.k2G);

    private long last_milli = 0;
    private double avg_accel = 0.0;
    private double velocity = 0.0;
    private double position = 0.0;
    
    @Override
	public void robotPeriodic()
	{
        long milli = System.currentTimeMillis();
        if (last_milli != 0)
        {
            // Time between calls
            double period = (milli - last_milli) / 1000.0;
    
            // In principle, the accelerometer measures acceleration/
            double acceleration = accel.getY() - avg_accel;
            
            // Acceleration updates our speed
            velocity += acceleration * period;
            
            // .. and speed changes our position
            position += velocity * period;

            // Reset when USER button is pressed
            if (RobotController.getUserButton())
            {
                avg_accel = acceleration;
                velocity = position = 0.0;
            }
            System.out.println("Position: " + position);
            
            SmartDashboard.putNumber("Acceleration", acceleration);
            SmartDashboard.putNumber("Speed", velocity);
            SmartDashboard.putNumber("Position", position);
        }
        last_milli = milli;
	}
}
