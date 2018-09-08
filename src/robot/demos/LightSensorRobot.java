package robot.demos;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import robot.BasicRobot;
import robot.USERButton;

/** Read a Light Sensor
 *  
 *      +5V
 *       |
 *  Photo Resistor
 *       |
 *       o---------> Analog Input
 *       |
 *       |
 *     Ground
 *     
 *  Turns LED on when there's no light detected.
 */
public class LightSensorRobot extends BasicRobot
{
    /** Sensor on analog in channel 3 */
    // Using 3 because that's right next to the "S 5V Ground" label on the roboRIO
    private AnalogInput sensor = new AnalogInput(3);
    
    /** LED on DIO channel 9 */
    // Using 9 because that's right next to the "Ground 5V S" label on the roboRIO
    private DigitalOutput led = new DigitalOutput(9);
    
    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("Light Sensor on Analog In " + sensor.getChannel());
        System.out.println("LED on DIO " + led.getChannel());
        System.out.println("Push USER button to print sensor voltage.");
    }

    @Override
    public void robotPeriodic()
    {
        double voltage = sensor.getVoltage();

        // When it's dark, turn the LED on
        led.set(voltage < 3.0);

        if (USERButton.isPressed())
            System.out.println("Sensor Voltage: " + voltage);
    }    
}
