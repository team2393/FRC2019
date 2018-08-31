package first;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ClawTest
{
    @Test
    public void testClaw()
    {
        Claw claw = new Claw();
        
        // Start out trying to pull blocks into the open claw
        assertEquals(-0.5, claw.getMotorSpeed(), 0.1);
        assertEquals(false, claw.isClosed());
        
        // When block detected, close, stop motor
        claw.setBlockDetected(true);
        assertEquals(0, claw.getMotorSpeed(), 0.1);
        assertEquals(true, claw.isClosed());
        
        // Assume block falls out
        claw.setBlockDetected(false);
        // Claw should then open and turn motor back on
        assertEquals(false, claw.isClosed());
        assertEquals(-0.5, claw.getMotorSpeed(), 0.1);
    }
}
