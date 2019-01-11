package robot.camera;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;
import robot.BasicRobot;

/** Camera Tests */
public class CameraRobot extends BasicRobot
{
	@Override
	public void robotInit()
	{
		super.robotInit();
	
		// Publish image from USB camera
		CameraServer server = CameraServer.getInstance();
		UsbCamera camera = server.startAutomaticCapture();
		CameraInfo.show(camera);
		camera.setResolution(640, 480);
		camera.setFPS(10);


		Thread thread = new Thread(new Crosshair(server, camera));
		thread.start();
	
	}
}
