package robot.demos;

import edu.wpi.first.wpilibj.RobotController;
import robot.BasicRobot;

/** Robot that displays the cycle time when pushing USER button */
public class MeasurePeriodRobot extends BasicRobot
{
    /** Last time we were called */
    private long last_ms = System.currentTimeMillis();

    /** Average time between calls */
    private double avg_ms = 20;

    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("Push the USER button to display average period");
    }

    @Override
	public void robotPeriodic()
	{
        // What time is it now?
	    long now = System.currentTimeMillis();
	    
	    // How long since we were last called?
	    long diff = now - last_ms;
	    
	    // Compute running average
	    avg_ms = (diff + avg_ms) / 2.0;
	    
	    // If USER button is pressed, display the info
	    if (RobotController.getUserButton())
	        System.out.println("Period: " + avg_ms + " ms");
	    
	    // Update 'last' ms for the next period
	    last_ms = now;
	}
}
