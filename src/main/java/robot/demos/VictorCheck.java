package robot.demos;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import robot.BasicRobot;

/** Demo of turning output of Pneumatic Control Module on/off */
public class VictorCheck extends BasicRobot
{
    private final int PCM_ID = 0;
    private final VictorSPX vic = new VictorSPX(1);
    
    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("Victor Firmware:");
        System.out.println(vic.getFirmwareVersion());
        vic.configFactoryDefault();
        vic.setNeutralMode(NeutralMode.Brake);
    }

    @Override
    public void teleopPeriodic()
    {
    }

    @Override
    public void autonomousPeriodic()
    {
    }
}