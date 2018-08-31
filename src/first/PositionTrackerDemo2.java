package first;

class PositionTracker2
{
    double position = 5.0;
    
    void move(double amount)
    {
        position += amount;
        if (position > 10)
            position = 10;
        if (position < 0)
            position = 0;
    }
}

public class PositionTrackerDemo2
{    
    public static void main(String[] args)
    {
        PositionTracker2 tracker = new PositionTracker2();
        System.out.println("I am at " + tracker.position);
        
        tracker.move(2);
        System.out.println("I am at " + tracker.position);
        
        tracker.move(8);
        System.out.println("I am at " + tracker.position);
        
        tracker.move(8);
        System.out.println("I am at " + tracker.position);
    }
}
