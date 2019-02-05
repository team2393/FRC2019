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
        // To be on the safe side,
        // require drivetrain and thus stop other drivetrain
        // commands when resetting encoders.
        // Would be OK to reset while driving manually,
        // but auto moves would be very confused when
        // resetting while they're trying to control position or heading.
        requires(drivetrain);
    }

    @Override
    protected void execute()
    {
        drivetrain.resetEncoders();;
    }
}
