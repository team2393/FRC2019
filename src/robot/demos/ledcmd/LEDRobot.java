package robot.demos.ledcmd;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import robot.BasicRobot;
import robot.USERButton;

/** Robot that uses Commands
 * 
 *  Functionally, this is similar to LEDDemoRobot1:
 *  Push USER button -> LED turns on.
 *  
 *  But instead of simply having `new DigitalOutput(9)`
 *  for the LED right in here, we
 *  
 *  1) Define the port number as RobotMap.DIO_LED.
 *  
 *  2) Wrap the LED (plus a dashboard 'Active') as an ActivityIndicator Subsystem.
 *  
 *  3) Create a LookBusy Command for turning the ActivityIndicator on/off.
 *
 *  That's overkill for a single LED, but will make it easier
 *  to keep track as more hardware is added.
 *  
 *  The ActivityIndicator and Scheduler appear in the Shuffleboard (LiveWindow section)
 *  so we can see when commands are executed.
 */
public class LEDRobot extends BasicRobot
{
    public static ActivityIndicator activity_indicator = new ActivityIndicator();
    
    private Command be_active = new LookBusy(1.0);
    
    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("LED on DIO " + RobotMap.DIO_LED);
        System.out.println("Push USER button.");
    }
    
    @Override
	public void robotPeriodic()
	{
        if (USERButton.isPressed()  &&   !be_active.isRunning())
            be_active.start();
        Scheduler.getInstance().run();
	}
}
