package robot.arm;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends Subsystem
{
    public static final double BASE_HOME = 90.0;
    public static final double OUTER_HOME = 90.0;
    public static final double INNER_HOME = 45.0;
    public static final double HAND_HOME = 160.0;

    /** Servos on ports 1..4 as labeled on the servo connector */
    private Servo hand = new Servo(1);
    private Servo inner = new Servo(2);
    private Servo outer = new Servo(3);
    private Servo base = new Servo(4);

    public Arm()
    {
        setHand(HAND_HOME);
        setInner(INNER_HOME);
        setOuter(OUTER_HOME);
        setBase(BASE_HOME);
    }

    /** Move toward the home position of each servo */
    public void moveHome()
    {
        home(hand, HAND_HOME);
        home(inner, INNER_HOME);
        home(outer, OUTER_HOME);
        home(base, BASE_HOME);
    }

    private void home(Servo axis, double home)
    {
        double pos = axis.getAngle();
        if (Math.abs(pos - home) > 0.5)
            if (pos > home)
                axis.setAngle(pos - 0.5);
            else if (pos < home)
                axis.setAngle(pos + 0.5);
    }

    @Override
    protected void initDefaultCommand()
    {
        // No default command
    }

    /** @return Hand angle */
    public double getHand()
    {
        return hand.getAngle();
    }

    /** @param angle Hand angle: 90 (closed) .. 160 (open) */
    public void setHand(double angle)
    {
        if (angle < 92.0)
            angle = 92.0;
        else if (angle > 160.0)
            angle = 160.0;
        SmartDashboard.putNumber("hand", angle);
        hand.setAngle(angle);
    }

    /** @param angle Positive to open hand further, negative to close */
    public void changeHand(double angle)
    {
        setHand(hand.getAngle() + angle);
    }

    /** @return Inner angle */
    public double getInner()
    {
        return inner.getAngle();
    }

    /** @param angle Inner angle: 20 (close) .. 120 (out) */
    public void setInner(double angle)
    {
        if (angle < 20.0)
            angle = 20.0;
        else if (angle > 120.0)
            angle = 120.0;
        SmartDashboard.putNumber("inner", angle);
        inner.setAngle(angle);
    }

    public void changeInner(double angle)
    {
        setInner(inner.getAngle() + angle);
    }

    /** @return Outer angle */
    public double getOuter()
    {
        return outer.getAngle();
    }

    /** @param angle Outer angle: 60 .. 170 */
    public void setOuter(double angle)
    {
        if (angle < 60.0)
            angle = 60.0;
        else if (angle > 170.0)
            angle = 170.0;
        SmartDashboard.putNumber("outer", angle);
        outer.setAngle(angle);
    }

    public void changeOuter(double angle)
    {
        setOuter(outer.getAngle() + angle);
    }

    /** @return Base angle */
    public double getBase()
    {
        return base.getAngle();
    }

    /** @param angle Base angle: 11 (right) .. 165 (left) */
    public void setBase(double angle)
    {
        if (angle < 11.0)
            angle = 11.0;
        else if (angle > 165.0)
            angle = 165.0;
        SmartDashboard.putNumber("base", angle);
        base.setAngle(angle);
    }

    public void changeBase(double angle)
    {
        setBase(base.getAngle() + angle);
    }
}
