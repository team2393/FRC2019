package robot.deepspace.drivetrain;

import edu.wpi.first.wpilibj.command.InstantCommand;

/** Command to reset drivetrain position and heading
 *  to zero inches and degrees
*/
public class ResetDrivetrain extends InstantCommand
{
    private final DriveTrain drivetrain;

    public ResetDrivetrain(final DriveTrain drivetrain)
    {
        this.drivetrain = drivetrain;
        requires(drivetrain);
    }

    @Override
    protected void execute()
    {
        drivetrain.resetEncoders();;
    }
}
