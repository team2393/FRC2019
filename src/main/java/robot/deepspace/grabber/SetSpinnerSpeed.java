package robot.deepspace.grabber;

import edu.wpi.first.wpilibj.command.InstantCommand;

/** Command to set speed of cargo spinners */
public class SetSpinnerSpeed extends InstantCommand
{
    private final Grabber grabber;
    private final double speed;

    public SetSpinnerSpeed(final Grabber grabber, final double speed)
    {
        requires(grabber);
        this.grabber = grabber;
        this.speed = speed;
    }

    @Override
    protected void execute()
    {
        grabber.setSpinnerSpeed(speed);
    }
}
