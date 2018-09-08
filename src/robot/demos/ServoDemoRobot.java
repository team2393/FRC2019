package robot.demos;

import edu.wpi.first.wpilibj.Servo;
import robot.BasicRobot;
import robot.USERButton;

/** Robot that moves a servo */
public class ServoDemoRobot extends BasicRobot
{
    /** Servo on channel 9 */
    // Using 9 because that's right next to the "S 6V Ground" label on the roboRIO
    private Servo servo = new Servo(9);

    /** We move the servo 'up' from pos 0.0 to 1.0,
     *  then from 1.0 back to 0.0 moving 'down',
     *  and so on, each time going by 'step_size'.
     */
    private double step_size = 0.01;
    private double pos = 0.0;
    private boolean move_up = true;
    
    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("Moves servo on PWM " + servo.getChannel() + " in AUTONOMOUS mode.");
        System.out.println("Push USER button to see servo position.");
    }

    @Override
    public void autonomousPeriodic()
    {
        // Tell servo where it should be
        servo.set(pos);
        
        // If USER button is pressed, display the current position
        if (USERButton.isPressed())
            System.out.println("Servo at " + pos);
        
        // Prepare next servo position
        if (move_up)
        {
            pos += step_size;
            if (pos > 1.0)
            {   // Reached upper end, switch to moving down
                pos = 1.0;
                move_up = false;
            }
        }
        else
        {   // Reached lower end, switch to moving up
            pos -= step_size;
            if (pos < 0.0)
            {
                pos = 0.0;
                move_up = true;
            }
        }
    }
}
