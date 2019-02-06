package robot.deepspace;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.camera.CameraHandler;
import robot.camera.CameraInfo;
import robot.camera.MarkerDetector;
import robot.deepspace.drivetrain.DriveTrain;
import robot.deepspace.drivetrain.HeadingHoldJoydrive;
import robot.deepspace.drivetrain.Joydrive;
import robot.deepspace.drivetrain.MoveToPosition;
import robot.deepspace.drivetrain.ResetDrivetrain;
import robot.deepspace.drivetrain.RotateToHeading;
import robot.deepspace.drivetrain.ToggleGear;
import robot.deepspace.grabber.CloseGrabber;
import robot.deepspace.grabber.Extend;
import robot.deepspace.grabber.Grabber;
import robot.deepspace.grabber.OpenGrabber;
import robot.deepspace.grabber.Retract;
import robot.deepspace.grabber.SetSpinnerSpeed;
import robot.deepspace.grabber.ToggleGrabber;
import robot.deepspace.grabber.WaitForCargo;
import robot.deepspace.grabber.WaitForHatch;
import robot.deepspace.lift.DriveLift;
import robot.deepspace.lift.HomeLift;
import robot.deepspace.lift.Lift;
import robot.deepspace.lift.MoveLift;
import robot.deepspace.riser.DropAll;
import robot.deepspace.riser.ResetRiser;
import robot.deepspace.riser.RiseFront;
import robot.deepspace.riser.Riser;

/** Main robot class for deep space 2019
 * 
 *  TODO Make all grabber commands require the Grabber
 * 
 *  TODO Documentation: PPT for OI, vision, ..
 *
 *  TODO Prepare autonomous moves from N start positions to M initial disk placements.
 *  Maybe leave last leg of route to driver, using vision, but get them close.
 */
public class DeepspaceRobot extends BasicRobot
{
    // TODO Use PDP
    // private final PowerDistributionPanel pdp = new PowerDistributionPanel();
    
    // Components, subsystems
    private final DriveTrain drivetrain = new DriveTrain();
    private final Lift lift = new Lift();
    private CameraHandler camera;
    private Grabber grabber = new Grabber();
    private Riser riser = new Riser();

    // Commands for drivetrain
    private final Command reset_drivetrain = new ResetDrivetrain(drivetrain);
    private final Command toggle_gear = new ToggleGear(drivetrain);
    private final Command joydrive = new Joydrive(drivetrain);
    private final Command hhdrive = new HeadingHoldJoydrive(drivetrain);

    // .. Lift
    private final Command home_lift = new HomeLift(lift);
    private final Command drive_lift = new DriveLift(lift);
    private final Command move_lift_low = new MoveLift("Low Pos", lift, 15.5);
    private final Command move_lift_middle = new MoveLift("Mid Pos", lift, 30.0);
    private final Command move_lift_high = new MoveLift("Hi Pos", lift, 75.0);

    // .. Grabber
    private final Command toggle_grabber = new ToggleGrabber(grabber);
    private final CommandGroup get_hatch = new CommandGroup();
    private final CommandGroup release_hatch = new CommandGroup();
    private final CommandGroup get_cargo = new CommandGroup();
    private final CommandGroup deposit_cargo = new CommandGroup();

    // .. Riser
    private final Command reset_riser = new ResetRiser(riser);
    private final Command drop_all = new DropAll(riser);
    private final Command rise_front = new RiseFront(riser);

    // What to start in autonomous mode
    private final SendableChooser<Command> auto_options = new SendableChooser<>();

    @Override
    public void robotInit()
    {
        super.robotInit();

        if (CameraInfo.haveCamera())
            camera = new CameraHandler(320, 240, 10, new MarkerDetector());

        // Fill command groups =======================================

        // Get hatch panel
        // TODO Maybe start by moving lift to loading station height
        // get_hatch.addSequential(new StartCommand(move_lift_low));
        get_hatch.addSequential(new Retract(grabber));
        get_hatch.addSequential(new OpenGrabber(grabber));
        get_hatch.addSequential(new WaitForHatch(grabber));
        get_hatch.addSequential(new CloseGrabber(grabber));
        // TODO Maybe add command to lift the hatch panel
        // off the lower brush in the loading station
        // get_hatch.addSequential(new MoveLift("Loading Station Get Out", lift, 18));
        // Then drivers need to move robot away from loading station,
        // to spaceship or rocket, and push low/mid/high position buttons.

        // Release hatch panel
        release_hatch.addSequential(new Extend(grabber));
        // release_hatch.addSequential(new OpenGrabber(grabber));

        // Get Cargo
        get_cargo.addSequential(new SetSpinnerSpeed(grabber, -0.5));
        get_cargo.addSequential(new WaitForCargo(grabber));
        // Maybe we use -0.1 to make sure cargo isn't dropped
        get_cargo.addSequential(new SetSpinnerSpeed(grabber, 0));

        // Deposit Cargo
        deposit_cargo.addSequential(new SetSpinnerSpeed(grabber, 1));
        deposit_cargo.addSequential(new WaitCommand(1));
        deposit_cargo.addSequential(new SetSpinnerSpeed(grabber, 0));

        // Auto moves
        createAutoMoves();

        // Bind Buttons to commands ..
        OI.gearshift.whenPressed(toggle_gear);
        OI.togglegrabber.whenPressed(toggle_grabber);

        OI.set_lift_home.whenPressed(home_lift);
        OI.set_lift_low.whenPressed(move_lift_low);
        OI.set_lift_med.whenPressed(move_lift_middle);
        OI.set_lift_high.whenPressed(move_lift_high);

        // TODO Buttons to
        // move lift to the rocket's low/mid/high cargo openings,
        // move lift to space ship cargo opening,
        // get_hatch,
        // release_hatch,
        // drop_all, rise_front, reset_riser

        // .. and/or place them on dashboard
        SmartDashboard.putData("Auto Options", auto_options);
   
        SmartDashboard.putData("Drive", joydrive);
        SmartDashboard.putData("HH Drive", hhdrive);
        SmartDashboard.putData("Reset", reset_drivetrain);

        SmartDashboard.putData("Home Lift", home_lift);
        SmartDashboard.putData("Drive Lift", drive_lift);
        SmartDashboard.putData("Lift Low", move_lift_low);
        SmartDashboard.putData("Lift Middle", move_lift_middle);
        SmartDashboard.putData("Lift High", move_lift_high);

        SmartDashboard.putData("Get Hatch", get_hatch);
        SmartDashboard.putData("Release Hatch", release_hatch);
        SmartDashboard.putData("Get Cargo", get_cargo);
        SmartDashboard.putData("Deposit Cargo", deposit_cargo);

        SmartDashboard.putData("Reset Riser", reset_riser);
        SmartDashboard.putData("Drop All", drop_all);
        SmartDashboard.putData("Rise Front", rise_front);

        // Publish scheduler to see active command
        SmartDashboard.putData(Scheduler.getInstance());

        // Allow "Reset" even when not in teleop or periodic
        reset_drivetrain.setRunWhenDisabled(true);
        // .. and in fact do it right now
        reset_drivetrain.start();

        // TODO Allow home_lift when disabled, and do that right now?
        // home_lift.setRunWhenDisabled(true);
        // home_lift.start();

        // TODO Make sure risers are retracted
        // reset_riser.setRunWhenDisabled(true);
        // reset_riser.start();
    }

