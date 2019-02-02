package robot.deepspace.drivetrain;

import edu.wpi.first.wpilibj.command.InstantCommand;

/** Command to toggle high/low gear */
public class ToggleGear extends InstantCommand
{
    private final DriveTrain drivetrain;

    public ToggleGear(final DriveTrain drivetrain)
    {
        this.drivetrain = drivetrain;
    }

    @Override
    protected void execute()
    {
        drivetrain.setGear(! drivetrain.isHighGear());
    }
}
