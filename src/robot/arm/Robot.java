package robot.arm;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

public class Robot extends BasicRobot
{
    private Arm arm = new Arm();
    private Joystick joystick = new Joystick(0);

    private Command manual = new JoystickArmControl(arm, joystick);
    private Command home = new MoveArm(arm, Arm.HAND_HOME, Arm.INNER_HOME, Arm.OUTER_HOME, Arm.BASE_HOME);
    private Command back_and_forth = createBackAndForth();
    private Command stack_two = createStackTwo();
    private Command stack_two2 = createStackTwo2();

    private SendableChooser<Command> auto_options = new SendableChooser<>();

    @Override
    public void robotInit()
    {
        super.robotInit();

        auto_options.addDefault("Back & Forth", back_and_forth);
        auto_options.addObject("Stack Two", stack_two);
        auto_options.addObject("Stack Two 2", stack_two2);
        auto_options.addObject("Home", home);
        SmartDashboard.putData(auto_options);
    }

    @Override
    public void robotPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit()
    {
        manual.start();
    }

    @Override
    public void autonomousInit()
    {
        auto_options.getSelected().start();
    }

    private Command createBackAndForth()
    {
        CommandGroup moves = new CommandGroup();
        moves.addSequential(new MoveArm(arm, Arm.HAND_HOME, Arm.INNER_HOME, Arm.OUTER_HOME, Arm.BASE_HOME));
        moves.addSequential(new MoveArm(arm, 160, 80.45, 60, 90));
        moves.addSequential(new MoveArm(arm, 105, 80.45, 60, 90));
        moves.addSequential(new MoveArm(arm, 105, 42, 120, 90));
        moves.addSequential(new MoveArm(arm, 105, 42, 120, 145));
        moves.addSequential(new MoveArm(arm, 105, 81.65, 84.65, 145));
        moves.addSequential(new MoveArm(arm, 160, 81.65, 84.65, 145));
        moves.addSequential(new WaitCommand(2.0));
        moves.addSequential(new MoveArm(arm, 160, 42, 120, 145));
        moves.addSequential(new MoveArm(arm, Arm.HAND_HOME, Arm.INNER_HOME, Arm.OUTER_HOME, Arm.BASE_HOME));
        moves.addSequential(new WaitCommand(2.0));
        moves.addSequential(new MoveArm(arm, 160, 42, 120, 145));
        moves.addSequential(new MoveArm(arm, 160, 81.65, 84.65, 145));
        moves.addSequential(new MoveArm(arm, 105, 81.65, 84.65, 145));
        moves.addSequential(new MoveArm(arm, 105, 42, 120, 145));
        moves.addSequential(new MoveArm(arm, 105, 42, 120, 90));
        moves.addSequential(new MoveArm(arm, 105, 80.45, 60, 90));
        moves.addSequential(new MoveArm(arm, 160, 80.45, 60, 90));
        moves.addSequential(new MoveArm(arm, Arm.HAND_HOME, Arm.INNER_HOME, Arm.OUTER_HOME, Arm.BASE_HOME));
        return moves;
    }

