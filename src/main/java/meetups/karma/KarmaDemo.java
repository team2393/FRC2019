package meetups.karma;

/** Karma Tracker */
public class KarmaDemo
{
    public static void main(String[] args)
    {
        // Two people with slightly different types of Karma
        Karma some_guys_karma = new Karma();
        Karma some_gals_karma = new GirlKarma();

        some_guys_karma.registerGoodDeed();
        some_gals_karma.registerGoodDeed();
        some_guys_karma.registerGoodDeed();
        System.out.println("Some guy has " + some_guys_karma);
        System.out.println("That's worth " + some_guys_karma.getMoney() + " in money");
        System.out.println("Some gal has " + some_gals_karma);
        System.out.println("That's worth " + some_gals_karma.getMoney() + " in money");

        some_guys_karma.registerMessup();
        some_gals_karma.registerMessup();
        System.out.println("Now he's left with " + some_guys_karma +
                           ", and she with " + some_gals_karma);

    }
}
