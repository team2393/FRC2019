package robot.demos.blacky;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.parts.USERButton;

public class Robot extends BasicRobot
{
    private final static double max_speed = 0.2;

    // Subsystems, Components
    private Wheels wheels = new Wheels();
    private Gyro gyro = new ADXRS450_Gyro();
    private Button user = new USERButton();

    // Commands
    private Command jog = new SmoothMove(wheels, 0.3, 0.6, 7.0);
    private Command forward = new Move(wheels, max_speed, -1);
    private Command left = new Turn(wheels, -max_speed/2, -1);
    private Command rock = new RocknRoll(wheels, max_speed, -1);
    private Command wiggle = new Wiggle(wheels, max_speed, -1);
    private Command hold = new HoldHeading(wheels, gyro);
    private Command blink = new Blink(RobotMap.DIO_LED, 0.3);

    @Override
    public void robotInit ()
    {
        super.robotInit();
        System.out.println("Left and right wheels connected to PWM " + RobotMap.PWM_LEFT + " resp. " + RobotMap.PWM_RIGHT);
        System.out.println("LED on DIO " + RobotMap.DIO_LED);

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
        moves.addSequential(new SmoothMove(wheels, 0.25, 1.0, 3.0));
        moves.addSequential(new SmoothMove(wheels, 0.5, 1.0, 5.0));
        moves.addSequential(new SmoothMove(wheels, 0.1, max_speed, 5.0));
        moves.addSequential(new Wiggle(wheels, 0.3, 2.0));
        moves.addSequential(new SmoothMove(wheels, 0.1, -max_speed/2, 3.0));
        moves.addSequential(new RocknRoll(wheels, 0.3, 3.0));
        moves.start();
    }

    // In teleop, use dashboard to trigger commands
}
