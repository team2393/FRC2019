package robot.camera;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.BasicRobot;

/** Camera Tests */
public class CameraRobot extends BasicRobot
{
	private CameraServer server;
	private UsbCamera camera;

	@Override
	public void robotInit()
	{
		super.robotInit();
	
		// Publish image from USB camera
		server = CameraServer.getInstance();
		camera = server.startAutomaticCapture();
		CameraInfo.show(camera);
		camera.setResolution(640, 480);
		camera.setFPS(10);

		Thread thread = new Thread(new Crosshair(server, camera));
		thread.start();

		// Enable telemetry measurements
		CameraServerJNI.setTelemetryPeriod(1.0);
	}

	@Override
    public void robotPeriodic()
    {
		// Convert bytes per second into bits (8) and Mega (1e6)
		double bits_per_sec = camera.getActualDataRate()*8/1000.0/1000.0;
		SmartDashboard.putNumber("Camera Mbps", bits_per_sec);
		SmartDashboard.putBoolean("Too Much", bits_per_sec >= 4.0);
    }
}
