package robot.arm;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

public class JoystickArmControl extends Command
{
    private Arm arm;
    private Joystick joystick;

    public JoystickArmControl(Arm arm, Joystick joystick)
    {
        this.arm = arm;
        this.joystick = joystick;
        // requires(arm);
    }

    @Override
    public synchronized void start()
    {
        super.start();
        System.out.println("Arm control via Joystick");
        System.out.println("Front buttons : Open, close");
        System.out.println("Front levers  : Left, right");
        System.out.println("Left Joystick : Outer arm");
        System.out.println("Right Joystick: Inner arm");
        System.out.println("Green button  : Print waypoint");
        System.out.println("Red button    : Home");
    }

    @Override
    protected void execute()
    {
        if (joystick.getRawButtonPressed(1))
            System.out.format("        moves.addSequential(new MoveArm(arm, %.1f, %.1f, %.1f, %.1f));\n",
                              arm.getHand(),
                              arm.getInner(),
                              arm.getOuter(),
                              arm.getBase());

        if (joystick.getRawButton(2))
            arm.moveHome();

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

    @Override
    protected boolean isFinished()
    {
        return false;
    }
}
