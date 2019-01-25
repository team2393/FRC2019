package robot.deepspace;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Command to move lift to specific position */
public class MoveLift extends Command
{
    private final String name;
    private final Lift lift;
    private final double height;

    /** @param name Name used for position tweaks on dashboard
     *  @param lift Lift to use
     *  @param height Desired height
     */
    public MoveLift(final String name, final Lift lift, final double height)
    {
        this.name = name;
        this.lift = lift;
        this.height = height;
        requires(lift);

        SmartDashboard.setDefaultNumber(name, height);
    }

    @Override
    protected void execute()
    {
        lift.setheight(SmartDashboard.getNumber(name, height));
    }

    @Override
    protected boolean isFinished()
    {
        return false;
    }
}
