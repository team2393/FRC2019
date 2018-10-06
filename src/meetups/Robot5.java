package meetups;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.BasicRobot;
import robot.commands.Blink;

// Command demo: Blink
public class Robot5 extends BasicRobot
{
    // LED connected to DIO 9
    private DigitalOutput led = new DigitalOutput(9);

    // Command: Blink that LED, half second period
    private Command blink = new Blink(led, 0.5);

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
}
