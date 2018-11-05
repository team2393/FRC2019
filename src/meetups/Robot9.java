package meetups;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.commands.DriveByJoystick;
import robot.parts.ContinuousRotationServo;
import robot.subsystems.DriveSubsystem;

public class Robot9 extends BasicRobot
{
    // Beeper or LED
    private final DigitalOutput beepy = new DigitalOutput(0);

    // 2-Wheel Drive
    private final ContinuousRotationServo left = new ContinuousRotationServo(0);
    private final ContinuousRotationServo right = new ContinuousRotationServo(1);
    private final DifferentialDrive diffdrive = new DifferentialDrive(left, right);
    private final DriveSubsystem drive = new DriveSubsystem(diffdrive);

    // Encoder
    private final Encoder encoder = new Encoder(1, 2);

    private final Joystick joystick = new Joystick(0);

    // Commands
    private final Command fanfare = new FanfareCommand(beepy, "b--b-b-bbb-b-bbb");

    private final Command joydrive = new DriveByJoystick(drive, joystick);

    private final Command goto_0   = new EncoderMoveCommand(drive, encoder,   0.0);
    private final Command goto_50  = new EncoderMoveCommand(drive, encoder,  50.0);
    private final Command goto_100 = new EncoderMoveCommand(drive, encoder, 100.0);

    private SendableChooser<Command> auto_options = new SendableChooser<>();

    @Override
    public void robotInit()
    {
        super.robotInit();

        // Diameter: 67 mm
        // ==> circumference is 67*pi,
        // encoder model should give 400 ticks per revolution
        encoder.setDistancePerPulse(67*Math.PI / 400.0);

        auto_options.addDefault(" 0 mm", goto_0);
        auto_options.addObject(" 50 mm", goto_50);
        auto_options.addObject("100 mm", goto_100);
        SmartDashboard.putData(auto_options);

        SmartDashboard.putData("Fanfare", fanfare);
    }

    @Override
    public void robotPeriodic()
    {
        // Always run the scheduler; it handles the commands
        Scheduler.getInstance().run();

        SmartDashboard.putNumber("position", encoder.getDistance());

        // Zero the encoder when red 'B' button is pressed
        if (joystick.getRawButtonPressed(2))
            encoder.reset();
    }

    @Override
    public void teleopPeriodic()
    {
        super.teleopPeriodic();
        joydrive.start();

        joystick.setRumble(RumbleType.kLeftRumble, Math.abs(drive.getSpeed()));
        joystick.setRumble(RumbleType.kRightRumble, Math.abs(drive.getSpeed()));
    }

    @Override
    public void autonomousInit()
    {
        super.autonomousInit();
        fanfare.start();
        auto_options.getSelected().start();
    }
}
