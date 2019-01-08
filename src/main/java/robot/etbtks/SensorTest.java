package robot.etbtks;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

/** Show light sensor reading in dashboard */
public class SensorTest extends BasicRobot
{
	AnalogInput leftlight = new AnalogInput(2);
	AnalogInput rightlight = new AnalogInput(3);
	
	@Override
	public void robotPeriodic()
	{
		SmartDashboard.putNumber("left light", leftlight.getVoltage());
		SmartDashboard.putNumber("right light", rightlight.getVoltage());
	}	
}
