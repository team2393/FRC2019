package robot;

import edu.wpi.first.wpilibj.RobotBase;
import robot.blacky.Robot;
import robot.deepspace.DeepspaceRobot;

/** The java 'main' class */
public final class Main
{
  public static void main(String... args)
  {
    // Change this to select which robot code to run
    RobotBase.startRobot(Robot::new);
  }
}
