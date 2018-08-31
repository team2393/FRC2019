package first;

public class PositionTrackerDemo1
{
    public static void main(String[] args)
    {
        // Assume we somehow need to track a position.
        // So we have a variable to hold the current position,
        // starting at 5
        double position = 5.0;
        System.out.println("I am at " + position);
        
        // We can move
        double move = 2.0;
        position += move;
        System.out.println("I am at " + position);
        
        // But assume the position should be limited to the range 0 .. 10.
        move = 8;
        position += move;
        // That means we always have to check after each move to correct it:
        if (position > 10)
            position = 10;
        if (position < 0)
            position = 0;
        System.out.println("I am at " + position);
        
        // We always need to remember to correct
        move = 8;
        position += move;
        // Here we forgot to add the if (pos > 10 ..) test...
        System.out.println("I am at " + position + ". Oooops.");
    }
}
