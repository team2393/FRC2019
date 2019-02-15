package robot.deepspace;

/** Hardware Mappings
 *  
 *  One place to find what's connected how
 */ 
public class RobotMap
{
    // Solenoids =================================
    //Riser Solenoid
    public final static int RISER_PCM = 0;
    public final static int FRONT_RISER_SOLENOID = 5;
    public final static int BACK_RISER_SOLENOID = 4;
    // High/low speed gear
    public final static int GEARBOX_SOLENOID1 = 1;
    public final static int GEARBOX_SOLENOID2 = 2;
    // Hatch grabber open/close
    public final static int GRABBER_SOLENOID = 6;
    // Hatch grabber extend/retract
    public final static int EXTEND_SOLENOID = 7;
    
    // Talon IDs =================================
    // Lift up/down
    public final static int LIFT_MOTOR = 5;
    // Drivetrain motors
    public final static int LEFT_MOTOR_SLAVE = 4;
    public final static int LEFT_MOTOR_MAIN = 2;
    public final static int RIGHT_MOTOR_SLAVE = 3;
    public final static int RIGHT_MOTOR_MAIN = 1;

    // ANALOG INPUTS
    public final static int USONIC_TEST = 0;

    // PWM ports =================================
    public final static int RISER_MOTOR = 0;
    // Ball grabber
    public final static int CARGO_SPINNER1 = 1;
    public final static int CARGO_SPINNER2 = 2;

    // DIO ports =================================
    // Lift at bottom limit
    public final static int LIMIT_SWITCH = 0;
    // Ball detected
    public final static int CARGO_SENSOR = 1;
    // Hatch panel detected
    public final static int HATCH_SENSOR = 2;
}
