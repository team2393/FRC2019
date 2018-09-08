package first;

import java.util.Objects;

/** Test assertions
 * 
 *  WPILib doesn't include JUnit,
 *  so implementing some basic assertions ourselves.
 */
public class Testing
{
    /** @param expected Expected value
     *  @param actual Actual value
     */
    public static void assertEquals(final Object expected, final Object actual)
    {
        if (! Objects.equals(expected, actual))
            throw new Error("Expected " + expected + " but got " + actual);
    }

    /** @param expected Expected numeric value
     *  @param actual Actual value
     *  @param tolerance Tolerance to allow for rounding errors in double values
     */
    public static void assertEquals(final double expected, final double actual, final double tolerance)
    {
        if (Math.abs(expected - actual) > tolerance)
            throw new Error("Expected " + expected + " but got " + actual);
    }
}
