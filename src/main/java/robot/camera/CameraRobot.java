package robot.camera;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

/** Camera Tests */
public class CameraRobot extends BasicRobot
{
	private final MarkerDetector vision = new MarkerDetector();
	private CameraHandler camera;

	@Override
	public void robotInit()
	{
		super.robotInit();
	
		// Run USB camera
		camera = new CameraHandler(320, 240, 10, vision);
	}

	@Override
    public void robotPeriodic()
    {
		camera.publishState();

		// Cannot use putNumber(".."  , vision.getDirection())
		// because it doesn't handle Double.NaN,
		// so publishing as text.
		SmartDashboard.putString("Direction", Double.toString(vision.getDirection()));
    }
}
