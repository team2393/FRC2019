package robot.deepspace;

import edu.wpi.first.wpilibj.command.Command;

public class RotateToHeading extends Command
{
    private final DriveTrain drivetrain;
    private final double heading;

    public RotateToHeading(DriveTrain drivetrain, double heading)
    {
        this.drivetrain = drivetrain;
        this.heading = heading;
    }

    @Override
    protected void execute() 
    {
        drivetrain.setHeading(heading);
    }

    @Override
    protected boolean isFinished()
    {
        return Math.abs(drivetrain.getHeading() - heading) < 2;
    }

    @Override
    protected void end()
    {
        drivetrain.setHeading(Double.NaN);
    }
}