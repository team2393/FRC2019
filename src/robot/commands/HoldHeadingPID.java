package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.subsystems.DriveSubsystem;

/** Command to hold current heading
 *
 *  On start, fetch current heading from gyro
 *  and use that as the desired heading.
 *  From then on, use proportional gain to hold heading.
 */
public class HoldHeadingPID extends Command
{
    private final DriveSubsystem drive_subsys;
    private final Gyro gyro;

    // Gain for correcting error.
    private double desired_heading, integral = 0.0;

    public HoldHeadingPID(DriveSubsystem drive_subsys, Gyro gyro)
    {
        this.drive_subsys = drive_subsys;
        this.gyro = gyro;
        doesRequire(drive_subsys);

        SmartDashboard.setDefaultNumber("P", 0.01);
        SmartDashboard.setDefaultNumber("I", 0.001);
        SmartDashboard.setDefaultNumber("Imax", 90);
    }

    @Override
    protected void initialize()
    {
        setDesiredHeading(gyro.getAngle());
    }

    public void setDesiredHeading(double heading)
    {
        desired_heading = heading;
        integral = 0.0;
    }

    public double getDesiredHeading()
    {
        return desired_heading;
    }

    @Override
    protected void execute()
    {
        double P = SmartDashboard.getNumber("P", 0.01);
        double I = SmartDashboard.getNumber("I", 0.001);
        double integral_limit = SmartDashboard.getNumber("Imax", 90.0);

        double heading = gyro.getAngle();

        double error = desired_heading - heading;
        integral += error;
        if (integral > integral_limit)
            integral = integral_limit;
        else if (integral < - integral_limit)
            integral = - integral_limit;
        drive_subsys.turn(P * error + I * integral);

        SmartDashboard.putNumber("error", error);
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
