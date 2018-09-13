package robot.demos;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

public class JoystickDemo2 extends BasicRobot
{
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
    }
}
