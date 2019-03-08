package robot.demos;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import robot.BasicRobot;

/** Demo of basic TalonSRX move with encoder display */
public class TalonEncoderTest extends BasicRobot
{
    private final WPI_TalonSRX tal = new WPI_TalonSRX(3-1);

    @Override
    public void robotInit()
    {
        super.robotInit();
        tal.configFactoryDefault();
        tal.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        tal.configOpenloopRamp(0.5, 0);
    }

    @Override
    public void autonomousPeriodic()
    {
        if (((System.currentTimeMillis() / 3000) % 2) == 0)
            tal.set(0.5);
        else
            tal.set(-0.5);
    }
}