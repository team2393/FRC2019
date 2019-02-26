package robot.deepspace;


import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.StartCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
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
import robot.deepspace.grabber.WaitForHatchButtonRelease;
import robot.deepspace.lift.DriveLift;
import robot.deepspace.lift.Lift;
import robot.deepspace.lift.MoveLift;
import robot.deepspace.lift.ResetLift;
import robot.deepspace.riser.DropAll;
import robot.deepspace.riser.ResetRiser;
import robot.deepspace.riser.RiseFront;
import robot.deepspace.riser.Riser;

/** Main robot class for deep space 2019
 *  *
 *  TODO Prepare autonomous moves from N start positions to M initial disk placements.
 *  Maybe leave last leg of route to driver, using vision, but get them close.
 */
public class DeepspaceRobot extends BasicRobot
{
    // private final PowerDistributionPanel pdp = new PowerDistributionPanel();
    private final Accelerometer tilty = new BuiltInAccelerometer();
    
    // Components, subsystems
    private final DriveTrain drivetrain = new DriveTrain();
    private final Lift lift = new Lift();
    private CameraHandler camera;
    private Grabber grabber = new Grabber();
    private Riser riser = new Riser();

    // Commands for drivetrain
    private final Command reset_drivetrain = new ResetDrivetrain(drivetrain);
    private final Command toggle_gear = new ToggleGear(drivetrain);
    private final Joydrive joydrive = new Joydrive(drivetrain);
    private final HeadingHoldJoydrive hhdrive = new HeadingHoldJoydrive(drivetrain);
    private final Rumble rumble = new Rumble();

    // .. Lift
    private final Command reset_lift = new ResetLift(lift);
    private final Command drive_lift = new DriveLift(lift);
    private final Command move_lift_hatch_high = new MoveLift("Hatch Hi Pos", lift, 54.5);
    private final Command move_lift_hatch_middle = new MoveLift("Hatch Mid Pos", lift, 29.5);
    private final Command move_lift_hatch_low = new MoveLift("Hatch Low Pos", lift, 3, true);
    private final Command move_lift_cargo_high = new MoveLift("Cargo Hi Pos", lift, 47  );
    private final Command move_lift_cargo_middle = new MoveLift("Cargo Mid Pos", lift, 25);
    private final Command move_lift_cargo_low = new MoveLift("Cargo Low Pos", lift, 3, true);
    private final Command move_lift_cargo_ship = new MoveLift("Cargo Ship Pos", lift, 10);
    private final Command move_lift_cargo_pickup = new MoveLift("Cargo Pickup Pos", lift, 11.8);

    // .. Grabber
    private final Command toggle_grabber = new ToggleGrabber(grabber);
    private final Command extend_grabber = new Extend(grabber);
    private final Command retract_grabber = new Retract(grabber);
    private final CommandGroup get_hatch = new CommandGroup();
    private final CommandGroup release_hatch = new CommandGroup();
    private final CommandGroup get_cargo = new CommandGroup();
    private final CommandGroup deposit_cargo = new CommandGroup();

    // .. Riser
    private final Command reset_riser = new ResetRiser(riser);
    private final Command drop_all = new DropAll(riser,tilty);
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
        get_hatch.addSequential(new Extend(grabber));
        get_hatch.addSequential(new OpenGrabber(grabber));
        get_hatch.addSequential(new WaitForHatch(grabber));
        get_hatch.addSequential(new WaitCommand(0.5));
        get_hatch.addSequential(new CloseGrabber(grabber));
        get_hatch.addSequential(new WaitCommand(0.5));
        get_hatch.addSequential(new Retract(grabber));
        // Only do this in autonomous:
        get_hatch.addSequential(
                new ConditionalCommand(new MoveLift("Loading Station Get Out", lift, 15))
                {
                    @Override
                    protected boolean condition()
                    {
                        return DriverStation.getInstance().isAutonomous();
                    }
                });

