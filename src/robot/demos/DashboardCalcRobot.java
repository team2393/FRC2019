package robot.demos;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

/** Robot that uses the Dashboard as UI for calculator
 * 
 *  Publishes values 'a' and 'b' to the 'SmartDashboard'
 *  so you can enter numbers, and then displays the result
 *  in 'c'.
 */
public class DashboardCalcRobot extends BasicRobot
{
    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("Open dashboard");

        // Set the default value so that these items are displayed
        // on the dashboard for user to enter something:
        SmartDashboard.setDefaultNumber("a", 0.0);
        SmartDashboard.setDefaultNumber("b", 0.0);
        SmartDashboard.setDefaultString("op", "+");
    }

    @Override
	public void robotPeriodic()
	{
        // Read value that user had entered.
        // We _did_ publish an initial default value,
        // so there _will_ be data, but we need to provide
        // a fallback value (0.0) anyway
        double a = SmartDashboard.getNumber("a", 0.0);
        double b = SmartDashboard.getNumber("b", 0.0);

        String op = SmartDashboard.getString("op", "+");
        
        // Perform sophisticated math as requested
        double c;
        if (op.equals("+"))
            c = a + b;
        else if (op.equals("-"))
            c = a - b;
        else
        { 
            // We do not recognize the requested operation,
            // so report the special not-a-number value.
            // Pity is that the dashboard doesn't handle this
            // and just keeps showing the last number...
            c = Double.NaN;
        }
        
        // Publish the result
        SmartDashboard.putNumber("c", c);
	}
}
