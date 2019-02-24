package robot.deepspace.grabber;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.deepspace.RobotMap;

/** Grabber for the hatch panel (disk)
 *  with solenoid to open/close.
 *  Sensor detects if hatch is on grabber.
 * 
 *  For cargo (ball), two spinner motors to pull
 *  ball in or push out.
 *  Sensor to detect ball.
 */
public class Grabber extends Subsystem
{
    private final Solenoid grabber = new Solenoid(RobotMap.GRABBER_SOLENOID);
    private final Solenoid extender = new Solenoid(RobotMap.EXTEND_SOLENOID);
    private final DigitalInput hatch_sensor = new DigitalInput(RobotMap.HATCH_SENSOR);
    private final DigitalInput cargo_sensor = new DigitalInput(RobotMap.CARGO_SENSOR);
    // private final AnalogInput sonic_test = new AnalogInput(RobotMap.USONIC_TEST);
    private final Victor intake_spinner1 = new Victor(RobotMap.CARGO_SPINNER1);
    private final Victor intake_spinner2 = new Victor(RobotMap.CARGO_SPINNER2);
    
    /** Desired speed for the 2 intake spinners
     *  setSpinnerSpeed() updates this value,
     *  and in periodic() we keep sending it to the motors.
     */
    private double speed = 0;

    public Grabber() 
    {
        open(false);
        extend(false);
    }

    @Override
    protected void initDefaultCommand()
    {
        // Nothing To Do Here
    }

    public boolean isOpen()
    {
        return grabber.get();
    }

    /** Open/close
     *  @param should_open true to open, false to close
     */
    public void open(final boolean should_open)
    {
        grabber.set(should_open);
        SmartDashboard.putBoolean("Grabber Is Open", should_open);
    }

    /** @param do_extend true to extend, false to pull in */
    public void extend(final boolean do_extend)
    {
        extender.set(do_extend);
        SmartDashboard.putBoolean("Grabber Extended", do_extend);
    }

    /** @return true when hatch detected */
    public boolean isHatchDetected()
    {
        return !hatch_sensor.get();
    }

    /** @return true when cargo detected */
    public boolean isCargoDetected()
    {
        return cargo_sensor.get();
    }

    /** Set spinner speed
     *  
     *  Motors then keep running at that speed,
     *  no need to continuously call this method.
     *  @param speed Desired speed
     */
    public void setSpinnerSpeed(double speed)
    {
        this.speed = speed;
    }
    
    @Override
    public void periodic()
    {
        intake_spinner1.set(speed);
        intake_spinner2.set(speed);
        // SmartDashboard.putNumber("Sonic Test", sonic_test.getVoltage());
        SmartDashboard.putBoolean("Cargo Sensor", isCargoDetected());
        SmartDashboard.putBoolean("Hatch Sensor", isHatchDetected());
    }   
}
