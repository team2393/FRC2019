package robot.demos;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer.Range;
import robot.BasicRobot;

/** Robot that displays the accelerometer readings
 * 
 *  Shows how X and Y could be used to detect
 *  if the robot is tilted.
 */
public class AccelerometerRobot extends BasicRobot
{
    // Default range and max. range is 8G
    private Accelerometer accel = new BuiltInAccelerometer(Range.k2G);

    /** Show value like this:
     *  <pre>
     *   0 ----------#----------
     *   1 ----------########### 
     *  -1 ###########----------
     *  </pre>
     *  @param value Value in range -1..1
     */
    public void printValueBar(double value)
    {
        System.out.format("%+5.2f ", value);
        
        // Restrict value to [-1, 1]
        if (value < -1.0)
            value = -1.0;
        else if (value > 1.0)
            value = 1.0;
        
        // Turn value -1..1 into number of bar segments -10..10, rounding
        int count = (int)(value * 10 + 0.5);
        if (count < 0)
        {
            for (int i=-10; i<=0; ++i)
                if (i < count)
                    System.out.print('-');
                else
                    System.out.print('#');
            System.out.print("----------");
        }
        else
        {
            System.out.print("----------");
            for (int i=0; i<=10; ++i)
                if (i <= count)
                    System.out.print('#');
                else
                    System.out.print('-');
        }
    }
    
    
    @Override
	public void robotPeriodic()
	{
        System.out.print("X ");
        printValueBar(accel.getX());

        System.out.print(", Y ");
        printValueBar(accel.getY());

        System.out.print(", Z ");
        printValueBar(accel.getZ());
        System.out.println();
	}
}
