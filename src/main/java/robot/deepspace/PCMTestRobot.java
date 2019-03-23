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
    private Timer timer = new Timer();
    private Thread thread = new Thread(this::run_thread);

    private volatile boolean running = true;
    private boolean on_off = true;

    @Override
    public void teleopPeriodic()
    {
        digi.set(on_off);
        sol.set(on_off);
        on_off = ! on_off;
    }

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

    @Override
    public void autonomousPeriodic()
    {
    }
}
