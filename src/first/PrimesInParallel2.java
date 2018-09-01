package first;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/** Find prime numbers in multiple threads.
 * 
 *  Compared to `PrimesInParallel`, all threads keep
 *  working on the next to-be-checked number,
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
    
    /** Next number to check.
     *  Threads get this number & increment.
     */
    static AtomicInteger next_number_to_check = new AtomicInteger(3);

    /** Keep checking the respective next number
     *  @return Count of how many prime numbers we found
     */
    public static int checkNumbers()
    {
        int count = 0;
        
        int number = next_number_to_check.getAndAdd(2);
        while (number < 100000)
        {
            if (isPrime(number))
            {
                // Found another one
                ++count;
                System.out.println(Thread.currentThread().getName() + " Prime number # " + count + " is " + number);
            }
            number = next_number_to_check.getAndAdd(2);
        }
        return count;
    }
    
    public static void main(String[] args) throws Exception
    {
        System.out.println("Prime number # 1 is 2");
        
        // Assume you have 4 CPU cores.
        // Instead of tying to determine good subranges,
        // we have 4 threads (the 'main' one and 3 others)
        // which keep checking the next number until
        // we reach 100000.
        ExecutorService pool = Executors.newFixedThreadPool(3);

        long start_milli = System.currentTimeMillis();
        Future<Integer> thread1 = pool.submit(() -> checkNumbers());
        Future<Integer> thread2 = pool.submit(() -> checkNumbers());
        Future<Integer> thread3 = pool.submit(() -> checkNumbers());
        int count4 = checkNumbers();
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
        
        pool.shutdown();
    
        // It's a little faster than `PrimesInParallel` because 4 threads
        // run in parallel, always staying busy until we checked all numbers
        // up to 100000.
    
        // Typical results are ~20000 PRIMPS
        
        // This shows how to use more hardware (multiple CPU cores)
        // to gain speed.
        // Further improvements would have to come from being overall
        // more clever about it.
        // We know that 2 is prime, so we skipped all multiples of two,
        // only checking odd numbers.
        // The "Sieve of Eratosthenes" method takes this further, see
        // https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes.
        // When we find that 3 is prime, we could skip multiples of 3.
        // When we find the next prime P, we could skip all multiples of P. 
        // This quickly reduces the list of numbers that you have to check.
        // If then working on the list of numbers-still-to-test in
        // multiple threads, you'd have a really fast algorithm.
    }
}
