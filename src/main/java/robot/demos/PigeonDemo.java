package robot.demos;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

public class PigeonDemo extends BasicRobot
{
    final private TalonSRX talon = new TalonSRX(5);
    final private PigeonIMU pigeon = new PigeonIMU(talon);

    @Override
    public void robotPeriodic()
    {
        double ypr[] = new double[3];
        pigeon.getYawPitchRoll(ypr);
        SmartDashboard.putNumber("All Y'all's yaw", ypr[0]);
        SmartDashboard.putNumber("Pitch", ypr[1]);
        SmartDashboard.putNumber("Roll", ypr[2]);

        short xyz[] = new short[3];
        pigeon.getBiasedAccelerometer(xyz);
        SmartDashboard.putNumber("X", xyz[0]);
        SmartDashboard.putNumber("Y", xyz[1]);
        SmartDashboard.putNumber("Z", xyz[2]);
    }
}
