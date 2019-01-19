package robot.camera;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

/** Camera Tests */
public class CameraRobot extends BasicRobot
{
	private CameraServer server;
	private UsbCamera camera;
	private VisionProcessor1 vision;

	@Override
	public void robotInit()
	{
		super.robotInit();
	
		// Publish image from USB camera
		server = CameraServer.getInstance();
		camera = server.startAutomaticCapture();
		// Check if we _have_ a camera
		// (would otherwise crash when trying to use it)
		if (CameraInfo.show(camera))
		{
			// Enable telemetry measurements
			CameraServerJNI.setTelemetryPeriod(1.0);
			camera.setResolution(320, 240);
			camera.setFPS(10);

			vision = new VisionProcessor1(server, camera);
			Thread thread = new Thread(vision);
			thread.start();
		}
	}

	@Override
    public void robotPeriodic()
    {
		try
		{
			if (camera.isConnected())
			{
				// Convert bytes per second into bits (8) and Mega (1e6)
				double bits_per_sec = camera.getActualDataRate()*8/1000.0/1000.0;
				SmartDashboard.putNumber("Camera Mbps", bits_per_sec);
				SmartDashboard.putBoolean("Too Much", bits_per_sec >= 4.0);
				SmartDashboard.putBoolean("Too Much", bits_per_sec >= 4.0);
				// Cannot use putNumber(  , vision.getDirection())
				// because it doesn't handle Double.NaN,
				// so publishing as text.
				SmartDashboard.putString("Direction", Double.toString(vision.getDirection()));
			}
		}
		catch (Throwable ex)
		{
			// No camera data
		}
    }
}
