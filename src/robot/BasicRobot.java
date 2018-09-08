package robot;

import edu.wpi.first.wpilibj.TimedRobot;

/** Starting point for Team 2393 robots
 *  
 *  A 'timed' robot that displays the actual class name on startup,
 *  then does nothing in the various modes.
 *  
 *  To implement a functional robot,
 *  derive from this class and override the desired methods.
 *  
 *  To select which code to actually run on the roboRIO,
 *  set the `package` and `robot.class` in build.properties
 */
public class BasicRobot extends TimedRobot
{
    /** Called once on startup */
    @Override
    public void robotInit()
    {
        // Print the class name (which might be the name of the derived class).
        // This way we can see what's running on the roboRIO console.
        System.out.println("###################################################");
        System.out.println("########### Team 2393 " + getClass().getName());
        System.out.println("###################################################");
    }

    /** Called periodically in ANY mode */
    @Override
    public void robotPeriodic()
    {
    }

    /** Called when robot switches to DISABLED mode */
    @Override
    public void disabledInit()
    {
        System.out.println("Mode: Disabled");
    }
    
    /** Called periodically while DISABLED */
    @Override
    public void disabledPeriodic()
    {
        // Well, when we're disabled, we probably shouldn't do much...
    }

    /** Called when robot switches to AUTONOMOUS mode */
    @Override
    public void autonomousInit()
    {
        System.out.println("Mode: Auto-no-mouse");
    }

    /** Called periodically while in AUTONOMOUS mode */
    @Override
    public void autonomousPeriodic()
    {
    }

    /** Called when robot switches to TELEOP mode */
    @Override
    public void teleopInit()
    {
        System.out.println("Mode: Tele-op");
    }

    /** Called periodically while in TELEOP mode */
    @Override
    public void teleopPeriodic()
    {
    }

    /** Called when robot switches to TEST mode */
    @Override
    public void testInit()
    {
        System.out.println("Mode: Test");
    }

    /** Called periodically while in TEST mode */
    @Override
    public void testPeriodic()
    {
    }
}
