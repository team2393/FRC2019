package robot.demos;

import edu.wpi.first.wpilibj.Joystick;
import robot.BasicRobot;

public class JoystickDemo1 extends BasicRobot
{
    private Joystick joystick = new Joystick(0);

    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("In teleop mode, move Joystick");
    }

    
    @Override
    public void teleopPeriodic()
    {
        double turn = joystick.getRawAxis(4);
        double speed = joystick.getRawAxis(5);
        
        System.out.println("Turn: " + turn + ", Speed: " + speed);
    }
}
