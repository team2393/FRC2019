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
 *  Main robot class for deep space 2019
 * 
 *  TODO:
 *  Camera & Vision
 * 
 *  Drive motors: Left and right, 2 Talons each side, one follows the other, 1 encoder per side, gyro
 *  -> Need to program PID for movement with gyro to keep heading
 * 
 *  Gearbox Shifter: 1 or 2 Solenoids
 *
 *  Disk grabber: 1 solenoid to hold/release disk
 * 
 *  Lift: 1 motor, encoder, limit switch, button box to move lift to ~4 predertermined heights
 *  -> Can use Talon PID & motion magic
 * 
 *  Push-up mechanism: 1 solenoid for 2 front cylinders, 1 solenoid for back cylinder, 1 drive motor controller
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
