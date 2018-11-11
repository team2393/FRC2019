package robot.demos;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.parts.ContinuousRotationServo;
import robot.parts.PDPController;

public class RadioShackChassisDemo extends BasicRobot
{
    private final ContinuousRotationServo left = new ContinuousRotationServo(1);
    private final ContinuousRotationServo right = new ContinuousRotationServo(0);

    private final Joystick joystick = new Joystick(0);



    @Override
    public void robotInit()
    {
        super.robotInit();
    }

    @Override
    public void robotPeriodic()
    {
        left.set(joystick.getRawAxis(PDPController.LEFT_STICK_VERTICAL));
        right.set(joystick.getRawAxis(PDPController.RIGHT_STICK_VERTICAL));

        SmartDashboard.putNumber("Left", left.get());
        SmartDashboard.putNumber("Right", right.get());
        super.robotPeriodic();
    }
}
