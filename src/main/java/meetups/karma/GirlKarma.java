package meetups.karma;

/** Slightly different Karma Tracker */
public class GirlKarma extends Karma
{
    // Starts out with more points
    public GirlKarma()
    {
        karma = 100;
    }

    // Gets less in $$
    public double getMoney()
    {
        return 0.75 * super.getMoney();
    }
}
