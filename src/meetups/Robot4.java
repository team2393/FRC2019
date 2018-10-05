package meetups;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.parts.ContinuousRotationServo;

// Add integral gain to improve heading hold
public class Robot4 extends BasicRobot
{
	// Variables to control the left/right motor
    ContinuousRotationServo left = new ContinuousRotationServo(1);
    ContinuousRotationServo right = new ContinuousRotationServo(0);

    // Wrap the motors into a 'Drive' that handles the details
    // of converting forward/backward and turn into left/right motor movement.
    DifferentialDrive  drive = new DifferentialDrive(left, right);

    // Joystick (used in teleop)
    Joystick joystick = new Joystick(0);

    // Gyro (used in autonomous)
    Gyro gyro = new ADXRS450_Gyro();
    private static final double P_Gain = 0.01;
    private static final double I_Gain = 0.01;
    private static final int Integral_Limit = 20;
    double desiredheading = 0.0;
    double integral = 0.0;

    @Override
    public void autonomousPeriodic()
    {
        // Try to keep robot at desired heading
        double heading = gyro.getAngle();

        // Compute error
        double error = desiredheading - heading;

        // Accumulate error readings for integral
        integral += error;
        // Keep integral from getting too large
        if(integral > Integral_Limit)
        	integral = Integral_Limit;
        // .. same for negative integral
        if(integral < -Integral_Limit)
        	integral = -Integral_Limit;

        // P-I control
        double turn = P_Gain*error + I_Gain * integral;

        // If there's really no need to turn, don't turn at reduce motor wear
        if(Math.abs(turn) < 0.05)
        	turn = 0;
        drive.arcadeDrive(0, turn);

        // Display/publish some data
        System.out.println(error);
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
