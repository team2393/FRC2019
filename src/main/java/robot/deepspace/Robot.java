/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot.deepspace;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
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
        UsbCamera camera =  CameraServer.getInstance().startAutomaticCapture();
        //160 by 120 at 10fps - 0.3mbs
        //640 by 480 at 10fps - 3.1mbs
        //640 by 480 at 15fps - 4.1mbs
        //610 by 450 at 15fps - 3.4mbs
        camera.setResolution(600, 440);
        camera.setFPS(10);
        
    }
}
