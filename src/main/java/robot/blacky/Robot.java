package robot.blacky;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.demos.commands.Blink;
import robot.demos.commands.DriveByJoystick;
import robot.demos.commands.HoldHeading;
import robot.demos.commands.HoldHeadingPID;
import robot.demos.commands.HoldHeadingPID2;
import robot.demos.commands.Move;
import robot.demos.commands.POVHeading;
import robot.demos.commands.RocknRoll;
import robot.demos.commands.SmoothMove;
import robot.demos.commands.Turn;
import robot.demos.commands.Wiggle;
import robot.demos.subsystems.DriveSubsystem;
import robot.parts.USERButton;

public class Robot extends BasicRobot
{
    private final static double max_speed = 0.2;

    // Subsystems, Components
    // Wrap the motors into a DriveSubsystem
    private DriveSubsystem drive_subsys = new DriveSubsystem(RobotMap.drive);
    private Button user = new USERButton();
    private Joystick joystick = new Joystick(0);

    // Commands
    private Command jog = new SmoothMove(drive_subsys, 0.3, 0.6, 7.0);
    private Command forward = new Move(drive_subsys, max_speed, -1);
    private Command left = new Turn(drive_subsys, -max_speed/2, -1);
    private Command rock = new RocknRoll(drive_subsys, max_speed, -1);
    private Command wiggle = new Wiggle(drive_subsys, max_speed, -1);
    private Command hold = new HoldHeading(drive_subsys, RobotMap.gyro);
    private Command hold_pid = new HoldHeadingPID(drive_subsys, RobotMap.gyro);
    private Command hold_pid2 = new HoldHeadingPID2(drive_subsys, RobotMap.gyro);
    private Command pov = new POVHeading(drive_subsys, RobotMap.gyro, joystick);
    private Command stick = new DriveByJoystick(drive_subsys, joystick);
    private Command blink = new Blink(RobotMap.led, 0.3);
    private CommandGroup moves = new CommandGroup();

    private SendableChooser<Command> auto_options = new SendableChooser<>();

    @Override
    public void robotInit ()
    {
        super.robotInit();

        RobotMap.describe();

        // Build some more complex commands
        moves.addSequential(new SmoothMove(drive_subsys, 0.25, 1.0, 3.0));
        moves.addSequential(new SmoothMove(drive_subsys, 0.5, 1.0, 5.0));
        moves.addSequential(new SmoothMove(drive_subsys, 0.1, max_speed, 5.0));
        moves.addSequential(new Wiggle(drive_subsys, 0.3, 2.0));
        moves.addSequential(new SmoothMove(drive_subsys, 0.1, -max_speed/2, 3.0));
        moves.addSequential(new RocknRoll(drive_subsys, 0.3, 3.0));

        // List options for auto
        auto_options.addObject("Moves", moves);
        auto_options.addObject("Blink", blink);
        auto_options.addObject("Rock'n'Roll", rock);
        auto_options.addDefault("Wiggle", wiggle);

        // Bind blinking to the User button
        user.toggleWhenPressed(blink);

        // Publish commands to allow control from dashboard
        SmartDashboard.putData("Auto Options", auto_options);
        SmartDashboard.putData("Jog", jog);
        SmartDashboard.putData("Forward", forward);
        SmartDashboard.putData("Left", left);
        SmartDashboard.putData("Rock'n'Roll", rock);
        SmartDashboard.putData("Wiggle", wiggle);
        SmartDashboard.putData("Hold Heading", hold);
        SmartDashboard.putData("Hold Heading PID", hold_pid);
        SmartDashboard.putData("Hold Heading PID2", hold_pid2);
        SmartDashboard.putData("POV Heading", pov);
        SmartDashboard.putData("Joystick", stick);
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

        // Start the selected command
        auto_options.getSelected().start();
    }

    @Override
    public void teleopInit()
    {
        super.teleopInit();

        // In teleop, you would typically enable joystick operation,
        // but dashboard can also start/stop commands
        stick.start();
    }
}
