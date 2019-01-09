package meetups;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Command;

/** Command that connects the {@link GrabberLogic} to
 *  the motor & sensor of a robot's grabber
 *  and operates it via joystick buttons
 */
public class GrabberCommand extends Command
{
    // The joystick buttons that we use to activate grabber and to trigger eject
    // We could just use these numbers in the code below,
    // i.e. call  joystick.getRawButtonPressed(4)
    // instead of joystick.getRawButtonPressed(ACTIVATE_BUTTON).
    // But the latter is more descriptive,
    // and if we want to change the button assignments,
    // this way we can see and adjust all the grabber related button assignments here:
    private final int ACTIVATE_BUTTON = 4;
    private final int EJECT_BUTTON    = 5;
    // Constants like ^^^^^^^^^^^^^^^ are typically UPPERCASE and have 'final'.
    // final  = The value assigned right now won't change while the program is running.

    private GrabberLogic logic = new GrabberLogic();
    private SpeedController motor;
    private DigitalInput sensor;
    private Joystick joystick;

    public GrabberCommand(SpeedController motor, DigitalInput sensor, Joystick joystick)
    {
        this.motor = motor;
        this.sensor = sensor;
        this.joystick = joystick;
    }

    /** Whenever the command is executed,
     *  use the grabber logic to control the motor
     *  based on joystick inputs and sensor reading
     */
    @Override
    protected void execute()
    {
        // Tell the logic if joystick buttons have been pressed
        if (joystick.getRawButtonPressed(ACTIVATE_BUTTON))
            logic.activate();

        if (joystick.getRawButtonPressed(EJECT_BUTTON))
            logic.eject();

        // Update logic's sensor state from actual sensor
        logic.setSensor(sensor.get());

        // Update motor from grabber logic
        motor.set(logic.getMotorSpeed());
    }

    /** After each call to execute(), the Scheduler
     *  will as this command if it isFinished().
     */
    @Override
    protected boolean isFinished()
    {
        // Always return false to indicate that we're never finished.
        // We want to continue controlling the grabber.
        // We're never 'done' on our own.
        // To stop this command, it has to be cancelled.
        return false;
    }

    /** When the command ends (because it was canceled), stop the motor */
    @Override
    protected void end()
    {
        motor.set(0.0);
    }
}
