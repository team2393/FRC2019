import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DemoTest
{
    @Test
    public void test1()
    {
        System.out.println("TESTING...");
        int sum = 1 + 2;
        assertEquals(3, sum);
    }
}