package robot.camera;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.vision.VisionPipeline;

/** Video processor that adds a crosshair */
public class Crosshair implements VisionPipeline
{
    private final int width, height, mid_x, mid_y;
    private final Scalar color = new Scalar(200.0, 200.0, 200.0);
    private final CvSource processed;
    private final Mat output = new Mat();

    public Crosshair(final int width, final int height)
    {
        // Remember original image size
        this.width = width;
        this.height = height;
        // Compute center of image
        mid_x = width  / 2;
        mid_y = height / 2;
        // Create video stream for processed image
        processed = CameraServer.getInstance().putVideo("Processed", width, height);
    }

    @Override
    public void process(final Mat original)
    {
        if (original == null)
            return;

        // Convert to grayscale
        Imgproc.cvtColor(original, output, Imgproc.COLOR_BGR2GRAY);
    
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
