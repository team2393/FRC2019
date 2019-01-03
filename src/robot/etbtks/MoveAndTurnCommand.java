package robot.etbtks;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class MoveAndTurnCommand extends Command 
{
	private final double desired_heading;
	private final double desired_distance;
	private final Encoder encoder;
	private final Gyro gyro;
	private final DifferentialDrive drive;
	
	// Proportional gain
	private final double p_gain = 0.015;
	
	// Integral limit and gain
	private final double limit = 20;
	private final double i_gain = 0.015;
	private double integral = 0;
	private final double rot_p_gain = 0.02;
	private final double rot_limit = 15;
	private double hedtegral = 0;
	private boolean done = false;

	
	public MoveAndTurnCommand(double the_heading, double the_distance,
			                  Encoder the_encoder, Gyro the_gyro, DifferentialDrive the_drive)
	{
		desired_heading = the_heading;
		desired_distance = the_distance;
		encoder = the_encoder; 
		gyro = the_gyro;
		drive = the_drive; 
	}
	
	protected void execute()
	{
		double actual_distance = encoder.getDistance();
		double error = desired_distance - actual_distance;
		integral = integral + error;
		if (integral > limit)
			integral = limit;
		if (integral < -limit)
			integral = -limit; 
		double speed = p_gain * error + i_gain*integral;
		
		double heading = gyro.getAngle();
		double rot_error = desired_heading - heading;
		hedtegral = hedtegral  + rot_error;
		if (hedtegral > rot_limit)
			hedtegral = rot_limit;
		if (hedtegral < -rot_limit)
			hedtegral = -rot_limit;
		
		double rotation = rot_p_gain * rot_error + i_gain * hedtegral;
		drive.arcadeDrive(speed, rotation);
		
		if (Math.abs(error) < 5    &&   Math.abs(rot_error) < 5)
			done = true;
	}

	@Override
	protected boolean isFinished()
	{
		return done;
	}
	
	protected void end()
	{
		// Shut off motor
		drive.arcadeDrive(0, 0);
	}
}