        // Release hatch panel
        release_hatch.addSequential(new Extend(grabber));
        release_hatch.addSequential(new WaitForHatchButtonRelease());
        release_hatch.addSequential(new OpenGrabber(grabber));
        release_hatch.addSequential(new Retract(grabber));
        
        // Get Cargo
        get_cargo.addSequential(new SetSpinnerSpeed(grabber, 0.3));
        get_cargo.addSequential(new WaitForCargo(grabber));
        // Maybe we use -0.1 to make sure cargo isn't dropped
        get_cargo.addSequential(new SetSpinnerSpeed(grabber, 0));

        // Deposit Cargo
        deposit_cargo.addSequential(new SetSpinnerSpeed(grabber, -1));
        deposit_cargo.addSequential(new WaitCommand(1));
        deposit_cargo.addSequential(new SetSpinnerSpeed(grabber, 0));

        // Auto moves
        createAutoMoves();

        // Bind Buttons to commands ..
        OI.gearshift.whenPressed(toggle_gear);
        OI.toggleLift.toggleWhenPressed(drive_lift);

        // .. and/or place them on dashboard
        SmartDashboard.putData("Auto Options", auto_options);
   
        SmartDashboard.putData("Drive", joydrive);
        SmartDashboard.putData("HH Drive", hhdrive);
        SmartDashboard.putData("Reset Drivetrain", reset_drivetrain);

        SmartDashboard.putData("Reset Lift", reset_lift);
        SmartDashboard.putData("Drive Lift", drive_lift);

        SmartDashboard.putData("Hatch Low", move_lift_hatch_low);
        SmartDashboard.putData("Hatch Mid", move_lift_hatch_middle);
        SmartDashboard.putData("Hatch High", move_lift_hatch_high);
        SmartDashboard.putData("Cargo Low", move_lift_cargo_low);
        SmartDashboard.putData("Cargo Mid", move_lift_cargo_middle);
        SmartDashboard.putData("Cargo High", move_lift_cargo_high);
        SmartDashboard.putData("Cargo Ship", move_lift_cargo_ship);
        SmartDashboard.putData("Cargo Pickup",move_lift_cargo_pickup);

        SmartDashboard.putData("Extend Grabber", extend_grabber);
        SmartDashboard.putData("Retract Grabber", retract_grabber);
        SmartDashboard.putData("Toggle Grabber", toggle_grabber);
        SmartDashboard.putData("Get Hatch", get_hatch);
        SmartDashboard.putData("Release Hatch", release_hatch);
        SmartDashboard.putData("Get Cargo", get_cargo);
        SmartDashboard.putData("Deposit Cargo", deposit_cargo);

        SmartDashboard.putData("Reset Riser", reset_riser);
        SmartDashboard.putData("Drop All", drop_all);
        SmartDashboard.putData("Rise Front", rise_front);

