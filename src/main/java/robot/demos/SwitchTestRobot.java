/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot.demos;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

/**
 * Add your docs here.
 */
public class SwitchTestRobot extends BasicRobot
{
    DigitalInput limit_switch = new DigitalInput(4);

    @Override
    public void teleopPeriodic()
    {
        SmartDashboard.putBoolean("Limit Switch", limit_switch.get());
    }
}
