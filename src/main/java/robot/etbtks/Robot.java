package robot.etbtks;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.parts.ContinuousRotationServo;
import robot.parts.PDPController;

/** Everything but the kitchen sink
 * 
 *  Drive via joystick
 *  Read encoder
 *  Commands to drive certain distances
 *  Read gyro
 *  Commands to hold heading
 *  Should Use Commands
 *  Should NOT beep
 *  @author Anna
 */
@SuppressWarnings("all")
public class Robot extends BasicRobot
{
	ContinuousRotationServo left = new ContinuousRotationServo(0);
	ContinuousRotationServo right = new ContinuousRotationServo(1);
	
	DifferentialDrive drive = new DifferentialDrive(left, right);
	
	Joystick joystick = new Joystick(0);
	
	Encoder encoder = new Encoder(2,1);
	
	Gyro gyro = new ADXRS450_Gyro();
	double integral = 0;
	double hedtegral = 0;

	
	public Robot()
	{
		left.setInverted(true);
		right.setInverted(false);
		// It's about 10 cm per 190 pulses/ticks, but need to measure
		encoder.setDistancePerPulse(100.0/190.0);
	}
	
	@Override
	public void robotPeriodic()
	{
		super.robotPeriodic();
		// always publishes coder position
		SmartDashboard.putNumber("encoder", encoder.getDistance());
		SmartDashboard.putNumber("heading", gyro.getAngle());

	}
	
	@Override
	public void autonomousInit()
	{
		super.autonomousInit();
	}
	
	@Override
	public void autonomousPeriodic() 
	{
		double desired_distance = 50.0; 
		// Proportional gain
		double p_gain = 0.015;
		
		// Integral limit and gain
		double limit = 20;
		double i_gain = 0.015;

		double actual_distance = encoder.getDistance();
		double error = desired_distance - actual_distance;
		integral = integral + error;
		if (integral > limit)
			integral = limit;
		if (integral < -limit)
			integral = -limit;
		
		double speed = p_gain * error   + i_gain * integral; 

		double desired_heading = 0;		
		
		// Proportional gain and limit
		double rot_p_gain = 0.02;
		double rot_limit = 15;

		double heading = gyro.getAngle(); 
		double rot_error = desired_heading - heading; 
		hedtegral = hedtegral + rot_error; 		
		if (hedtegral > rot_limit)
			hedtegral = rot_limit;
		if (hedtegral < -rot_limit)
			hedtegral = -rot_limit;
		
		double rotation = rot_p_gain * rot_error + i_gain * hedtegral;
		
		drive.arcadeDrive(speed, rotation);	
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
		{
			encoder.reset();
			gyro.reset();
		}
		
		// Joystick reports -1 for "full forward"
		drive.arcadeDrive(-joystick.getRawAxis(PDPController.RIGHT_STICK_VERTICAL),
				          joystick.getRawAxis(PDPController.RIGHT_STICK_HORIZONTAL));
		// System.out.println(encoder.getDistance() + "mm");
	
	}	
	
}
