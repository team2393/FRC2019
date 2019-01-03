package robot.etbtks;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
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
 *  Use Commands
 *  NOT TODO beep
 *  @author Anna
 */
public class CommandRobot extends BasicRobot
{
	ContinuousRotationServo left = new ContinuousRotationServo(0);
	ContinuousRotationServo right = new ContinuousRotationServo(1);
	
	DifferentialDrive drive = new DifferentialDrive(left, right);
	
	Joystick joystick = new Joystick(0);
	
	Encoder encoder = new Encoder(2,1);
	
	Gyro gyro = new ADXRS450_Gyro();
	
	Command pos1 = new MoveAndTurnCommand(0, 100, encoder, gyro, drive);
	CommandGroup moves = new CommandGroup();
	
	public CommandRobot()
	{
		left.setInverted(true);
		right.setInverted(false);
		// TODO it's about 10 cm per 190 pulses/ticks, but need to measure
		encoder.setDistancePerPulse(100.0/190.0);
		
		moves.addSequential(new MoveAndTurnCommand(0, 300, encoder, gyro, drive));
		moves.addSequential(new MoveAndTurnCommand(90, 300, encoder, gyro, drive));
		moves.addSequential(new MoveAndTurnCommand(90, 600, encoder, gyro, drive));
	}
	
	@Override
	public void robotPeriodic()
	{
		super.robotPeriodic();
		// to use commands, we must run the scheduler 
		Scheduler.getInstance().run();
		// always publishes coder position
		SmartDashboard.putNumber("encoder", encoder.getDistance());
		SmartDashboard.putNumber("heading", gyro.getAngle());

	}
	
	@Override
	public void autonomousInit()
	{
		super.autonomousInit();
		moves.start();
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
