package robot.deepspace.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import robot.deepspace.OI;

/** Command to drive with joysick */
public class Joydrive extends Command
{
    private final DriveTrain drivetrain;
    private boolean do_stop; 

    public Joydrive(final DriveTrain drivetrain)
    {
        this.drivetrain = drivetrain;
        requires(drivetrain);
    }
   
    @Override
    public void initialize()
    {
        do_stop = true;
    }
    
    @Override
    protected void execute()
    {
        drivetrain.setSpeed(OI.getSpeed());
        drivetrain.setRotation(OI.getTurn());
    }

    public void cancelbutdontstop()
    {
        do_stop = false;
        cancel();
    }
    
    @Override
    protected boolean isFinished()
    {
        // Keep running until cancelled
        return false;
    }

    @Override
    protected void end()
    {
        // When command is cancelled, stop the drivetrain
        if (do_stop)
            drivetrain.setSpeed(0);
        drivetrain.setRotation(0);
    }
}
