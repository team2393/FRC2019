package robot.demos.commands;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.BasicRobot;

/** Demo of running some command in auto,
 *  and allowing start of another command in teleop.
 */
public class CmdDemo extends BasicRobot
{
    static class DemoCommand extends Command
    {
        private final String name;
        private long show;

        public DemoCommand(final String name)
        {
            this.name = name;
        }

        @Override
        protected void initialize()
        {
            show = System.currentTimeMillis() + 1000;
        }

        @Override
        protected void execute()
        {
            final long now = System.currentTimeMillis() + 1000;
            if (now >= show)
            {
                System.out.println("Running " + name);
                show = now + 1000;
            }
        }

        @Override
        protected boolean isFinished()
        {
            return false;
        }
    }

    private final Command cmd_a = new DemoCommand("A");
    private final Command cmd_b = new DemoCommand("B");

    @Override
    public void robotPeriodic()
    {
        Scheduler.getInstance().run();
    }

    @Override
    public void autonomousInit()
    {
        super.autonomousInit();
        cmd_a.start();
    }

    @Override
    public void teleopPeriodic()
    {
        if (RobotController.getUserButton())
            cmd_b.start();
    }
}
