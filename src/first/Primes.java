package first;

/** Find a bunch of prime numbers
 *
 *  Experts are creating better prime number algorithms each day.
 *  This is not one of those.
 *  It's a 'brute force' example that uses the basic
 *  principle of testing if a number can be divided.
 *  The main point here is to learn about loops, conditions, functions.
 */
public class Primes
{
    public static boolean isPrime(int number)
    {
        // test if number is divisible by 2, 3, 4, 5, ... up to and including number/2.
        // For example, we'd test if the number 8 is divisible by 2, 3, 4.
        // (We'd actually stop at 2, because 8 % 2 is already == 0)
        int test = 2;
        while (test <= number/2)
        {
            // Compute the remainder of number / test.
            // If it's zero, i.e. there is no remainder,
            // then the number is divisible by 'test' and thus not prime
            if (number % test == 0)
                return false;
            // Wasn't divisible? Test the next one.
            test = test + 1;
        }
        return true;
    }

    public static void main(String[] args)
    {
        // The 'for (...)' loop is a shorter version of
        //
        // int x = start;
        // while (condition)
        // {
        //     do_work;
        //     x = x + increment;
        // }
        //
        // for (int x = start;  condition;  x = x + increment)
        // {
        //     do_work;
        // }
        //
        // Instead of
        //    x = x + increment
        // there's the shorter syntax
        //    x += increment
        // and for incrementing by 1 there's the even shorter
        //    ++x

        // Check all numbers from 3 to 100000.
        // Actually not 100% brute force:
        // Since we know that even numbers can't be prime,
        // we only check odd numbers by starting at 3 and
        // then stepping by 2: 3, 5, 7, 9, 11, ...
        // Since we omit even numbers, we also omit 2,
        // the very first prime, so mention that:
        System.out.println("Prime number # 1 is 2");
        long start_milli = System.currentTimeMillis();
        int count = 1;
        for (int number = 3; number < 100000; number += 2)
            if (isPrime(number))
            {
                // Found another one
                ++count;
                System.out.println("Prime number # " + count + " is " + number);
            }
        long end_milli = System.currentTimeMillis();
        double elapsed_seconds = (end_milli - start_milli) / 1000.0;
        System.out.println("I found " + count + " prime numbers in " + elapsed_seconds + " seconds");
        System.out.println("That's " + count / elapsed_seconds + " PRIMe numbers Per Seconds.");
        System.out.println("How many 'PRIMPS' do you get on your computer?");
        // Typical results are ~14000 PRIMPS
    }
}
