package meetups;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.parts.ContinuousRotationServo;
import robot.parts.USERButton;
import robot.subsystems.DriveSubsystem;

public class Robot8 extends BasicRobot
{
    // Beeper or LED
    private final DigitalOutput beepy = new DigitalOutput(0);

    // 2-Wheel Drive
    private final ContinuousRotationServo left = new ContinuousRotationServo(0);
    private final ContinuousRotationServo right = new ContinuousRotationServo(1);
    private final DifferentialDrive diffdrive = new DifferentialDrive(left, right);
    private final DriveSubsystem drive = new DriveSubsystem(diffdrive);

    //encoder
    private final Encoder spin = new Encoder(1, 2);

    // Commands
    private final Command short_beep = new BeepCommand(beepy, 0.5);
    private final Command long_beep = new BeepCommand(beepy, 2);
    private final CommandGroup signal = new CommandGroup();
    private final Command fanfare = new FanfareCommand(beepy, "b--b-b-bbb-b-bbb");

    private final Command small_step = new JogCommand(drive, 1);
    private final Command longer_jog = new JogCommand(drive, 2);

    @Override
    public void robotInit()
    {
        super.robotInit();

        signal.addSequential(new BeepCommand(beepy, 0.1));
        signal.addSequential(new WaitCommand(0.05));
        signal.addSequential(new BeepCommand(beepy, 0.5));
        signal.addSequential(new WaitCommand(0.05));
        signal.addSequential(new BeepCommand(beepy, 0.1));

        SmartDashboard.putData("Beep", short_beep);
        SmartDashboard.putData("BEEEP!", long_beep);
        SmartDashboard.putData("BeepBeep", signal);
        SmartDashboard.putData("Fanfare", fanfare);
        SmartDashboard.putData("Step", small_step);
        SmartDashboard.putData("Jog", longer_jog);

        new USERButton().whenPressed(small_step);
    }

    @Override
    public void robotPeriodic()
    {
        // Always run the scheduler; it handles the commands
        Scheduler.getInstance().run();

        SmartDashboard.putNumber("ticks", spin.get());
    }

    @Override
    public void autonomousInit()
    {
        fanfare.start();
    }
}