        // Allow "Reset" even when not in teleop or periodic
        reset_drivetrain.setRunWhenDisabled(true);
        // .. and in fact do it right now
        reset_drivetrain.start();
    }

    /** Create auto moves */
    private void createAutoMoves()
    {
        // Note: Most command sequences start with 'ResetDrivetrain'
        //       and end in StartCommand(joydrive)

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
        demo.addSequential(new StartCommand(joydrive));
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
        demo.addSequential(new StartCommand(joydrive));
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
        demo.addSequential(new StartCommand(joydrive));
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
        demo.addSequential(new StartCommand(joydrive));
        auto_options.addOption("L to R1", demo);
 
        demo = new CommandGroup();
        demo.addSequential(new ResetDrivetrain(drivetrain));
        demo.addSequential(new MoveToPosition(drivetrain, 118, 0));
        demo.addSequential(new RotateToHeading(drivetrain, 90));
        demo.addSequential(new MoveToPosition(drivetrain, 202, 90));
        demo.addSequential(new StartCommand(joydrive));
        auto_options.addOption("Test1", demo);

        // Also allow "Nothing"
        auto_options.setDefaultOption("Nothing", new StartCommand(joydrive));
    }

    @Override
    public void robotPeriodic()
    {
        Scheduler.getInstance().run();

        if (OI.isGetButtonPressed())
        {
            if (OI.isCargoModeEnabled())
                get_cargo.start();
            else
                get_hatch.start();
        }
            
        if (OI.isReleaseButtonPressed())
        {
            if (OI.isCargoModeEnabled())
                deposit_cargo.start();
            else 
                release_hatch.start();
        }
    }

    private void updateJoystickDrivemode()
    {
        // Toggle between plain drive
        // and heading-hold mode
        if (OI.isToggleHeadingholdPressed())
        {
            if (joydrive.isRunning())
            {
                joydrive.cancelbutdontstop();
                hhdrive.start();
                rumble.start(0.1);
            }
            else
            {
                hhdrive.cancelbutdontstop();
                joydrive.start();
                rumble.start(0.5);
            }
        }
    }
    
    private void handleButtonBoard()
    {
        if (! OI.haveButtonboard())
            return;
        final boolean cargo_mode = OI.isCargoModeEnabled();
        SmartDashboard.putBoolean("Cargo Mode", cargo_mode);
        SmartDashboard.putBoolean("Hatch Mode", ! cargo_mode);
        
        if (OI.isPickUpPressed())
        {
            if (cargo_mode)
                move_lift_cargo_pickup.start();
            else
                move_lift_hatch_low.start();
        }

        if (OI.isCargoShipPressed())
        {
            if (cargo_mode)
                move_lift_cargo_ship.start();
            else
                move_lift_hatch_low.start();
        }

        if (OI.isRocketLowPressed())
        {
            if (cargo_mode)
                move_lift_cargo_low.start();
            else
                move_lift_hatch_low.start();
        }

        if (OI.isRocketMedPressed())
        {
            if (cargo_mode)
                move_lift_cargo_middle.start();
            else
                move_lift_hatch_middle.start();
        }

        if (OI.isRocketHighPressed())
        {
            if (cargo_mode)
                move_lift_cargo_high.start();
            else
                move_lift_hatch_high.start();
        }

        // Prevent accidental riser deployment:
        // To lower all risers, must hold the 'Y'(es) button on joystick.
        if (OI.isRiserAllDownPressed() && OI.isRiserEnabled())
            drop_all.start();
        // The second step, pulling the front back up which would on its own
        // lower the back is only permitted when the back was already down
        // as a result of the first step.
        if (OI.isRiserFrontUpPressed() && riser.isBackDown())
            rise_front.start();
        // Raising all back up is always permitted
        if (OI.isRiserAllUpPressed())
            reset_riser.start();
    }

    @Override
    public void teleopInit()
    {
        super.teleopInit();

        // Reading to clear any pending button presses
        OI.isToggleHeadingholdPressed();

        // Start driving by joystick
        joydrive.start();
    }

    @Override
    public void teleopPeriodic()
    {
        // .. and allow toggling between HH mode and plain joydrive
        updateJoystickDrivemode();
        handleButtonBoard();
    }

    @Override
    public void autonomousInit()
    {
        super.autonomousInit();

        // Reading to clear any pending button presses
        OI.isToggleHeadingholdPressed();

        // Start the selected option, which may be "Nothing"
        auto_options.getSelected().start();
        // Driving by joystick is initially off
    }

    @Override
    public void autonomousPeriodic()
    {
        // Test drive PID
        // if ((System.currentTimeMillis() / 5000) % 2 == 1)
        //     drivetrain.setPosition(24);
        // else
        //     drivetrain.setPosition(0);

        //Test lift PID
        // if ((System.currentTimeMillis() / 10000) % 2 == 1)
        //     lift.setHeight(12);
        // else
        //     lift.setHeight(24);

        // Pressing a button will start driving by joystick,
        // then toggle between plain and HH mode
        updateJoystickDrivemode();
        handleButtonBoard();
    }
}
