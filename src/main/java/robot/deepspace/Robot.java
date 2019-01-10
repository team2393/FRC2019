/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot.deepspace;

import edu.wpi.first.cameraserver.CameraServer;
import robot.BasicRobot;

/**
 * main robot class for deep space 2019
 */
public class Robot extends BasicRobot
{
    @Override
    public void robotInit()
    {
        super.robotInit();
        // this is where the camera streaming begins
        CameraServer.getInstance().startAutomaticCapture();
    }
}
