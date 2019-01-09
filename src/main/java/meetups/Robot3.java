package meetups;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.parts.ContinuousRotationServo;

// Robot that drives with joystick and holds heading
public class Robot3 extends BasicRobot
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
    double desiredheading = 0.0;
    private static final double P_Gain = 0.01;

    @Override
    public void autonomousPeriodic()
    {
        // Try to keep robot at desired heading
        double heading = gyro.getAngle();

        // Assume for example an error of 50 degrees
        double error = desiredheading - heading;
        // ==> We'd turn by 0.5, half speed
        double turn = P_Gain*error;

        drive.arcadeDrive(0, turn);

        // Display the error in console and dashboard
        System.out.println(error);
        SmartDashboard.putNumber("Error", error);
    }

    @Override
    public void teleopPeriodic()
    {
        // Move as instructed by joystick
        // Note forward/backward need to be corrected
        drive.arcadeDrive(-joystick.getRawAxis(5), joystick.getRawAxis(4));
    }
}
