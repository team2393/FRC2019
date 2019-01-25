package robot.demos.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.demos.subsystems.DriveSubsystem;

/** Command to hold current heading
 *
 *  Like HoldHeadingPID, but using PIDController & PIDCommand.
 *  So don't need to do the computations,
 *  and get 'D' in addition to P & I.
 *  .. but not good way to clamp the integral,
 *  so tends to overshoot more.
 */
public class HoldHeadingPID2 extends PIDCommand
{
    private final DriveSubsystem drive_subsys;
    private final Gyro gyro;

    public HoldHeadingPID2(DriveSubsystem drive_subsys, Gyro gyro)
    {
        super(0.01, 0.003, 0.001);
        this.drive_subsys = drive_subsys;
        this.gyro = gyro;

        // This helps the PID controller to clamp the integral sum
        getPIDController().setOutputRange(-1.0, +1.0);

        // Place PID controller on dashboard allows adjusting gains
        SmartDashboard.putData(getPIDController());
    }

    @Override
    protected void initialize()
    {
        setSetpoint(gyro.getAngle());
    }

    @Override
    protected double returnPIDInput()
    {
        return gyro.getAngle();
    }

    @Override
    protected void usePIDOutput(double output)
    {
        drive_subsys.turn(output);
        SmartDashboard.putNumber("error", getPIDController().getError());
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
        super.end();
        drive_subsys.turn(0.0);
    }
}
