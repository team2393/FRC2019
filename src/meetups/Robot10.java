package meetups;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.commands.DriveByJoystick;
import robot.commands.HoldHeadingPID;
import robot.commands.SmoothMove;
import robot.parts.ContinuousRotationServo;
import robot.parts.PDPController;
import robot.subsystems.DriveSubsystem;

public class Robot10 extends BasicRobot
{
    // Beeper or LED
    private final DigitalOutput beepy = new DigitalOutput(0);

    private final  Gyro gyro = new ADXRS450_Gyro();

    // 2-Wheel Drive
    private final ContinuousRotationServo left = new ContinuousRotationServo(1);
    private final ContinuousRotationServo right = new ContinuousRotationServo(0);
    private final DifferentialDrive diffdrive = new DifferentialDrive(left, right);
    private final DriveSubsystem drive = new DriveSubsystem(diffdrive);

    private final Joystick joystick = new Joystick(0);

    // Commands
    private final Command backup_beep = new BeepPatternCommand(beepy, "bbb---");

    private final Command joydrive = new DriveByJoystick(drive, joystick);

    private final Command sm1 = new SmoothMove(drive, 0.75, 1.0, 10.0);
    private final HoldHeadingPID hold = new HoldHeadingPID(drive, gyro);
    private SendableChooser<Command> auto_options = new SendableChooser<>();

    @Override
    public void robotInit()
    {
        super.robotInit();
        left.setInverted(false);

        SmartDashboard.putData(auto_options);
    }

    @Override
    public void robotPeriodic()
    {
        // Always run the scheduler; it handles the commands
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopPeriodic()
    {
        super.teleopPeriodic();
        joydrive.start();

        joystick.setRumble(RumbleType.kLeftRumble, Math.abs(drive.getSpeed()));
        joystick.setRumble(RumbleType.kRightRumble, Math.abs(drive.getSpeed()));

        // When moving backwards, start the backup_beep.
        // This keeps starting respectively stopping the command, even if
        // it's already running resp. stopped.
        // No problem:
        // If a command is already running, starting it again has no effect.
        // Same for stopping if already stopped.
        if (joystick.getRawAxis(PDPController.RIGHT_STICK_VERTICAL) > 0.1)
            backup_beep.start();
        else
            backup_beep.cancel();
    }

    @Override
    public void autonomousInit()
    {
        super.autonomousInit();
        //auto_options.getSelected().start();

        gyro.reset();
        hold.start();
        sm1.start();
    }

    @Override
    public void autonomousPeriodic()
    {
        if (sm1.isCompleted())
        {
            hold.setDesiredHeading(hold.getDesiredHeading() + 180.0);
            sm1.start();
        }
    }
}
