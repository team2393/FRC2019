package meetups;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.parts.ContinuousRotationServo;

public class Robot3 extends BasicRobot
{
	private static final int Integral_Limit = 20;
	// Variables to control the left/right motor
    ContinuousRotationServo left = new ContinuousRotationServo(1);
    ContinuousRotationServo right = new ContinuousRotationServo(0);

    // Wrap the motors into a 'Drive' that handles the details
    // of converting forward/backward and turn into left/right motor movement.
    DifferentialDrive  drive = new DifferentialDrive(left, right);

    // Joystick (used in teleop)
    Joystick joystick = new Joystick(0);

    // Gyto (used in autonomous)
    Gyro gyro = new ADXRS450_Gyro();
    double desiredheading = 0.0;
    double integral = 0.0;
    private static final double P_Gain = 0.01;
    private static final double I_Gain = 0.01;

    @Override
    public void autonomousPeriodic()
    {
        // Try to keep robot at desired heading
        double heading = gyro.getAngle();

        // Assume for example an error of 45 degrees
        double error = desiredheading - heading;
        integral += error;
        if(integral > Integral_Limit) 
        	integral = Integral_Limit;
        if(integral < -Integral_Limit)
        	integral = -Integral_Limit;
        // ==> We'd turn by 0.5, half speed
        double turn = P_Gain*error + I_Gain * integral;
        if(Math.abs(turn) < 0.05)
        	turn = 0;
        System.out.println(error);
        drive.arcadeDrive(0, turn);
        SmartDashboard.putNumber("Error", error);
        SmartDashboard.putNumber("Integral", integral);
        
    }

    @Override
    public void teleopPeriodic()
    {
        // Move as instructed by joystick
        // Note forward/backward need to be corrected
        drive.arcadeDrive(-joystick.getRawAxis(5), joystick.getRawAxis(4));
    }
}
