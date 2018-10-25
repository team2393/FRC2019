package meetups;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import robot.BasicRobot;
import robot.parts.ContinuousRotationServo;
import robot.subsystems.DriveSubsystem;

public class Robot8 extends BasicRobot
{
    // Beeper or LED
    private DigitalOutput beepy = new DigitalOutput(0);

    // 2-Wheel Drive
    private ContinuousRotationServo left = new ContinuousRotationServo(0);
    private ContinuousRotationServo right = new ContinuousRotationServo(1);
    private DifferentialDrive diffdrive = new DifferentialDrive(left, right);
    private DriveSubsystem drive = new DriveSubsystem(diffdrive);

    private Command short_beep /* = new BeepCommand(beepy, 0.5) */  ;     // TODO implement
    private Command long_beep;     // TODO implement
    private Command small_step;    // TODO implement
    private Command longer_jog;    // TODO implement

    @Override
    public void robotInit()
    {
        super.robotInit();

        // TODO Enable as the commands get implemented
        // SmartDashboard.putData("Beep", short_beep);
        // SmartDashboard.putData("BEEEP!", long_beep);
        // SmartDashboard.putData("Step", small_step);
        // SmartDashboard.putData("Jog", longer_jog);
    }

    @Override
    public void robotPeriodic()
    {
        // Always run the scheduler; it handles the commands
        Scheduler.getInstance().run();
    }
}
