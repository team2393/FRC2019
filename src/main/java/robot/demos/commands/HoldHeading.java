package robot.demos.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.demos.subsystems.DriveSubsystem;

/** Command to hold current heading
 *
 *  On start, fetch current heading from gyro
 *  and use that as the desired heading.
 *  From then on, use proportional gain to hold heading.
 */
public class HoldHeading extends Command
{
    private final DriveSubsystem drive_subsys;
    private final Gyro gyro;

    // Gain for correcting error.
    // Example:
    // desired heading is 10.0 degrees, but gyro tells us we're pointed to 8 degrees.
    // error = 10 - 8 = 2 degrees
    // error * P = 0.02, so we'd slowly turn clockwise (left)
    //
    // error = 90 degrees -> we'd turn 90*0.01 = 0.9 i.e. almost full speed clockwise
    //
    // error = 150 degrees -> we'd try to turn by 1.5,
    // but motor classes already limit values to the -1..1 range, so we'd turn by 1.0
    private double P = 0.01;
    private double desired_heading;

    public HoldHeading(DriveSubsystem drive_subsys, Gyro gyro)
    {
        this.drive_subsys = drive_subsys;
        this.gyro = gyro;
    }

    @Override
    protected void initialize()
    {
        setDesiredHeading(gyro.getAngle());
    }

    public void setDesiredHeading(double heading)
    {
        desired_heading = heading;
    }

    public double getDesiredHeading()
    {
        return desired_heading;
    }

    @Override
    protected void execute()
    {
        double heading = gyro.getAngle();

        double error = desired_heading - heading;
        drive_subsys.turn(P * error);

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
