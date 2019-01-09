package robot.demos;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import robot.BasicRobot;
import robot.commands.HoldHeading;
import robot.parts.ContinuousRotationServo;
import robot.subsystems.DriveSubsystem;

public class JoystickDemo5 extends BasicRobot
{
    // Wrap the motors into a DriveSubsystem
    final DifferentialDrive drive = new DifferentialDrive(new ContinuousRotationServo(0), new ContinuousRotationServo(1));
    private DriveSubsystem drive_subsys = new DriveSubsystem(drive);
    
    // Use gryro
    private Gyro gyro = new ADXRS450_Gyro();
    
    // Joystick
    private Joystick joystick = new Joystick(0);
    
    // Instead of using the color buttons, we could also use the "POV" control of the joystick
    // to set 8 different headings...
    private Button north = new JoystickButton(joystick, 4);
    private Button south = new JoystickButton(joystick, 1);
    private Button east = new JoystickButton(joystick, 2);
    private Button west = new JoystickButton(joystick, 3);
    
    // Commands
    private HoldHeading hold_heading = new HoldHeading(drive_subsys, gyro);
     
    private double initial_heading;
    
    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("In teleop mode, use colored Joystick buttons to set heading");
    }
    
    @Override
    public void robotPeriodic()
    {
        // Run scheduler in any mode
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit()
    {
        // In teleop, hold the heading
        hold_heading.start();
        // Remember initial heading
        initial_heading = hold_heading.getDesiredHeading();
    }

    @Override
    public void teleopPeriodic()
    {
        // Use buttons to update desired heading,
        // relative to initial heading
        if (north.get())
            hold_heading.setDesiredHeading(initial_heading);
        else if (west.get())
            hold_heading.setDesiredHeading(initial_heading-90.0);
        else if (east.get())
            hold_heading.setDesiredHeading(initial_heading+90.0);
        else if (south.get())
            hold_heading.setDesiredHeading(initial_heading+180.0);
    }
}
