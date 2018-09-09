package robot.demos.blacky;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

public class Robot extends BasicRobot
{
    private final static double max_speed = 0.2;

    public static Wheels wheels = new Wheels();
    
    private Command move = new Move(max_speed, -1);
    private Command rock = new RocknRoll(max_speed, -1);
    private Command wiggle = new Wiggle(max_speed, -1);
    
    @Override
    public void robotInit ()
    {
        super.robotInit();
        
        // Publish commands to allow control from dashboard
        SmartDashboard.putData(Scheduler.getInstance());
        SmartDashboard.putData("Move", move);
        SmartDashboard.putData("Rock'n'Roll", rock);
        SmartDashboard.putData("Wiggle", wiggle);
    }

    @Override
    public void robotPeriodic()
    {
        Scheduler.getInstance().run();
    }
    
    @Override
    public void autonomousInit()
    {
        CommandGroup moves = new CommandGroup();
        moves.addSequential(new Move(0.3, 1.0));
        moves.addSequential(new Wiggle(0.3, 1.0));
        moves.addSequential(new Move(0.3, 1.0));
        moves.addSequential(new RocknRoll(0.3, 1.0));
        moves.start();
    }

    @Override
    public void teleopInit()
    {
        wiggle.start();
    }
}
