package robot.camera;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.VideoCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;

/** Video processor that adds a crosshair */
public class Crosshair implements Runnable
{
    private final CameraServer server;
    private final VideoCamera camera;

    public Crosshair(final CameraServer server,
                     final VideoCamera camera)
    {
        this.server = server;
        this.camera = camera;
    }

    @Override
    public void run()
    {
        final VideoMode mode = camera.getVideoMode();
        final CvSink video = server.getVideo();
        final CvSource processed = CameraServer.getInstance().putVideo("Processed", mode.width, mode.height);
        final int mid_x = mode.width/2;
        final int mid_y = mode.height/2;

        final Mat source = new Mat();
        final Mat output = new Mat();
        while (!Thread.interrupted())
        {
            if (video.grabFrame(source) == 0)
            {
                System.out.println(video.getError());
                try
                {
                    Thread.sleep(1000);
                }
                catch (Exception ex)
                {
                    // Ignore
                }
            }
            else
            {
                Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
                // Imgproc.HoughLines() ?!
                // Horizontal arrows
                Imgproc.arrowedLine(output, new Point(0, mid_y), new Point(mid_x-50, mid_y), new Scalar(200.0, 200.0, 200.0));
                Imgproc.arrowedLine(output, new Point(mode.width, mid_y), new Point(mid_x+50, mid_y), new Scalar(200.0, 200.0, 200.0));

                // Vertical arrows
                Imgproc.arrowedLine(output, new Point(mid_x, 0), new Point(mid_x, mid_y-50), new Scalar(200.0, 200.0, 200.0));
                Imgproc.arrowedLine(output, new Point(mid_x, mode.height), new Point(mid_x, mid_y+50), new Scalar(200.0, 200.0, 200.0));

                processed.putFrame(output);
            }
        }
    }
}
