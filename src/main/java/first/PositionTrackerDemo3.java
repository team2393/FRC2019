package first;

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
