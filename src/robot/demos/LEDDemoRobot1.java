package robot.demos;

import edu.wpi.first.wpilibj.DigitalOutput;
import robot.BasicRobot;
import robot.USERButton;

/** Start of every hardware demo: LED
 * 
 *  Turn LED on when USER button is pressed
 */
public class LEDDemoRobot1 extends BasicRobot
{
    /** LED on DIO channel 9 */
    // Using 9 because that's right next to the "Ground 5V S" label on the roboRIO
    private DigitalOutput led = new DigitalOutput(9);
    
    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("LED on DIO " + led.getChannel());
        System.out.println("Push USER button.");
    }

    @Override
    public void robotPeriodic()
    {
        // Turn 'on' when USER button is pressed
        led.set(USERButton.isPressed());
    }    
}
