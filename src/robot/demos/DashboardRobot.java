package robot.demos;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

/** Robot that interacts with the Dashboard
 * 
 *  Publishes values to the 'SmartDashboard'.
 *  
 *  When starting the 'SmartDashboard',
 *  published items appear automatically
 *  and you can then configure some detail on how
 *  they look.
 *  For example, select a 'Boolean Box' for the 'USER Button'.
 *  
 *  Similarly, these values can be added to a ShuffleBoard
 *  by dragging them from Sources -> NetworkTables -> SmartDashboard
 */
public class DashboardRobot extends BasicRobot
{
    private Accelerometer accel = new BuiltInAccelerometer();

    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("Open dashboard, push USER button, tilt the roboRIO");

        // Set an initial, default, value for "Name"
        // to have it appear on the SmartDashboard for the user to enter a value.
        // Later, we read what the user entered.
        SmartDashboard.setDefaultString("Name", "");
    }

    @Override
	public void robotPeriodic()
	{
        // Publish the current value of the 'USER' button so we can show it on the Dashboard
        SmartDashboard.putBoolean("USER Button", RobotController.getUserButton());

        // We can not only publish a value to the DB but also read a value:
        String name = SmartDashboard.getString("Name", "");
        if (name.isEmpty())
            SmartDashboard.putString("StatusMessage", "Enter your name!");
        else
            SmartDashboard.putString("StatusMessage", "Hello, " + name);
        
        // The 'BuiltInAccelerometer' actually already publishes
        // itself in the 'LiveWindow' as 'BuiltInAccel[0].X, Y, Z'.
        // This adds it once more to the 'SmartDashboard'
        SmartDashboard.putNumber("Accel X", accel.getX());
        SmartDashboard.putNumber("Accel Y", accel.getY());
        SmartDashboard.putNumber("Accel Z", accel.getZ());
	}
}
