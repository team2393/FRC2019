package robot.demos;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.parts.PDPController;

/** Demo of basic speed control with VictorSPX */
public class VictorCheck extends BasicRobot
{
    // VictorSPX offers full access to the controller.
    // WPI_VictorSPX wraps that to add the basic set(speed)
    // necessary for DifferentialDrive, in case we need that later.
    private final WPI_VictorSPX vic = new WPI_VictorSPX(1);

    private final Joystick joystick = new Joystick(0);

    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("Victor Firmware:");
        System.out.println(vic.getFirmwareVersion());

        // You can also configure the Victor via the Tuner application,
        // but it's best to configure it from the code at robot initialization.
        // That way, if we later need to replace the actual Victor controller,
        // we only need to set the ID, and the rest will be configured on startup,
        // no need to do that manually in the Tuner.
        vic.configFactoryDefault();
        vic.setNeutralMode(NeutralMode.Brake); // Brake or coast?
        vic.setInverted(false);                // Invert?
        vic.configOpenloopRamp(2.0);           // Use N seconds to ramp to full speed

        // The WPI_VictorSPX can be placed on the dashboard to show some basic info
        SmartDashboard.putData("Victor", vic);
    }

    @Override
    public void teleopPeriodic()
    {
        final double speed = - joystick.getRawAxis(PDPController.RIGHT_STICK_VERTICAL);
        vic.set(speed);
    }

    @Override
    public void autonomousPeriodic()
    {
    }
}