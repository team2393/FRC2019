package robot.blacky;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import robot.parts.ContinuousRotationServo;

/** Hardware connections of the robot
 * 
 *  Placing all hardware connections of the robot in this one class
 *  simplifies tracking and managing them in the long run.
 */
public class RobotMap
{
    /** PWM used by left & right motor */
    public static ContinuousRotationServo left_motor = new ContinuousRotationServo(0),
                                          right_motor = new ContinuousRotationServo(1);
    public static DifferentialDrive drive = new DifferentialDrive(left_motor, right_motor);

    /** DIO used for LED */
    public static DigitalOutput led = new DigitalOutput(9);
    
    public static  Gyro gyro = new ADXRS450_Gyro();

    /** Print info about all the connections */
    public static void describe()
    {
        System.out.println("Left and right drive_subsys connected to PWM " + RobotMap.left_motor.getChannel() + " resp. " + RobotMap.right_motor.getChannel());
        System.out.println("LED on DIO " + RobotMap.led.getChannel());
        System.out.println("Gyro on SPI");
    }
}
