package robot.arm;

import edu.wpi.first.wpilibj.Joystick;
import robot.BasicRobot;

public class Robot extends BasicRobot
{
    private Arm arm = new Arm();
    private Joystick joystick = new Joystick(0);

    /** Called once on startup */
    @Override
    public void robotInit()
    {
        super.robotInit();

        System.out.println("Front buttons: Open, close");
        System.out.println("Front levers : Left, right");
        System.out.println("Left Joystick: Outer arm");
        System.out.println("Right Joystick: Inner arm");
        System.out.println("Red button    : Home");
    }

    @Override
    public void teleopPeriodic()
    {
        if (joystick.getRawButton(2))
            arm.home();

        if (joystick.getRawButton(5))
            arm.changeHand(0.5);
        else if (joystick.getRawButton(6))
            arm.changeHand(-0.5);

        if (joystick.getRawAxis(5) <= -0.5)
            arm.changeInner(0.25);
        else if (joystick.getRawAxis(5) >= 0.5)
            arm.changeInner(-0.25);

        if (joystick.getRawAxis(1) <= -0.5)
            arm.changeOuter(0.25);
        else if (joystick.getRawAxis(1) >= 0.5)
            arm.changeOuter(-0.25);

        if (joystick.getRawAxis(2) >= 0.5)
            arm.changeBase(0.25);
        else if (joystick.getRawAxis(3) >= 0.5)
            arm.changeBase(-0.25);

    }
}
