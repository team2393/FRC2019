package robot.demos;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.parts.PDPController;

/** Demo of PID position control with TalonSRX */
public class TalonPIDDemo extends BasicRobot
{
    private final WPI_TalonSRX tal = new WPI_TalonSRX(3);
    private final WPI_TalonSRX tal2 = new WPI_TalonSRX(8);

    private final Joystick joystick = new Joystick(0);

    private final PowerDistributionPanel panel = new PowerDistributionPanel(); //:)

    @Override
    public void robotInit()
    {
        super.robotInit();

        // You can also configure the Talon via the Tuner application,
        // but it's best to configure it from the code at robot initialization.
        // That way, if we later need to replace the actual Talon controller,
        // we only need to set the ID, and the rest will be configured on startup,
        // no need to do that manually in the Tuner.
        tal.configFactoryDefault();
        tal.setNeutralMode(NeutralMode.Coast); // Brake or coast?
        tal.setInverted(false);                // Invert?
        tal.configOpenloopRamp(3.0);           // Use N seconds to ramp to full speed

        // Use quad (relative) encoder
        tal.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
        // Does the sensor rotate in opposite direction compared to motor?
        tal.setSensorPhase(true);
        // Set full output range [-1, 1] or restrict it
        tal.configPeakOutputReverse(-0.5);
        tal.configPeakOutputForward(0.5);
        
        // PID Settings
        tal.configNeutralDeadband(0.04); // Default is 0.04
        tal.configClosedloopRamp(0.0);   // Use N seconds to ramp up to full PID output
        // To adjust PID gains, use Tuner,
        // then copy/paste the values in here
        tal.config_kP(0, 1.0);           // Proportional, 
        tal.config_kI(0, 0.0002);        // Integral,
        tal.config_kD(0, 10.0);          // Differential Gain. (manual suggests 10*kP)
        tal.config_IntegralZone(0, 80);
        // tal.configMaxIntegralAccumulator(0, 40000);
        tal.configClosedLoopPeakOutput(0, 1.0);
        
        // Add 'MotionMagic'.
        // MUST set kF = 1023 / max speed
        // with max speed in ticks per 100ms as shown in Tuner plot.
        final int max_speed = 8500;
        tal.config_kF(0, 1023.0/max_speed);
        // Set max speed to about 75% of max speed in tics/100ms
        tal.configMotionCruiseVelocity(max_speed*2/3);
        // Accelerate twice as fast to reach cruise velocity in half a second
        tal.configMotionAcceleration(2*max_speed*2/3);
        
        // Reset the sensor position
        tal.setSelectedSensorPosition(0);

        // The WPI_TalonSRX can be placed on the dashboard to show some basic info
        SmartDashboard.putData("Talon", tal);

        // Second motor is controlled by another Talong
        // which follows the first one
        tal2.configFactoryDefault();
        tal2.setInverted(false);                // Invert?
        tal2.follow(tal);
    }

    @Override
    public void robotPeriodic()
    {
        SmartDashboard.putNumber("Position", tal.getSelectedSensorPosition());
        SmartDashboard.putNumber("Current", panel.getTotalCurrent());
        SmartDashboard.putNumber("Error", tal.getClosedLoopError());
        SmartDashboard.putNumber("Integral", tal.getIntegralAccumulator());
    }

    @Override
    public void teleopPeriodic()
    {
        // Reset encoder to zero?
        if (joystick.getRawButtonPressed(PDPController.X_BUTTON))
            tal.setSelectedSensorPosition(0);
        
        if (joystick.getRawButton(PDPController.LEFT_FRONT_BUTTON) ||
            joystick.getRawButton(PDPController.RIGHT_FRONT_BUTTON))
        {
            // Set motor position from joystick
            // Joystick value is [-1, 1], making it positive when stick forward
            // Scale to [-4096, 4096] to get one revolution back/forward,
            // assuming encoder counts 4096 ticks per rev,
            // then scale to command -10 .. +10 turns
            final double pos = -joystick.getRawAxis(PDPController.RIGHT_STICK_VERTICAL) * 4096*2;

            if (joystick.getRawButton(PDPController.LEFT_FRONT_BUTTON))
                tal.set(ControlMode.Position, pos);
            else
                tal.set(ControlMode.MotionMagic, pos);

            // Display encoder position
            SmartDashboard.putNumber("Desired Position", pos);
        }
        else
        {
            // Set plain speed, not controlling position based on encoder
            tal.set(ControlMode.PercentOutput, -joystick.getRawAxis(PDPController.RIGHT_STICK_VERTICAL));
        }

        // If there are any faults, print them
        final Faults faults = new Faults();
        tal.getFaults(faults);
        if (faults.hasAnyFault())
            System.out.println(faults);
    
    }

    @Override
    public void autonomousInit()
    {
        super.autonomousInit();
        // At start of auto, set current position as "0".
        // This allows testing where pos. is manually set
        // while disabled, then start auto from that pos on.
        tal.setSelectedSensorPosition(0);
    }

    @Override
    public void autonomousPeriodic()
    {
        // Every 2 seconds, change from pos 0 to 1 turn (4096 encoder steps)
        if (((System.currentTimeMillis() / 4000) % 2) == 1)
            tal.set(ControlMode.MotionMagic, 0);
        else
            tal.set(ControlMode.MotionMagic, 20.0 * 4096);
    }
}