package robot.arm;

import edu.wpi.first.wpilibj.command.Command;

public class MoveArm extends Command
{
    private static final double ACCURACY = 0.5;
    private static final double SPEED = 0.5;
    private Arm arm;
    private double hand, inner, outer, base;

    /** Command to move arm to a desired position
     *  @param arm Arm to move
     *  @param hand Hand angle
     *  @param inner Inner angle
     *  @param outer Outer angle
     *  @param base Base angle
     */
    public MoveArm(Arm arm, double hand, double inner, double outer, double base)
    {
        this.arm = arm;
        this.hand = hand;
        this.inner = inner;
        this.outer = outer;
        this.base = base;
    }

    @Override
    protected void execute()
    {
        arm.setHand(computeChange(arm.getHand(), hand));
        arm.setInner(computeChange(arm.getInner(), inner));
        arm.setOuter(computeChange(arm.getOuter(), outer));
        arm.setBase(computeChange(arm.getBase(), base));
    }

    private double computeChange(double current, double desired)
    {
        double error = desired - current;
        if (Math.abs(error) > SPEED)
        {
            if (error > 0)
                return current + SPEED;
            else
                return current - SPEED;
        }
        else
            return desired;
    }

    @Override
    protected boolean isFinished()
    {
        return atPosition(arm.getHand(), hand) &&
               atPosition(arm.getInner(), inner) &&
               atPosition(arm.getOuter(), outer) &&
               atPosition(arm.getBase(), base);
    }

    private boolean atPosition(double current, double desired)
    {
        return Math.abs(current - desired) < ACCURACY;
    }
}
