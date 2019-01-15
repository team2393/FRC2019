package robot.demos;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.parts.PDPController;

/** Demo of turning output of Pneumatic Control Module on/off */
public class VictorCheck extends BasicRobot
{
    private final int PCM_ID = 0;

    // VictorSPX offers full access to the controller.
    // WPI_VictorSPX wraps that to add the basic set(speed)
    // necessary for DifferentialDrive
    private final WPI_VictorSPX vic = new WPI_VictorSPX(1);

    private final Joystick joystick = new Joystick(0);

    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("Victor Firmware:");
        System.out.println(vic.getFirmwareVersion());

        // You can also configure the Victor via the Tuner application,
        // but it's best to configuret them from the code at robot initialization
        // because that allows swapping the actual Victor controller,
        // needing only to set the ID, and the rest is then configured in here.
        vic.configFactoryDefault();
        vic.setNeutralMode(NeutralMode.Brake); // Brake or coast?
        vic.setInverted(false);                // Invert?
        vic.configOpenloopRamp(2.0);           // Use N seconds to ramp to full speed
    }

    @Override
    public void teleopPeriodic()
    {
        final double speed = - joystick.getRawAxis(PDPController.RIGHT_STICK_VERTICAL);
        vic.set(speed);

        SmartDashboard.putNumber("Victor", vic.getSelectedSensorPosition());
    }

    @Override
    public void autonomousPeriodic()
    {
    }
}