    private Command createStackTwo()
    {
        CommandGroup moves = new CommandGroup();
        moves.addSequential(new MoveArm(arm, 160.0, 44.5, 89.5, 90.0));
        moves.addSequential(new MoveArm(arm, 160.0, 69.2, 69.5, 90.0));
        moves.addSequential(new MoveArm(arm, 108.9, 69.2, 69.5, 90.0));
        moves.addSequential(new MoveArm(arm, 119.0, 30.3, 118.1, 90.0));
        moves.addSequential(new MoveArm(arm, 106.5, 30.3, 118.1, 128.0));
        moves.addSequential(new MoveArm(arm, 106.5, 88.5, 61.4, 128.0));
        moves.addSequential(new MoveArm(arm, 160.0, 88.5, 61.4, 128.0));
        moves.addSequential(new MoveArm(arm, 160.0, 38.3, 114.3, 128.0));
        moves.addSequential(new MoveArm(arm, 160.0, 44.66, 90.4, 90.3));
        moves.addSequential(new MoveArm(arm, 160.0, 84.5, 60.0, 90.3));
        moves.addSequential(new MoveArm(arm, 102.5, 84.5, 60.0, 90.3));
        moves.addSequential(new MoveArm(arm, 102.5, 47.2, 103.6, 90.3));
        moves.addSequential(new MoveArm(arm, 102.5, 47.2, 103.6, 124.9));
        moves.addSequential(new MoveArm(arm, 102.5, 62.0, 86.1, 124.9));
        moves.addSequential(new MoveArm(arm, 160.0, 62.0, 86.1, 126.7));
        moves.addSequential(new MoveArm(arm, 160.0, 50.3, 81.0, 126.7));
        moves.addSequential(new MoveArm(arm, 160.0, 36.5, 120.3, 126.7));
        moves.addSequential(new MoveArm(arm, 160.0, 44.5, 90.4, 90.1));
        return moves;
    }

    private Command createStackTwo2()
    {
        CommandGroup moves = new CommandGroup();
        moves.addSequential(new MoveArm(arm, 160.0, 45.0, 90.0, 90.0));
        moves.addSequential(new MoveArm(arm, 160.0, 64.3, 67.7, 90.0));
        moves.addSequential(new MoveArm(arm, 99.5, 64.3, 67.7, 90.0));
        moves.addSequential(new MoveArm(arm, 99.5, 46.9, 87.1, 90.0));
        moves.addSequential(new MoveArm(arm, 99.5, 46.9, 87.1, 120.8));
        moves.addSequential(new MoveArm(arm, 99.5, 78.7, 63.4, 120.8));
        moves.addSequential(new MoveArm(arm, 99.5, 84.9, 60.0, 120.8));
        moves.addSequential(new MoveArm(arm, 160.0, 84.9, 60.0, 120.8));
        moves.addSequential(new MoveArm(arm, 160.0, 53.4, 83.8, 120.8));
        moves.addSequential(new MoveArm(arm, 160.0, 53.4, 83.8, 88.6));
        moves.addSequential(new MoveArm(arm, 160.0, 85.4, 60.0, 88.6));
        moves.addSequential(new MoveArm(arm, 99.5, 85.4, 60.0, 88.6));
        moves.addSequential(new MoveArm(arm, 99.5, 43.7, 107.4, 88.6));
        moves.addSequential(new MoveArm(arm, 99.5, 43.7, 107.4, 52.9));
        moves.addSequential(new MoveArm(arm, 99.5, 88.5, 80.8, 52.9));
        moves.addSequential(new MoveArm(arm, 160.0, 88.5, 80.8, 52.9));
        moves.addSequential(new MoveArm(arm, 160.0, 53.7, 95.0, 52.9));
        moves.addSequential(new MoveArm(arm, 160.0, 53.7, 95.0, 117.9));
        moves.addSequential(new MoveArm(arm, 160.0, 71.3, 67.8, 117.9));
        moves.addSequential(new MoveArm(arm, 160.0, 84.3, 61.8, 117.9));
        moves.addSequential(new MoveArm(arm, 96.5, 84.3, 61.8, 117.9));
        moves.addSequential(new MoveArm(arm, 96.5, 59.1, 82.0, 117.9));
        moves.addSequential(new MoveArm(arm, 96.5, 59.1, 82.0, 52.5));
        moves.addSequential(new MoveArm(arm, 96.5, 59.6, 100.0, 52.5));
        moves.addSequential(new MoveArm(arm, 96.5, 67.8, 91.3, 52.5));
        moves.addSequential(new MoveArm(arm, 96.5, 71.6, 87.7, 52.5));
        moves.addSequential(new MoveArm(arm, 160.0, 71.6, 87.7, 52.5));
        moves.addSequential(new MoveArm(arm, 160.0, 54.5, 106.3, 52.5));
        moves.addSequential(new MoveArm(arm, 160.0, 45.4, 90.3, 89.5));
        return moves;
    }
}
