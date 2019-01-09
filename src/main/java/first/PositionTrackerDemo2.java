package first;

public class PositionTrackerDemo2
{    
    public static void main(String[] args)
    {
        PositionTracker2 tracker = new PositionTracker2();
        System.out.println("I am at " + tracker.position);
        
        // Works fine..
        tracker.move(2);
        System.out.println("I am at " + tracker.position);
        
        // We are prevented from going beyond 10..
        tracker.move(8);
        System.out.println("I am at " + tracker.position);
        
        // .. again ..
        tracker.move(8);
        System.out.println("I am at " + tracker.position);
        
        // But noting keeps us from circumventing the check:
        tracker.position += 8;
        System.out.println("I am at " + tracker.position);
    }
}
