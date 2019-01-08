package first;

public class Claw
{
    private double speed = -0.5;
    private boolean closed = false;
    
    public double getMotorSpeed()
    {
        return speed;
    }

    public boolean isClosed()
    {
        return closed;
    }

    public void setBlockDetected(boolean have_block)
    {
        if (have_block)
        {
            speed = 0.0;
            closed = true;
        }
        else
        {  
            speed = -0.5;
            closed = false;
        }
    }
}
