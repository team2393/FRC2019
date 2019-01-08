package meetups;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class GoLeftCommand extends InstantCommand
{
    private Servo servo;

    public GoLeftCommand(Servo servo_to_use)
    {
        this.servo = servo_to_use;
    }

    @Override
    protected void execute()
    {
        servo.setAngle(100);
    }
}
