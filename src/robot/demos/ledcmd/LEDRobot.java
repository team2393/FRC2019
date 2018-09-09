package robot.demos.ledcmd;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import robot.BasicRobot;
import robot.USERButton;

/** Robot that uses Commands
 * 
 *  Functionally, this is similar to {@link robot.demos.LEDDemoRobot1}:
 *  Push USER button -> LED turns on.
 *  
 *  But instead of simply having `new DigitalOutput(9)`
 *  for the LED right in here, we
 *  
 *  1) Define the port number as RobotMap.DIO_LED.
 *  
 *  2) Wrap the LED (plus a dashboard 'Active' tag) as an ActivityIndicator Subsystem.
 *  
 *  3) Create a LookBusy Command for turning the ActivityIndicator on/off.
 *
 *  That's overkill for a single LED, but will make it easier
 *  to keep track as more hardware is added.
 *  The GroupCommand example below shows how simple commands
 *  can be combined into larger commands.
 *  
 *  The ActivityIndicator and Scheduler appear in the Shuffleboard (LiveWindow section)
 *  so we can see when commands are executed.
 */
public class LEDRobot extends BasicRobot
{
    // Subsystems of this robot
    public static ActivityIndicator activity_indicator = new ActivityIndicator();
    
    // Command that we start when the USER button is pressed
    private Command do_something;
    
    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("LED on DIO " + RobotMap.DIO_LED);
        System.out.println("Push USER button.");
        
        // do_something could just be a plain LookBusy(1 second):
//        do_something = new LookBusy(1.0);
        
        // The beauty of commands is that we can combine them:
        CommandGroup recipe = new CommandGroup("Be Really Busy");
        
        // In parallel, 1) print something and 2) look busy for one sec
        recipe.addParallel(new PrintCommand("Starting to be real busy..."));
        recipe.addParallel(new LookBusy(1.0));
        // Wait until those two complete
        recipe.addSequential(new WaitForChildren());
        // Add 3 shorter blips
        for (int i=0; i<3; ++i)
        {
            recipe.addSequential(new WaitCommand(0.2));
            recipe.addSequential(new LookBusy(0.2));
        }
        recipe.addSequential(new PrintCommand("Done."));
        do_something = recipe;
        
        // It's OK to run this command even when the robot is disabled
        do_something.setRunWhenDisabled(true);
    }
    
    @Override
	public void robotPeriodic()
	{
        if (USERButton.isPressed()  &&   !do_something.isRunning())
            do_something.start();
        
        // Run Scheduler which takes care of commands
        Scheduler.getInstance().run();
	}
}
