package robot.demos;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

public class EncoderRobot extends BasicRobot
{
    private Encoder encoder = new Encoder(0, 1);

    @Override
    public void robotInit()
    {
        super.robotInit();

        // Diameter: 67 mm
        // ==> circumference is 67*pi,
        // encoder model should give 400 ticks per revolution
        encoder.setDistancePerPulse(67*Math.PI / 400.0);
    }

    @Override
    public void robotPeriodic()
    {
        super.robotPeriodic();

        // reset number to 0 when user button is pressed
        if (RobotController.getUserButton() == true)
            encoder.reset();

        SmartDashboard.putNumber("ticks", encoder.get());
        SmartDashboard.putNumber("position", encoder.getDistance());
    }
}
