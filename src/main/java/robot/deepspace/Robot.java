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
import edu.wpi.first.wpilibj.Joystick;
import robot.BasicRobot;
import robot.parts.PDPController;

/**
 *  Main robot class for deep space 2019
 * 
 *  TODO:
 *  Camera & Vision: Show video from front of robot,
 *  with overlay when target markers are detected.
 * 
 *  Drive motors: Left and right, 2 Talons each side, one follows the other, 1 encoder per side, gyro
 *  -> Need to program PID for movement with gyro to keep heading for autonomous moves
 * 
 *  Prepare autonomous moves from start N positions to M initial disk placements.
 *  Maybe leave last leg of route to driver, using vision, but get them close.
 * 
 *  Gearbox Shifter: 1 or 2 Solenoids, button to shift high <-> low, indicate current gear on dashboard
 *
 *  Disk grabber: 1 solenoid to hold/release disk, button to toggle
 * 
 *  Lift: 1 motor, encoder, limit switch, button box to move lift to ~4 predertermined heights
 *  -> Can use Talon PID & motion magic.
 * 
 *  Push-up mechanism: 1 solenoid for 2 front cylinders, 1 solenoid for back cylinder, 1 drive motor controller.
 *  Idea:
 *  Push button to lower 2 front and 1 back cylinder,
 *  and now bottom drive will move with other wheels forward/backward.
 *  Push button to raise 2 front cylinders back up, bottom drive still follows main wheels.
 *  Push button to raise back cylinder, bottom drive off.
 */
public class Robot extends BasicRobot
{
    private final Lift lift = new Lift();
    private final Joystick joystick = new Joystick(0);

    @Override
    public void robotInit()
    {
        super.robotInit();
        // this is where the camera streaming begins
        UsbCamera camera =  CameraServer.getInstance().startAutomaticCapture();
        //160 by 120 at 10fps - 0.3mbs
        //320 by 240 at 10fps - 0.9mbs  <--- Using this for now
        //640 by 480 at 10fps - 3.1mbs
        //640 by 480 at 15fps - 4.1mbs
        //610 by 450 at 15fps - 3.4mbs
        camera.setResolution(600, 440);
        camera.setFPS(10);
        
    }

    @Override
    public void teleopPeriodic()
    {
        lift.drive(-joystick.getRawAxis(PDPController.LEFT_STICK_VERTICAL));
    }

    @Override
    public void autonomousPeriodic()
    {
        if ((System.currentTimeMillis() / 3000) % 2 == 0)
            lift.setposition(0.0);
        else
            lift.setposition(30*4096.0);
    }
}
