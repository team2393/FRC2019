package robot.demos;

import edu.wpi.first.wpilibj.DigitalOutput;
import robot.BasicRobot;
import robot.parts.USERButton;

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
        // No matter which mode, turn LED 'on' when USER button is pressed
        led.set(USERButton.isPressed());
        
        // To only do this in tele-op mode,
        // replace robotPeriodic() with teleopPeriodic()
        // To only do this in autonomous mode,
        // replace robotPeriodic() with autonomousPeriodic().
    }    
}
