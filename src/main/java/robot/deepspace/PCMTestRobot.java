package robot.deepspace;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import robot.BasicRobot;

/** Robot for testing speed of PCM access */
public class PCMTestRobot extends BasicRobot
{
    private DigitalOutput digi = new DigitalOutput(7);
    private Solenoid sol = new Solenoid(3);
    private Thread thread = new Thread(this::run_thread);

    private volatile boolean running = true;
    private boolean on_off = true;

    // Turning solenoid on/off every period works fine.
    // Oscilloscope on PCM confirms that output is on/off
    // for 20ms, nice rectangle waveform
    @Override
    public void teleopPeriodic()
    {
        digi.set(on_off);
        sol.set(on_off);
        
        on_off = ! on_off;
    }

    // To try faster cycles, access solenoid in
    // its own thread, started with auto
    // and ended when disabled.
    // -> Doesn't work.
    //    WPILib only communicates with solenoid via
    //    CAN bus every so often. If we try to update
    //    the solenoid more frequently, that info
    //    is not sent to the solenoid, so it tends to
    //    stay on or off for pretty random durations. 
    //
    // There _IS_ actually a way to actuate a solenoid
    // for shorter times like 10ms:
    //    sol.setPulseDuration(0.010);
    //    sol.startPulse();
    // But this means:
    // Within the duration of one WPIlib 'period',
    // turn the solenoid _on_ for 10ms.
    // We, however, need to turn the solenoid _off_ for a short time.
    // In that scenario, the shortest time we can blip
    // the sol. off is one full 20ms period.
    private void run_thread()
    {
        while (running)
        {
            // Briefly blip sol 'on' for 5ms
            sol.set(true);
            Timer.delay(0.005);
            sol.set(false);
            Timer.delay(0.015);
        }
    }

    @Override
    public void autonomousInit()
    {
        super.autonomousInit();
        running = true;
        thread.start();
    }

    @Override
    public void disabledInit()
    {
        super.disabledInit();
        // Stop the thread
        running = false;
    }
}
