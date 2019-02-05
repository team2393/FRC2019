package robot.deepspace.grabber;

import edu.wpi.first.hal.sim.mockdata.AnalogOutDataJNI;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
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
    private final AnalogInput sonic_test = new AnalogInput(RobotMap.USONIC_TEST);

    //TODO 2 motors for intake wheels 
    //TODO One sensor in intake, should stop motors once cargo is detected
    //TODO Eject motor

    public Grabber() 
    {
        open(false);
        extend(false);
    }

    @Override
    protected void initDefaultCommand()
    {
        //Nothing To Do Here
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

    public boolean isCargoDetected()
    {
        return !cargo_sensor.get();
    }

    @Override
    public void periodic()
    {
        SmartDashboard.putNumber("Sonic Test", sonic_test.getVoltage());
        SmartDashboard.putBoolean("Cargo Sensor", isCargoDetected());
        SmartDashboard.putBoolean("Hatch Sensor", isHatchDetected());
    }   
}
