package robot;

import edu.wpi.first.wpilibj.RobotBase;
import robot.camera.CameraRobot;
import robot.deepspace.DeepspaceRobot;
import robot.demos.TalonPIDDemo;

/** The java 'main' class */
public final class Main
{
  public static void main(String... args)
  {
    // Change this to select which robot code to run
    RobotBase.startRobot(DeepspaceRobot::new);
  }
}
