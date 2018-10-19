package meetups;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

public class Robot7 extends BasicRobot
{
    DigitalOutput beepy = new DigitalOutput(0);
    Command beepon = new TurnOn(beepy);
    Command beepoff = new TurnOff(beepy);
    CommandGroup noise;

    @Override
    public void robotInit()
    {
        super.robotInit();
        SmartDashboard.putData("on", beepon);
        SmartDashboard.putData("off", beepoff);

        noise = new CommandGroup();
        noise.addSequential(new TurnOn(beepy));
        noise.addSequential(new WaitCommand(0.1));
        noise.addSequential(new TurnOff(beepy));
        noise.addSequential(new WaitCommand(0.5));
        noise.addSequential(new TurnOn(beepy));
        noise.addSequential(new WaitCommand(0.1));
        noise.addSequential(new TurnOff(beepy));
        noise.addSequential(new WaitCommand(0.5));
        noise.addSequential(new TurnOn(beepy));
        noise.addSequential(new WaitCommand(0.1));
        noise.addSequential(new TurnOff(beepy));
        noise.addSequential(new WaitCommand(0.5));
        SmartDashboard.putData("noise", noise);
    }

    @Override
    public void robotPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit()
    {
        super.teleopInit();
        beepon.start();
    }

    @Override
    public void disabledInit()
    {
        super.disabledInit();
        beepy.set(false);
    }

    @Override
    public void autonomousInit()
    {
        super.autonomousInit();
        noise.start();
    }
}
