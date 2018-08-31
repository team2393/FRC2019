package first;

class PositionTracker3
{
    private double position = 5.0;
    
    public double getPosition()
    {
        return position;
    }
    
    void move(double amount)
    {
        position += amount;
        if (position > 10)
            position = 10;
        if (position < 0)
            position = 0;
    }
    
    public String toString()
    {
        return "Tracker at position " + position;
    }
}

public class PositionTrackerDemo3
{    
    public static void main(String[] args)
    {
        PositionTracker3 tracker = new PositionTracker3();
        System.out.println("I am at " + tracker.getPosition());
        
        tracker.move(2);
        System.out.println(tracker);
        
        tracker.move(8);
        System.out.println(tracker);
        
        tracker.move(8);
        System.out.println(tracker);

        tracker.move(-2);
        System.out.println(tracker);
    }
}
