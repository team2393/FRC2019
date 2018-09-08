package robot.demos;

import edu.wpi.first.wpilibj.DigitalOutput;
import robot.BasicRobot;
import robot.USERButton;

/** Blink LED in AUTONOMOUS mode,
 *  turn on when USER button pressed in TELEOP
 */
public class LEDDemoRobot2 extends BasicRobot
{
    /** LED on DIO channel 9 */
    // Using 9 because that's right next to the "Ground 5V S" label on the roboRIO
    private DigitalOutput led = new DigitalOutput(9);
    
    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("LED on DIO " + led.getChannel() + " in TELEOP and AUTONOMOUS mode.");
        System.out.println("Push USER button in TELEOP.");
    }

    @Override
    public void autonomousPeriodic()
    {
        long now = System.currentTimeMillis();
        
        // Change LED every 500ms, i.e. every half second
        long period = now / 500;
        
        // Turn 'on' for every even multiple of period, then 'off' for every odd period
        boolean on = period % 2  == 0;
        led.set(on);
    }

    @Override
    public void teleopPeriodic()
    {
        // Turn 'on' when USER button is pressed
        led.set(USERButton.isPressed());
    }    
}
