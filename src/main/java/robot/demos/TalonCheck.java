package robot.demos;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.parts.PDPController;

/** Demo of basic speed control with TalonSRX */
public class TalonCheck extends BasicRobot
{
    private final PowerDistributionPanel panel = new PowerDistributionPanel(); //:)
    // TalonSRX offers full access to the controller.
    // WPI_TalonSRX wraps that to add the basic set(speed)
    // necessary for DifferentialDrive, in case we need that later.
    private final WPI_TalonSRX tal = new WPI_TalonSRX(3);

    private final Joystick joystick = new Joystick(0);

    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("Talon Firmware:");
        System.out.println(tal.getFirmwareVersion());

        // You can also configure the Talon via the Tuner application,
        // but it's best to configure it from the code at robot initialization.
        // That way, if we later need to replace the actual Talon controller,
        // we only need to set the ID, and the rest will be configured on startup,
        // no need to do that manually in the Tuner.
        tal.configFactoryDefault();
        tal.setNeutralMode(NeutralMode.Brake); // Brake or coast?
        tal.setInverted(false);                // Invert?
        tal.configOpenloopRamp(2.0);           // Use N seconds to ramp to full speed
        tal.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
          
        // Restrict output range because our power supply is weak
        tal.configPeakOutputReverse(-0.5);
        tal.configPeakOutputForward(0.5);

        // The WPI_TalonSRX can be placed on the dashboard to show some basic info
        SmartDashboard.putData("Talon", tal);
    }

    @Override
    public void teleopPeriodic()
    {
        // If joystick in in centerish, don't move
        double speed = - joystick.getRawAxis(PDPController.RIGHT_STICK_VERTICAL);
        if (Math.abs(speed) < 0.1)
            speed = 0;
        tal.set(speed);
        SmartDashboard.putNumber("Current", panel.getTotalCurrent());
        SmartDashboard.putNumber("Position", tal.getSelectedSensorPosition());
    }

    @Override
    public void autonomousPeriodic()
    {
    }
}