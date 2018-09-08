package first;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/** Find prime numbers in multiple threads.
 * 
 *  Uses one thread per CPU.
 *  All threads keep working on the next to-be-checked number,
 *  nobody is idle, and all numbers are printed later
 *  to remove the comparably slow printing from the timing.
 */
public class PrimesInParallelPrintedLater
{    
    /** @param number Number to test for being prime
     *  @return <code>true</code> if it's a prime number, otherwise <code>false</code>
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
    
    /** All threads add the prime numbers that they find to this list */
    static List<Integer> primes = Collections.synchronizedList(new ArrayList<>());
 
    /** Next number to check.
     *  Threads get this number & increment.
     */
    static AtomicInteger next_number_to_check = new AtomicInteger(3);

    /** Keep checking the respective next number until we hit 100000
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
                primes.add(number);
            }
            number = next_number_to_check.getAndAdd(2);
        }
        return count;
    }
    
    public static void main(String[] args) throws Exception
    {
        // Add 2, the only even prime number, right away.
        // We'll then check all the _odd_ numbers.
        primes.add(2);
        
        // Assume you have 4 CPU cores...
        int cpu_count = Runtime.getRuntime().availableProcessors();
        
        ExecutorService pool = Executors.newFixedThreadPool(cpu_count-1);
        List<Future<Integer>> threads = new ArrayList<>();

        // Start 3 additional threads to help search for numbers...
        long start_milli = System.currentTimeMillis();
        for (int i=0; i<cpu_count - 1; ++i)
            threads.add(pool.submit(() -> checkNumbers()));
        
        // .. and then also look for numbers ourselves.
        // See how many we find (plus the number "2" that we already added)
        int count = 1 + checkNumbers();
        
        // Then wait for the other 3 to finish, ask them how many primes they found
        for (Future<Integer> thread : threads)
            count += thread.get();

        long end_milli = System.currentTimeMillis();
        
        System.out.println("I have " + cpu_count + " CPUs");
        
        double elapsed_seconds = (end_milli - start_milli) / 1000.0;
        System.out.println("I found " + count + " prime numbers in " + elapsed_seconds + " seconds");
        System.out.println("That's " + count / elapsed_seconds + " PRIMe numbers Per Seconds.");
        System.out.println("How many 'PRIMPS' do you get on your computer?");
        
        pool.shutdown();
    
        // Typical results are ~29000 PRIMPS
        
        TimeUnit.SECONDS.sleep(2);
        System.out.println(".. but I haven't printed them, yet...");

        TimeUnit.SECONDS.sleep(2);
        System.out.println("Here they are:");
        Collections.sort(primes);
        int i = 0;
        for (int prime : primes)
            System.out.println("#" + ++i + " is " + prime);
    }
}
