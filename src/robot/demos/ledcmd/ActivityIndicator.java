package robot.demos.ledcmd;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** ActivityIndicator Subsystem
 * 
 *  Shows when we're 'Active' by both lighting an LED
 *  and updating a dashboard tag.
 */
class ActivityIndicator extends Subsystem
{
    // A Subsystem tends to handle one or more physical parts of the robot.
    // This subsystem includes an LED
    private DigitalOutput led = new DigitalOutput(RobotMap.DIO_LED);

    public ActivityIndicator()
    {
        // Initially, we're not active
        setActive(false);
    }
    
    @Override
    protected void initDefaultCommand()
    {
        // There is no default command
    }
    
    // A subsystem tends to have methods for controlling it.
    // These methods are called by Commands
    
    /** Set the activity indicator on or off
     *  @param active Indicate that we're active?
     */
    public void setActive(boolean active)
    {
        led.set(active);
        SmartDashboard.putBoolean("Active", active);
    }
}