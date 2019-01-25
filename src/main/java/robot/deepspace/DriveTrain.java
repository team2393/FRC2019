/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot.deepspace;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Add your docs here.
 */
public class DriveTrain
{
    private final WPI_TalonSRX left = new WPI_TalonSRX(RobotMap.LEFT_MOTOR_FRONT);
    private final WPI_TalonSRX left_slave = new WPI_TalonSRX(RobotMap.LEFT_MOTOR_BACK);
    private final WPI_TalonSRX right = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR_FRONT);
    private final WPI_TalonSRX right_slave = new WPI_TalonSRX(RobotMap.RIGHT_MOTOR_BACK);
    private final DifferentialDrive drive = new DifferentialDrive(left, right);
    private double speed = 0;
    private double rotation = 0;

    public DriveTrain()
    {
        //Resets Everything To Default
        left.configFactoryDefault();
        left_slave.configFactoryDefault();
        right_slave.configFactoryDefault();
        right.configFactoryDefault();

        //Tie Slaves To Master
        left_slave.follow(left);
        right_slave.follow(right);
    }

    public void setSpeed (double speed)
    {
        this.speed = speed;
        drive.arcadeDrive(speed, rotation);
    }

    public void setRotation (double new_rotation)
    {
        rotation = new_rotation;
        drive.arcadeDrive(speed, rotation);
    }
}
