package first;

class PositionTracker3
{
    // Make 'position' private, so nobody else can touch it
    private double position = 5.0;
    
    // We allow reading it
    public double getPosition()
    {
        return position;
    }
    
    // .. and moving it..
    void move(double amount)
    {
        position += amount;
        // .. where we always assert that it stays bounded
        if (position > 10)
            position = 10;
        if (position < 0)
            position = 0;
    }
    
    // In addition, a "toString()"
    // allows us to simply print the object
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
