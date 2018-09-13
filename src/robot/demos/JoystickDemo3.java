package robot.demos;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.parts.ContinuousRotationServo;

public class JoystickDemo3 extends BasicRobot
{
    private ContinuousRotationServo left = new ContinuousRotationServo(0);
    private ContinuousRotationServo right = new ContinuousRotationServo(1);
    final DifferentialDrive drive = new DifferentialDrive(left, right);
    private Joystick joystick = new Joystick(0);

    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("In teleop mode, move Joystick and watch on Dashboard");
    }

    
    @Override
    public void teleopPeriodic()
    {
        double turn = joystick.getRawAxis(4);
        double speed = joystick.getRawAxis(5);
        SmartDashboard.putNumber("Turn", turn);
        SmartDashboard.putNumber("Speed", speed);
        drive.arcadeDrive(speed, turn);
    }
}
