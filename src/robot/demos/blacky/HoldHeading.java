package robot.demos.blacky;

import edu.wpi.first.wpilibj.command.Command;

/** Command to hold current heading
 * 
 *  On start, fetch current heading from gyro
 *  and use that as the desired heading.
 *  From then on, use proportional gain to hold heading.
 */
public class HoldHeading extends Command
{
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

    public HoldHeading()
    {
        doesRequire(Robot.wheels);
    }
    
    @Override
    protected void initialize()
    {
        desired_heading = Robot.gyro.getAngle();
    }

    @Override
    protected void execute()
    {
        double heading = Robot.gyro.getAngle();
        
        double error = desired_heading - heading;
        Robot.wheels.turn(P * error);
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
        Robot.wheels.turn(0.0);
    }
}
