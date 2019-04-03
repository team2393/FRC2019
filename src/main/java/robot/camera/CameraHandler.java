package robot.camera;

import org.opencv.core.Mat;

import edu.wpi.cscore.CameraServerJNI;
import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* Basic idea how one could switch between two cameras:

UsbCamera camera1, camera2;
VideoSink server;
void robotInit()
{
  camera1 = CameraServer.getInstance().startAutomaticCapture(0);
  camera2 = CameraServer.getInstance().startAutomaticCapture(1);
  server = CameraServer.getInstance().addSwitchedCamera("switched camera");
  camera1.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
  camera2.setConnectionStrategy(ConnectionStrategy.kKeepOpen);
}
void teleopPeriodic()
{
  if (......)
    server.setSource(camera2);
  else if (.....)
    server.setSource(camera1);
}
*/


/** Helper class for handling the camera
 * 
 *  Connects to the USB camera,
 *  publishes the original image,
 *  and runs a separate thread for a VisionProcessor
 */
public class CameraHandler
{
    private final VisionProcessor processor;
	private CameraServer server;
    private UsbCamera camera;

    /** Start USB camera for given width x height
     *  at fps frames per second.
     *  Publish each original image
     *  plus call a processor for each image.
     * 
     *  In case there is no image, we call the pipeline
     *  with 'null'.
     * 
     *  160 by 120 at 10fps - 0.3mbs
     *  320 by 240 at 10fps - 0.9mbs  <--- Using this for now
     *  640 by 480 at 10fps - 3.1mbs
     *  640 by 480 at 15fps - 4.1mbs
     *  610 by 450 at 15fps - 3.4mbs
     *
     *  @param width
     *  @param height
     *  @param fps
     *  @param processor
     */
    public CameraHandler(final int width, final int height,
                         final int fps,
                         final VisionProcessor processor)
    {
        this.processor = processor;
 
        // Start USB camera, publish original image
        // Image shows in dashboard (add CameraServer stream viewer).
        // Or try opening a webbrowser to http://10.23.93.2:1181/
		server = CameraServer.getInstance();
		camera = server.startAutomaticCapture();
    
        // Check if we _have_ a camera
		// (would otherwise crash when trying to use it)
		if (CameraInfo.show(camera))
		{
			// Enable telemetry measurements
            CameraServerJNI.setTelemetryPeriod(1.0);
            
            // Configure
			camera.setResolution(width, height);
            camera.setFPS(fps);
            // Set these from dashboard?
            camera.setBrightness(25);
            camera.setExposureManual(50);
            // camera.setExposureManual(value);
            // camera.setWhiteBalanceManual(value);
            
            // Initialize processor
            processor.init(server, width, height);

            // Start new thread for processing images
			Thread thread = new Thread(this::process);
			thread.start();
		}
    }

    /** Called by thread: Read image from camera, call pipeline */
    private void process()
    {
        final Mat original = new Mat();
        final CvSink video = server.getVideo();

        // Keep processing images until this thread is interrupted (robot stopped)
        while (!Thread.interrupted())
        {
            if (video.grabFrame(original) == 0)
            {
                // Could not fetch frame.
                processor.process(null);
                // Report error..
                System.out.println(video.getError());
                try
                {   // .. and wait a little in hope of better luck next time
                    Thread.sleep(1000);
                }
                catch (Exception ex)
                {
                    // Ignore
                }
            }
            else
             processor.process(original);
        }
    }

    /** Can be called from e.g. robotPeriodic
     *  to publish camera info on daskboard.
     *  @return 'true' when camera is OK
     */
    public boolean publishState()
    {
		try
		{
			if (camera.isConnected())
			{
				// Convert bytes per second into bits (8) and Mega (1e6)
				double bits_per_sec = camera.getActualDataRate()*8/1000.0/1000.0;
				SmartDashboard.putNumber("Camera Mbps", bits_per_sec);
                SmartDashboard.putBoolean("Too Much", bits_per_sec >= 4.0);
                return true;
			}
		}
		catch (Throwable ex)
		{
			// No camera data
		}
        return false;
    }
}
