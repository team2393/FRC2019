package first;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/** Find a bunch of prime numbers in multiple threads.
 * 
 *  Compared to `PrimesInParallel`, all threads keep
 *  working on the next 500 numbers,
 *  nobody is idle,
 *  until all numbers have been checked for being prime.
 */
public class PrimesInParallel2
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
    
    // How many numbers a thread checks in one batch
    static int batchsize = 500;
    
    // The last number to check
    static int final_number = 100000;
    
    // Start of the next range of numbers to check.
    // Threads get this number & increment by `batchsize`.
    // Needs to be 'atomic' because multiple threads will
    // get-and-increment.
    // Then they check from this number on the next `batchsize` for being prime.
    // They keep doing this until we have checked up to `final_number`.
    static AtomicInteger next_range_to_check = new AtomicInteger(3);

    /** @param start Start of number range to check
     *  @param end End (exclusive) of range to check, must be odd
     *  @return Count of how many prime numbers we found
     */
    public static int checkNumberRange()
    {
        int count = 0;
        
        int start = next_range_to_check.getAndAdd(batchsize);
        while (start < final_number)
        {
            int end = start + batchsize;
            for (int number = start; number < end; number += 2)
                if (isPrime(number))
                {
                    // Found another one
                    ++count;
                    System.out.println(Thread.currentThread().getName() + " Prime number # " + count + " is " + number);
                }
            start = next_range_to_check.getAndAdd(batchsize);
        }
        return count;
    }
    
    public static void main(String[] args) throws Exception
    {
        System.out.println("Prime number # 1 is 2");
        
        // Assume you have 4 CPU cores.
        // Instead of tying to determine good subranges,
        // we have 4 threads (the 'main' one and 3 others)
        // which keep checking the next 1000 numbers.
        ExecutorService pool = Executors.newFixedThreadPool(3);

        long start_milli = System.currentTimeMillis();
        Future<Integer> thread1 = pool.submit(() -> checkNumberRange());
        Future<Integer> thread2 = pool.submit(() -> checkNumberRange());
        Future<Integer> thread3 = pool.submit(() -> checkNumberRange());
        int count4 = checkNumberRange();
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
        // Checking the higher number range is much slower than checking the
        // lower number ranges, so simply dividing the ranges as we did 
        // leaves the main thread with much more work in the end, while thread1 is long finished.
        // Balancing the load would require more thought.
        // Finally, most of what this program does is actually _printing_ the numbers out.
        // If you comment the line that prints the prime number, it's faster...
        // ==> Parallel programming is where you get the most performance gain,
        //     but it can be hard to write a decent parallel program.
        
        pool.shutdown();
        
        // Typical results are ~20000 PRIMPS
        
        // This shows how to use more hardware (multiple CPU cores)
        // to gain speed.
        // Another way would be the "Sieve of Eratosthenes" method,
        // https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes.
        // We know that 2 is prime, so we skipped all multiples of two,
        // only checking odd numbers.
        // When we then find that 3 is prime, we could skip multiples of 3.
        // This quickly reduces the list of numbers that you have to check.
        // If then working on the list of numbers-still-to-test in
        // multiple threads, you'd have a fast algorithm.
    }
}
