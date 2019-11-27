package lambdas;

import java.time.LocalDateTime;

/** Lambda Intro: More examples */
public class LambdaDemo2
{
    // Assume you have a method that does something tricky,
    // and you want the method to log what it's doing.
    // How it logs exactly might change, we just agree on
    // a functional interface for logging.
    @FunctionalInterface
    interface Logging
    {
        void log(String message);
    }

    // The @FunctionalInterface annotation doesn't "do" anything in the end,
    // it just tells to compiler to check if we really create
    // a functional interface, i.e. an interface with just one method/function.
    // If we accidentally defined the interface with no method, or more than 1 method,
    // we'd get a compiler error.

    // Our complicated method can now use that Logging interface
    static void complicated_method(Logging logging)
    {
        logging.log("I'm starting to do the complicated thing");
        // doing something..
        logging.log("Charging the rail gun..");
        // doing something..
        logging.log("Enabling the Flux Capacitor (c)..");
        // doing something..
        logging.log("Packing refreshments..");
        // doing something..
        logging.log("Downloading movies..");
        // doing something..
        logging.log("Got distracted 'like'-ing cat pictures..");
        // doing something..
        System.out.println("Blastoff!!");
        logging.log("Done.");
        logging.log("-----------------------------");
    }

    public static void main(String[] args)
    {
        // Before lambdas, we would have to create a class like this:
        //
        // class MyLogging implements Logging
        // {
        //    public void log(String message)
        //    {
        //         System.out.println(message);
        //    }
        // }
        //
        // .. and then pass 'new MyLogging()' to the method.
        // Or use an "anonymous" class instance, and that's still possible:
        LambdaDemo2.complicated_method(new Logging()
        {
            @Override
            public void log(String message)
            {
                System.out.println(message);
            }
        });
        // (Select "Logging", click the "Fixes" Lightbulb,
        //  and it should suggest "Convert to lambda"...
        // )

        // .. but using a lambda is much shorter: 
        // (arg) -> code
        LambdaDemo2.complicated_method((message) -> System.out.println(message));

        // Syntactic variant 1:
        // If there's just one arg, we can omit the parentheses:
        LambdaDemo2.complicated_method(message -> System.out.println(message));

        // Syntactic variant 2:
        // If the lambda code is just a call to one method, you can pass that method
        // with a somewhat quirky '::'syntax, which makes it really short to type:
        LambdaDemo2.complicated_method(System.out::println);

        // The point here: With lambdas, you can conveniently implement
        // a @FunctionalInterface, an interface that contains just one method/function.
        //
        // In this example, it allows us to change the logging to be more
        // elaborate, for example include time stamps:
        Logging timed_logging = message ->
            System.out.println(LocalDateTime.now() + " - " + message);

        // Or we could use a minimalistic, energy efficient logger,
        // where the "code" is just an empty block, doing nothing:
        Logging taciturn_logging = message -> {};

        LambdaDemo2.complicated_method(timed_logging);
        LambdaDemo2.complicated_method(taciturn_logging);
    }
}
