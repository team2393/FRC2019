package robot.picamera;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;
import robot.parts.PDPController;

/** Robot with Camera on Pi
 * 
 *  Pi runs PiVionDemo example which detects 
 *  colored blob and updates 'Direction'
 * 
 *  0: blob is in center of picture, or we don't know
 *  Positive: Blob is N pixel to the right of center
 *  Negative: ... to the left of center
 */
public class PiCameraRobot extends BasicRobot
{
  // Devices connected to the RIO
  private Servo servo = new Servo(9);

  // UI connected to the laptop/drive station
  private Joystick joystick = new Joystick(0);

  @Override
  public void teleopPeriodic()
  {
    // In teleop, control servo with joystick
    // Convert -1..1 from joystick into 180..0 degrees for servo
    final double angle = 90.0 - 90.0*joystick.getRawAxis(PDPController.LEFT_STICK_HORIZONTAL);
    servo.setAngle(angle);
  }

  @Override
  public void autonomousPeriodic()
  {
    // In autonomouse, control servo based on 'Direction'
    final double direction = SmartDashboard.getNumber("Direction", 0.0);
    final double current_angle = servo.getAngle();
    // How far to turn?
    // Direction is in pixels, +=160.
    // If we knew how far away the object is,
    // we could compute the exact angle.
    // But we don't, so we just turn a few degrees in that direction,
    // then try again the next time.
    double new_angle = current_angle - direction * 3.0/160.0;
    if (new_angle < 0)
      new_angle = 0.0;
    else if (new_angle > 180)
      new_angle = 180;
    servo.setAngle(new_angle);
  }
}
