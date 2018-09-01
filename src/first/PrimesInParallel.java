package first;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** Find a bunch of prime numbers in multiple threads
 *  */
public class PrimesInParallel
{
    /** @param number Number to test for being prime
     *  @return true if it's a prime number, otherwise false
     */
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

    /** @param start Start of number range to check
     *  @param end End (exclusive) of range to check, must be odd
     *  @return Count of how many prime numbers we found
     */
    public static int checkNumberRange(int start, int end)
    {
        int count = 0;
        for (int number = start; number < end; number += 2)
            if (isPrime(number))
            {
                // Found another one
                ++count;
                System.out.println(Thread.currentThread().getName() + " Prime number # " + count + " is " + number);
            }
        return count;
    }
    
    public static void main(String[] args) throws Exception
    {
        System.out.println("Prime number # 1 is 2");
        
        // Assume you have 4 CPU cores.
        // Chop the range of 3 .. 100000 into subranges,
        // always starting with an odd number.
        // Start 3 subrange checks each in their own thread
        ExecutorService pool = Executors.newFixedThreadPool(3);

        long start_milli = System.currentTimeMillis();
        Future<Integer> thread1 = pool.submit(() -> checkNumberRange(3, 25001));
        Future<Integer> thread2 = pool.submit(() -> checkNumberRange(25001, 50001));
        Future<Integer> thread3 = pool.submit(() -> checkNumberRange(50001, 75001));
        // Handle the 4th subrange ourselve...
        int count4 = checkNumberRange(75001, 100000);
        // Then wait for the other 3 to finish, ask them how many primes they found
        int count3 = thread3.get();
        int count2 = thread2.get();
        int count1 = thread1.get();
        // Total number of primes found
        int count = 1 + count1 + count2 + count3 + count4;
        
        long end_milli = System.currentTimeMillis();
        double elapsed_seconds = (end_milli - start_milli) / 1000.0;
        System.out.println("I found " + count + " prime numbers in " + elapsed_seconds + " seconds");
        System.out.println("That's " + count / elapsed_seconds + " PRIMe numbers Per Seconds.");
        System.out.println("How many 'PRIMPS' do you get on your computer?");
        // This _is_ faster than the basic Primes example.
        // It's not perfect, though:
        // We're not sorting the result into one nice list of prime numbers,
        // the output of the different threads is simply dumped together.
        //
        // Checking if 11 is prime means testing with 2, 3, 4, 5.
        // Checking if 101 is prime means testing with 2, 3, 4, 5, 6, 7, 8, 9, 10... 50.
        // Checking the higher number ranges is thus slower than checking the
        // lower number ranges. Simply dividing the ranges as we did by equal size 
        // leaves the main thread (testing 75001 to 100000) with much more work in the end,
        // while thread1 (testing 3, 25001) has long finished.
        //
        // Balancing the load so that all 4 threads keep working would require more thought.
        //
        // Finally, most of what this program does is actually _printing_ the numbers out.
        // If you comment the line that prints the prime number, it's faster...
        // ==> Parallel programming is where you get the most performance gain,
        //     but it can be hard to write a decent parallel program.
        
        pool.shutdown();
        
        // Typical results are ~18000 PRIMPS

    }
}
