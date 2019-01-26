package robot.deepspace;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.camera.CameraHandler;
import robot.camera.MarkerDetector;

/** Main robot class for deep space 2019
 * 
 *  TODO: Test & tweak
 *  Lift: 1 motor, encoder, limit switch, button box to move lift to ~4 predertermined heights
 *  -> Talon PID & motion magic.
 *     Commands to drive with joystick, drive up, drive down, move to position.
 *   Can/should the robot automatically start HomeLift at startup?
 * 
 *  Use OI
 *  Camera & Vision: Show video from front of robot,
 *  with overlay when target markers are detected.
 *  Check if exposure etc. need to be controlled from dashboard instead of 'auto'.
 * 
 *  Disk grabber: 1 solenoid to hold/release disk, button to toggle
 *  -> Commands open/close? Automatically close when disk is detected?
 * 
 *  TODO: Implement initial version
 * 
 * 
 *  Documentation: PPT for OI, vision, ..
 *
 *  Drive motors: Left and right, 2 Talons each side, one follows the other, 1 encoder per side, gyro
 *  -> Need to program PID for movement with gyro to keep heading for autonomous moves.
 *  Use PIDCommand or PIDSubsystem.
 *  Check https://frc-pdr.readthedocs.io/en/latest/control/using_WPILIB's_pid_controller.html#adding-ramping-for-motors
 *
 *  Gearbox Shifter: 1 or 2 Solenoids, button to shift high <-> low, indicate current gear on dashboard
 * 
 *  Prepare autonomous moves from N start positions to M initial disk placements.
 *  Maybe leave last leg of route to driver, using vision, but get them close. 
 *
 *  Push-up mechanism: 1 solenoid for 2 front cylinders, 1 solenoid for back cylinder, 1 drive motor controller.
 *  Idea:
 *  Push button to lower 2 front and 1 back cylinder,
 *  and now bottom drive will move with other wheels forward/backward.
 *  Push button to raise 2 front cylinders back up, bottom drive still follows main wheels.
 *  Push button to raise back cylinder, bottom drive off.
 */
public class DeepspaceRobot extends BasicRobot
{
    // Components, subsystems
    private final DriveTrain drivetrain = new DriveTrain();
    private final Lift lift = new Lift();
    private CameraHandler camera;
    private Grabber grabber = new Grabber();

    // Commands for drivetrain
    private final Command toggle_gear = new ToggleGear(drivetrain);
    private final Command joydrive = new Joydrive(drivetrain);
    // .. Lift
    private final Command home_lift = new HomeLift(lift);
    private final Command drive_lift = new DriveLift(lift);
    private final Command move_lift_low = new MoveLift("Low Pos", lift, 15.5);
    private final Command move_lift_middle = new MoveLift("Mid Pos", lift, 30.0);
    private final Command move_lift_high = new MoveLift("Hi Pos", lift, 75.0);
    // // .. Grabber
    private final Command toggle = new ToggleGrabber(grabber);
    private final CommandGroup get_hatch = new CommandGroup();
    private final CommandGroup release_hatch = new CommandGroup();

    @Override
    public void robotInit()
    {
        super.robotInit();
		camera = new CameraHandler(320, 240, 10, new MarkerDetector());

        // // Fill command groups
        get_hatch.addSequential(new Retract(grabber));
        get_hatch.addSequential(new OpenGrabber(grabber));
        get_hatch.addSequential(new WaitForHatch(grabber));
        get_hatch.addSequential(new CloseGrabber(grabber));

        release_hatch.addSequential(new Extend(grabber));

        // // Bind Buttons to commands ..
        OI.gearshift.whenPressed(toggle_gear);

        // .. or place them on dashboard
        SmartDashboard.putData("Drive", joydrive);

        SmartDashboard.putData("Home Lift", home_lift);
        SmartDashboard.putData("Drive Lift", drive_lift);
        SmartDashboard.putData("Lift Low", move_lift_low);
        SmartDashboard.putData("Lift Middle", move_lift_middle);
        SmartDashboard.putData("Lift High", move_lift_high);

        SmartDashboard.putData("Get Hatch", get_hatch);
        SmartDashboard.putData("Release Hatch", release_hatch);
    }

    @Override
    public void robotPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit()
    {
        super.teleopInit();
        joydrive.start();
    }

    @Override
    public void teleopPeriodic()
    {
        if (OI.isGrabberToggled())
            toggle.start();
    }

    @Override
    public void autonomousInit()
    {
        super.autonomousInit();
    }

    @Override
    public void autonomousPeriodic()
    {
    }
}
