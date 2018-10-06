package robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import robot.subsystems.DriveSubsystem;

/** Command to set heading based on Joystick POV
 *
 *  On start, fetch current heading from gyro
 *  and use that as the "zero" heading.
 *  From then on, use proportional gain to hold heading.
 */
public class POVHeading extends Command
{
    private final DriveSubsystem drive_subsys;
    private final Gyro gyro;
    private final Joystick joystick;

    // Gain for correcting error.
    private double P = 0.01;
    private double start_heading, desired_heading;

    public POVHeading(DriveSubsystem drive_subsys, Gyro gyro, Joystick joystick)
    {
        this.drive_subsys = drive_subsys;
        this.gyro = gyro;
        this.joystick = joystick;
    }

    @Override
    protected void initialize()
    {
        start_heading = desired_heading = gyro.getAngle();
    }

    @Override
    protected void execute()
    {
        double heading = gyro.getAngle();

        int pov = joystick.getPOV();
        if (pov < 0)
            desired_heading = start_heading;
        else
        {
            if (pov > 180)
                pov -= 360;
            desired_heading = start_heading + pov;
        }
        double error = desired_heading - heading;
        drive_subsys.turn(P * error);
    }

    @Override
    protected boolean isFinished()
    {
        // Don't finish (but may be cancelled or interrupted)
        return false;
    }

    @Override
    protected void end()
    {
        drive_subsys.turn(0.0);
    }
}
