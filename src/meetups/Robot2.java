package meetups;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Servo;
import robot.BasicRobot;

// Robot that does a little bit on the various modes
public class Robot2 extends BasicRobot
{
    // Add variables for LED connected to DIO #9
    DigitalOutput led = new DigitalOutput(9);

    // ..and Servo connected to PMW #9
    Servo servo= new Servo(9);


    // Override the BasicRobot version of auto and teleop Init
    // to move servo and turn led on/off
    @Override
    public void autonomousInit()
    {
        super.autonomousInit();
        led.set(true);
        servo.setAngle(-45.0);
    }

    @Override
    public void teleopInit()
    {
        super.teleopInit();
        led.set(false);
        servo.setAngle(45.0);
    }

}
