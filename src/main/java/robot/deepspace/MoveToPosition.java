package robot.deepspace;

import edu.wpi.first.wpilibj.command.Command;

public class MoveToPosition extends Command
{
    private final DriveTrain drivetrain;
    private final double position;

    public MoveToPosition(DriveTrain drivetrain, double position)
    {
        this.drivetrain = drivetrain;
        this.position = position;
    }

    @Override
    protected void execute() 
    {
        drivetrain.setPosition(position);
    }

    @Override
    protected boolean isFinished()
    {
        return Math.abs(drivetrain.getPosition() - position) < 2;
    }

    @Override
    protected void end()
    {
        drivetrain.setPosition(Double.NaN);
    }
}