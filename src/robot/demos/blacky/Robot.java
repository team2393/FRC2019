package robot.demos.blacky;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

public class Robot extends BasicRobot
{
    private SpeedController left_motor = new ContinuousRotationServo(RobotMap.PWM_LEFT);
    private SpeedController right_motor = new ContinuousRotationServo(RobotMap.PWM_RIGHT);
    // DifferentialDrive drive = new DifferentialDrive(left_motor, right_motor);
    
    @Override
    public void robotInit()
    {
        super.robotInit();
        SmartDashboard.setDefaultNumber("speed", 0.0);
    }
    
    @Override
    public void disabledInit()
    {
//        left_motor.set(0.0);
    }

    @Override
    public void robotPeriodic()
    {
        left_motor.set(SmartDashboard.getNumber("speed", 0.0));
    }
}
