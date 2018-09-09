package robot.demos.ledcmd;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.command.WaitForChildren;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
 *  The commands appear in the SmartDashboard or Shuffleboard
 *  so we can see when commands are executed,
 *  even start them from those tools or cancel them while running.
 */
public class LEDRobot extends BasicRobot
{
    // Subsystems of this robot
    public static ActivityIndicator activity_indicator = new ActivityIndicator();

    // Commands of this robot
    private Command say_hello, look_busy;

    // This one will be started via the USER button
    private CommandGroup do_something;
    
    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("LED on DIO " + RobotMap.DIO_LED);
        System.out.println("Push USER button.");

        // PrintCommand is a built-in type that prints text
        say_hello = new PrintCommand("Hello!");
        // For this demo, we want all commands to also work when disabled
        say_hello.setRunWhenDisabled(true);

        // Our LookBusy command
        look_busy = new LookBusy(0.5); 
        look_busy.setRunWhenDisabled(true);
        
        // The beauty of commands is that we can combine them:
        do_something = new CommandGroup("Be Really Busy");
        
        // In parallel, 1) print something and 2) look busy for one sec
        do_something.addParallel(new PrintCommand("Starting to be real busy..."));
        do_something.addParallel(new LookBusy(1.0));
        // Wait until those two complete
        do_something.addSequential(new WaitForChildren());
        // Add 3 shorter blips
        for (int i=0; i<3; ++i)
        {
            do_something.addSequential(new WaitCommand(0.2));
            do_something.addSequential(new LookBusy(0.2));
        }
        do_something.addSequential(new PrintCommand("Done."));
        do_something.setRunWhenDisabled(true);

        // Publish the subsystem to dashboard, so it will show when it's used by a command
        SmartDashboard.putData(activity_indicator);
        
        // Publish commands to dashboard: Allows starting/stopping them from there
        SmartDashboard.putData("Hello", say_hello);
        SmartDashboard.putData("Look Busy", look_busy);
        SmartDashboard.putData("Do Something", do_something);
    }
    
    @Override
	public void robotPeriodic()
	{
        // When USER button is pressed and command not already running,
        // start it.
        if (USERButton.isPressed()  &&   !do_something.isRunning())
            do_something.start();
        
        // Run Scheduler which takes care of commands
        Scheduler.getInstance().run();
	}
}
