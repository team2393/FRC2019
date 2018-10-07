package robot.arm;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import robot.BasicRobot;

public class Robot extends BasicRobot
{
    private Arm arm = new Arm();
    private Joystick joystick = new Joystick(0);

    private Command manual = new JoystickArmControl(arm, joystick);
    private CommandGroup moves = new CommandGroup();

    @Override
    public void robotInit()
    {
        super.robotInit();

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
        moves.start();
    }
}
