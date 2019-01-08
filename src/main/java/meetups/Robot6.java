package meetups;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

public class Robot6 extends BasicRobot
{
    Servo base = new Servo(4);
    Command left = new GoLeftCommand(base);
    Command right = new GoRightCommand(base);
    CommandGroup moves;


    @Override
    public void robotInit()
    {
        super.robotInit();
        moves = new CommandGroup();
        moves.addSequential(new GoLeftCommand(base));
        moves.addSequential(new WaitCommand(1));
        moves.addSequential(new GoRightCommand(base));
        moves.addSequential(new WaitCommand(1));
        moves.addSequential(new GoLeftCommand(base));

        SmartDashboard.putData("left", left);
        SmartDashboard.putData("right", right);
        SmartDashboard.putData("moves", moves);
    }

    @Override
    public void robotPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit()
    {
        super.autonomousInit();
        left.start();
    }

    @Override
    public void teleopInit()
    {
        super.teleopInit();
        moves.start();
    }


}
