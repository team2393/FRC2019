import org.junit.Test;
import static org.junit.Assert.assertEquals;

/** Tests are placed under src/test/java
 *  and executed via WPILib: Test Robot Code.
 * 
 *  The result is found in build/test-results.
 */
public class DemoTest
{
    /** A test class can contain many test methods,
     *  which are each annotated as '@Test'
     */
    @Test
    public void test1()
    {
        System.out.println("TESTING...");
        int sum = 1 + 2;

        // One of the basic assertions is this one:
        assertEquals(3, sum);
    }
}