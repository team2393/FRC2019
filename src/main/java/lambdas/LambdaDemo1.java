package lambdas;

import java.time.LocalDateTime;

/** Lambda Intro: Passing a value vs. and interface to get the value */
public class LambdaDemo1
{
    // Assume you have a method that needs some value.
    // Like this one that wants to print the current time:
    void showTime1(String the_time)
    {
        System.out.println("It's now " + the_time);
    }

    // That works as long as you always pass the value when you call the method.
    //
    // But what if the method is "running on its own",
    // for example it's called by some thread,
    // and then the method should have a way to fetch that value on its own.
    // In other words, instead of _you_calling_that_method_ with the desired value,
    // the _method_will_ask_you_ for the value whenever it needs one.
    //
    // This is done by first agreeing on an interface:
    interface TimeTextProvider
    {
        String getTimeText();
    }

    // Instead of getting the actual value, the method now
    // gets that interface, which it can use to fetch the value
    void showTime2(TimeTextProvider time_provider)
    {
        String the_time = time_provider.getTimeText();
        System.out.println("It's now " + the_time);
    }

    // When we call that updated method, we don't provide the time,
    // but a class that the method can use to fetch the time
    static class LocalTimeProvider implements TimeTextProvider
    {
        @Override
        public String getTimeText()
        {
            return LocalDateTime.now().toString();
        }
    }


    public static void main(String[] args)
    {
        final LambdaDemo1 demo = new LambdaDemo1();

        // For the first method, we need to provide the time text
        demo.showTime1(LocalDateTime.now().toString());

        // For the second method we need to pass something
        // that implements the "TimeTextProvider" interface,
        // for example an instance of the LocalTimeProvider class:
        demo.showTime2(new LocalTimeProvider());

        // If we didn't have the LocalTimeProvider class,
        // we could create an "anonymous" class instance
        // based on the interface right here:
        demo.showTime2(new TimeTextProvider()
        {
            @Override
            public String getTimeText()
            {
                return LocalDateTime.now().toString();
            }
        });

        // An interface with just one method or function is
        // also called a "Functional Interface" because it
        // basically defines the pattern of just one function:
        // Its return type (or void), name, arguments.
        //
        // A "Lamda function" is basically a shortcut
        // for implementing a Functional Interface:
        //
        //   (arg1, arg2, arg3) -> code that returns the value
        //
        // The Functional Interface "TimeTextProvider"
        // takes no arguments '()' and returns a string:
        demo.showTime2(() -> LocalDateTime.now().toString());
    }
}
