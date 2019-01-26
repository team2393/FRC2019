package robot.deepspace;

/** Hardware Mappings
 *  
 * One place to find what's connected how
 */ 
public class RobotMap
{
    // Solenoid
    public final static int GEARBOX_SOLENOID = 1;
    public final static int GRABBER_SOLENOID = 6;
    public final static int EXTEND_SOLENOID = 7;
    
    // Talon ID
    public final static int LIFT_MOTOR = 7;
    public final static int LEFT_MOTOR_FRONT = 1;
    public final static int LEFT_MOTOR_BACK = 3;
    public final static int RIGHT_MOTOR_FRONT = 2;
    public final static int RIGHT_MOTOR_BACK = 4;
    
    // DIO
    public final static int LIMIT_SWITCH = 4;
    public final static int HATCH_SENSOR = 3;
}