    /** Create auto moves */
    private void createAutoMoves()
    {
        // Demo
        CommandGroup demo = new CommandGroup();
        demo.addSequential(new ResetDrivetrain(drivetrain));
        for (int i=0; i<10; ++i)
        {
            demo.addSequential(new RotateToHeading(drivetrain, 90));
            demo.addSequential(new WaitCommand(3));
            demo.addSequential(new RotateToHeading(drivetrain, 0));
            demo.addSequential(new WaitCommand(3));
        }
        auto_options.addOption("0deg 90deg", demo);

        demo = new CommandGroup();
        demo.addSequential(new ResetDrivetrain(drivetrain));
        for (int i=0; i<10; ++i)
        {
            demo.addSequential(new MoveToPosition(drivetrain, 2*12));
            demo.addSequential(new WaitCommand(3));
            demo.addSequential(new MoveToPosition(drivetrain, 0*12));
            demo.addSequential(new WaitCommand(3));
        }
        auto_options.addOption("2ft and back", demo);

        demo = new CommandGroup();
        demo.addSequential(new ResetDrivetrain(drivetrain));
        demo.addSequential(new MoveToPosition(drivetrain, 6*12, 0));
        demo.addSequential(new RotateToHeading(drivetrain, 90));
        demo.addSequential(new MoveToPosition(drivetrain, (6+3)*12, 90));
        demo.addSequential(new RotateToHeading(drivetrain, 180));
        demo.addSequential(new MoveToPosition(drivetrain, (6+3+6)*12, 180));
        demo.addSequential(new RotateToHeading(drivetrain, 270));
        demo.addSequential(new MoveToPosition(drivetrain, (6+3+6+3)*12, 270));
        demo.addSequential(new RotateToHeading(drivetrain, 360));
        auto_options.addOption("Rectangle", demo);

        // Left start position to Rocket port 1
        demo = new CommandGroup();
        demo.addSequential(new ResetDrivetrain(drivetrain));
        demo.addSequential(new MoveToPosition(drivetrain, 6*12));
        demo.addSequential(new RotateToHeading(drivetrain, -20));
        demo.addSequential(new MoveToPosition(drivetrain, (6+3)*12));
        // .. and back for testing
        demo.addSequential(new MoveToPosition(drivetrain, 6*12));
        demo.addSequential(new RotateToHeading(drivetrain, 0));
        demo.addSequential(new MoveToPosition(drivetrain, 0));
        auto_options.addOption("L to R1", demo);

        // Also allow "Nothing"
        auto_options.setDefaultOption("Nothing", new WaitCommand(0.1));
    }

    @Override
    public void robotPeriodic()
    {
        Scheduler.getInstance().run();

        // TODO Publish energy info
        // SmartDashboard.putNumber("Current [A]", pdp.getTotalCurrent());
        // SmartDashboard.putNumber("Capacity [Ws]", pdp.getTotalEnergy());
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
        // Toggle between plain drive
        // and heading-hold mode
        if (OI.isToggleHeadingholdPressed())
        {
            if (joydrive.isRunning())
            {
                joydrive.cancel();
                hhdrive.start();
            }
            else
            {
                hhdrive.cancel();
                joydrive.start();
            }
        }
    }

    @Override
    public void autonomousInit()
    {
        super.autonomousInit();

        // Start the selected option
        auto_options.getSelected().start();
    }

    @Override
    public void autonomousPeriodic()
    {
        // Test drive PID
        // if ((System.currentTimeMillis() / 5000) % 2 == 1)
        //     drivetrain.setPosition(24);
        // else
        //     drivetrain.setPosition(0);
    }
}