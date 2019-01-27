package robot.demos;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

/** Robot that displays the gyro heading
 * 
 *  With initial FRCUpdateSuite_2019.0.0,
 *  the gyro always reports 0.0 heading.
 *  Start git bash,
 *     ssh admin@172.22.11.2
 *     updateNIDrivers
 *  Takes 5 minutes,
 *  ends with WARNING: Could not unload ..
 *  and "Do you want to reboot?" prompt.
 *  Answer "yes"
 *
 *  Later FRCUpdateSuite supposedly fixes that.
 */
public class GyroRobot extends BasicRobot
{
    private final Gyro gyro = new ADXRS450_Gyro();

    private final Command reset = new InstantCommand(gyro::reset);

    @Override
    public void robotInit()
    {
        super.robotInit();
        System.out.println("Needs Gyro board in SPI");
        reset.setRunWhenDisabled(true);
        SmartDashboard.putData("Reset", reset);
    }

    @Override
	public void robotPeriodic()
	{
        Scheduler.getInstance().run();
        double heading = gyro.getAngle();
        // Basics of 'printf' style formatting:
        //
        //  %...   Format an argument
        //  %+6.1f Always include the + or - sign,
        //         format into 6 characters total,
        //         using 1 decimal after the '.',
        //         and the argument is a floating point number.
        //         Examples:
        //         "-179.3",
        //         "  +0.5"
        // System.out.format("Heading: %+6.1f degrees\n", heading);
        SmartDashboard.putNumber("Heading", heading);
	}
}
