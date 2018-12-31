package robot.etbtks;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.parts.ContinuousRotationServo;
import robot.parts.PDPController;

/** Everything but the kitchen sink
 * 
 *  Drive via joystick
 *  Read encoder
 *  TODO Read gyro
 *  TODO Commands to drive certain distances
 *  TODO Commands to hold heading
 *  NOT TODO beep
 *  @author Anna
 */
public class Robot extends BasicRobot
{
	ContinuousRotationServo left = new ContinuousRotationServo(0);
	ContinuousRotationServo right = new ContinuousRotationServo(1);
	
	DifferentialDrive drive = new DifferentialDrive(left, right);
	
	Joystick joystick = new Joystick(0);
	
	Encoder encoder = new Encoder(2,1);
	
	public Robot()
	{
		left.setInverted(true);
		right.setInverted(false);
		// TODO it's about 10 cm per 190 pulses/ticks, but need to measure
		encoder.setDistancePerPulse(100.0/190.0);
	}
	
	@Override
	public void robotPeriodic()
	{
		super.robotPeriodic();
	}
	
	@Override
	public void autonomousInit()
	{
		super.autonomousInit();
	}
	
	@Override
	public void autonomousPeriodic() 
	{
		super.autonomousPeriodic();
	}
	
	@Override
	public void teleopInit() 
	{
		super.teleopInit();
	}
	
	@Override
	public void teleopPeriodic() 
	{
		if (joystick.getRawButtonPressed(PDPController.A_BUTTON))
			encoder.reset();
		
		// Joystick reports -1 for "full forward"
		drive.arcadeDrive(-joystick.getRawAxis(PDPController.RIGHT_STICK_VERTICAL),
				          joystick.getRawAxis(PDPController.RIGHT_STICK_HORIZONTAL));
		// System.out.println(encoder.getDistance() + "mm");
		SmartDashboard.putNumber("encoder", encoder.getDistance());
	}	
	
}
