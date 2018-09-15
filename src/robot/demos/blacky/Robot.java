package robot.demos.blacky;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.commands.HoldHeading;
import robot.commands.Move;
import robot.commands.SmoothMove;
import robot.commands.Turn;
import robot.parts.ContinuousRotationServo;
import robot.parts.USERButton;
import robot.subsystems.DriveSubsystem;

public class Robot extends BasicRobot
{
    private final static double max_speed = 0.2;

    // Subsystems, Components
    // Wrap the motors into a DriveSubsystem
    private DriveSubsystem drive_subsys = new DriveSubsystem(RobotMap.drive);
    private Button user = new USERButton();

    // Commands
    private Command jog = new SmoothMove(drive_subsys, 0.3, 0.6, 7.0);
    private Command forward = new Move(drive_subsys, max_speed, -1);
    private Command left = new Turn(drive_subsys, -max_speed/2, -1);
    private Command rock = new RocknRoll(drive_subsys, max_speed, -1);
    private Command wiggle = new Wiggle(drive_subsys, max_speed, -1);
    private Command hold = new HoldHeading(drive_subsys, RobotMap.gyro);
    private Command blink = new Blink(RobotMap.led, 0.3);

    @Override
    public void robotInit ()
    {
        super.robotInit();
        
        RobotMap.describe();

        user.toggleWhenPressed(blink);

        // Publish commands to allow control from dashboard
        SmartDashboard.putData("Jog", jog);
        SmartDashboard.putData("Forward", forward);
        SmartDashboard.putData("Left", left);
        SmartDashboard.putData("Rock'n'Roll", rock);
        SmartDashboard.putData("Wiggle", wiggle);
        SmartDashboard.putData("Hold Heading", hold);
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
        CommandGroup moves = new CommandGroup();
        moves.addSequential(new SmoothMove(drive_subsys, 0.25, 1.0, 3.0));
        moves.addSequential(new SmoothMove(drive_subsys, 0.5, 1.0, 5.0));
        moves.addSequential(new SmoothMove(drive_subsys, 0.1, max_speed, 5.0));
        moves.addSequential(new Wiggle(drive_subsys, 0.3, 2.0));
        moves.addSequential(new SmoothMove(drive_subsys, 0.1, -max_speed/2, 3.0));
        moves.addSequential(new RocknRoll(drive_subsys, 0.3, 3.0));
        moves.start();
    }

    // In teleop, use dashboard to trigger commands
}
