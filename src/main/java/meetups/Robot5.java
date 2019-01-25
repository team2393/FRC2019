package meetups;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import robot.BasicRobot;
import robot.demos.commands.Blink;

// Command demo: Blink
public class Robot5 extends BasicRobot
{
    // LED connected to DIO 9
    private DigitalOutput led = new DigitalOutput(9);

    // Command: Blink that LED, half second period
    private Command blink = new Blink(led, 0.5);

    // Command(Group): Sequence of Blink, Wait, Blink, ..
    private CommandGroup signal = new CommandGroup();;

    public Robot5()
    {
        // Blink rapidly for half a second
        signal.addSequential(new Blink(led, 0.05), 0.5);
        // Pause
        signal.addSequential(new WaitCommand(1.0));
        // Blink a little slower for a full second, .. and so on
        signal.addSequential(new Blink(led, 0.1), 1.0);
        signal.addSequential(new WaitCommand(1.0));
        signal.addSequential(new Blink(led, 0.2), 2.0);
    }

    @Override
    public void robotPeriodic()
    {
        // For commands to work, the scheduler must 'run'
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit()
    {
        // When entering autonomous mode, start the blink command
        blink.start();

        // That command, driven by the scheduler, keeps blinking.
        // Commands are automatically stopped when the robot is disabled.
    }

    @Override
    public void teleopPeriodic()
    {
        // When user button is pressed, start the 'signal' sequence.
        if (RobotController.getUserButton())
            signal.start();
        // If it was already running, it just continues.
    }
}
