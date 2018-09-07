package first;

class PositionTracker2
{
    // We wrap the 'position' into a class...
    double position = 5.0;
    
    // .. with a method to move by some amount
    void move(double amount)
    {
        position += amount;
        // After each move we check to keep the value
        // within bounds
        if (position > 10)
            position = 10;
        if (position < 0)
            position = 0;
    }
}