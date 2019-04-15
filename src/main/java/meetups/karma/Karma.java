package meetups.karma;

/** Karma Tracker */
public class Karma
{
    // Outside code cannot directly touch the points
    protected int karma;

    // Anybody can get the current value
    public int get()
    {
        return karma;
    }

    // .. also converted to $$
    public double getMoney()
    {
        return karma * 0.00000000001;
    }

    // Doing a good deed increases the value
    public void registerGoodDeed()
    {
        karma += 1;
    }

    // Messing up is bad 
    public void registerMessup()
    {
        karma = 0;
    }

    // 'toString' is used when trying to print the object
    public String toString()
    {
        return karma + " Karma Points";
    }
}
