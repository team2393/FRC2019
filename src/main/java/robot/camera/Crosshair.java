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
import robot.camera.GripPipeline.Line;

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
        // Get size of original image
        final VideoMode mode = camera.getVideoMode();
        final int width = mode.width, height = mode.height;
        final int mid_x = width  / 2, mid_y = height / 2;

        final CvSink original = server.getVideo();
        final CvSource processed = CameraServer.getInstance().putVideo("Processed", width, height);
        
        final Mat source = new Mat(), output = new Mat();
        final Scalar color = new Scalar(200.0, 200.0, 200.0);

        // TODO final GripPipeline pipeline = new GripPipeline();
        while (!Thread.interrupted())
        {
            if (original.grabFrame(source) == 0)
            {
                // Could not fetch frame.
                // Report error..
                System.out.println(original.getError());
                // .. and wait a little in hope of better luck next time
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
                // TODO Example for passing image to GRIP pipeline
                // pipeline.process(source);
                // .. and then doing something with the result
                // if (pipeline.filterLinesOutput().size() > 0)
                // {
                //     Line line = pipeline.filterLinesOutput().get(0);
                //     // .. 
                //     Imgproc.line(output, new Point(line.x1, line.y1), new Point(line.x2, line.y2), color);
                // }
                // Convert to grayscale
                Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
            
                // Horizontal arrows
                Imgproc.arrowedLine(output, new Point(0, mid_y), new Point(mid_x - 50, mid_y), color);
                Imgproc.arrowedLine(output, new Point(width, mid_y), new Point(mid_x+50, mid_y), color);

                // Vertical arrows
                Imgproc.arrowedLine(output, new Point(mid_x, 0), new Point(mid_x, mid_y - 50), color);
                Imgproc.arrowedLine(output, new Point(mid_x, height), new Point(mid_x, mid_y+50), color);

                // Publish the result
                processed.putFrame(output);
            }
        }
    }
}
