package robot.demos;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import robot.BasicRobot;
import robot.parts.ContinuousRotationServo;
import robot.parts.USERButton;

/** Robot that moves with left/right motor */
public class DriveDemoRobot extends BasicRobot
{
    // Connect to each motor
    private ContinuousRotationServo left = new ContinuousRotationServo(0);
    private ContinuousRotationServo right = new ContinuousRotationServo(1);
    
    // Connect motors to a 'differential' drive
    private DifferentialDrive drive = new DifferentialDrive(left, right);
    
    /** Speed for going reverse, stop, forward -1.0, 0.0, -1.0 */
    private double speed = 0.0;
    /** Turn left, not at all, right -1, 0, +1 */
    private double turn = 0.0;
    
    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("Left/right servo drives on PWM " + left.getChannel() + ", " + right.getChannel());
        System.out.println("Push USER button to move/rotate in auto resp. teleop");
        // Because of the way the demo robot is wired, the motors need to be reversed
        left.setInverted(true);
        right.setInverted(true);
    }
  
    @Override
    public void robotPeriodic()
    {
        // The motor software always wants to be updated,
        // otherwise it emergency-stops the motors and prints MotorSafetyHelper warnings.
        drive.arcadeDrive(speed, turn);
    }
  
    @Override
    public void autonomousPeriodic()
    {
        // If USER button is pressed, move forward
        if (USERButton.isPressed())
            speed = 0.3;
        else
            speed = 0.0;
    }

    @Override
    public void teleopPeriodic()
    {
        // If USER button is pressed, rotate right
        if (USERButton.isPressed())
            turn = 0.3;
        else
            turn = 0.0;
    }
    
    // In 'disabled' mode, the motors are automatically stopped,
    // even if we keep asking it to move in robotPeriodic()
}
