package robot.camera;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.LineSegmentDetector;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Vision processor that looks for target markers */
public class MarkerDetector implements VisionProcessor
{
    // Parameters used to filter image and detect lines:
    // "Green" hue, 70..90 for the camera with green LED
    //
    // Only well saturated colors, 200..255
    //
    // In principle, we're looking for bright colors near 255,
    // but in tests including darker values, 130..255 worked better.
    private final Scalar thres_low  = new Scalar( 70.0, 200.0, 130.0);
    private final Scalar thres_high = new Scalar( 90.0, 255.0, 255.0);

    // Center of original image
    private Point center;

    // Size of intermediate image used for processing the data
    private final int scale = 2;
    private int proc_width, proc_height;
    // Intermediate images used for processing
    private final Mat tmp1 = new Mat(), tmp2 = new Mat();
    
    // Line finder
    private final LineSegmentDetector lsd = Imgproc.createLineSegmentDetector();
    
    // RGB Color for drawing overlay
    private final Scalar overlay_bgr = new Scalar(200.0, 100.0, 255.0);
    
    // Video stream for processed image (will again have same size of original)
    private CvSource processed;

    // Direction from center of image to the markers
    private volatile double direction = Double.NaN;

    @Override
    public void init(final CameraServer server, int width, final int height)
    {
        center = new Point(width / 2, height / 2);
     
        // Size of intermediate image used for processing the data
        proc_width  = width  / scale;
        proc_height = height / scale;
        processed = CameraServer.getInstance().putVideo("Processed", width, height);

        // Fit parameters that can be changed via dashboard
        // Hue in terms of center and +- width
        SmartDashboard.setDefaultNumber("Hue", 135.0);
        SmartDashboard.setDefaultNumber("Hue Width", 65.0);
        // Lower end of sat and lum range
        SmartDashboard.setDefaultNumber("Saturation", 200.0);
        SmartDashboard.setDefaultNumber("Luminance", 130.0);
        // Minimum line length
        SmartDashboard.setDefaultNumber("Length Threshold", 10.0);
        // Angle range +- the nominal 14.5 deg
        SmartDashboard.setDefaultNumber("Angle Width", 10.0);
    }

    @Override
    public void process(final Mat original)
    {
        if (original == null)
        {
            direction = Double.NaN;
            return;
        }

        // Resize to use less CPU & memory to process 
        Imgproc.resize(original, tmp1, new Size(proc_width, proc_height));
        // tmp1 is now the smaller image

        // Scale colors to use full 0..255 range in case image was dark
        Core.normalize(tmp1, tmp2, 0.0, 255.0, Core.NORM_MINMAX);
        // tmp2 is normalized

        // Find specific hue, saturation, lunimance
        Imgproc.cvtColor(tmp2, tmp1, Imgproc.COLOR_BGR2HLS);
        // Update start of HSV range from dashboard
        final double hue = SmartDashboard.getNumber("Hue", 135.0);
        final double hue_wid = SmartDashboard.getNumber("Hue Width", 65.0);
        thres_low.val[0] = hue - hue_wid;
        thres_low.val[1] = SmartDashboard.getNumber("Saturation", 200.0);
        thres_low.val[2] = SmartDashboard.getNumber("Luminance", 130.0);
        // Update end of hue from dash. End of sat and lum stay at 255
        thres_high.val[0] = hue + hue_wid;
        Core.inRange(tmp1, thres_low, thres_high, tmp1);
        // tmp1 is now a black/white image (mask)

        // Detect lines
        lsd.detect(tmp1, tmp2);
        // tmp1 now contains the lines

        // Check which lines look like they belong to the target markers
        final double min_length = SmartDashboard.getNumber("Length Threshold", 10.0);
        final int angle_width = (int) SmartDashboard.getNumber("Angle Width", 10.0);

        // Find 'left' marker
        final int left_angle  = 90 + 15;
        final int right_angle = 90 - 15;

        // Used to compute average location (= 'center') of detected lines
        int l_count = 0, r_count = 0;
        int l_x = 0, r_x = 0;

        // Check all detected lines
        for (int i = 0; i < tmp2.rows(); ++i)
        {
            // line = [ x1, y1, x2, y2 ] for line from ( x1, y1 ) to ( x2, y2 )
            final double[] line = tmp2.get(i, 0);
            // Compute x2 - x1 and y2 - y1
            final double dx = line[2] - line[0];
            final double dy = line[3] - line[1];

            // Length of line
            final double length = Math.sqrt(dx * dx + dy * dy);
            // Ignore lines that are too short
            if (length < min_length)
                continue;

            // Angle of line, convert from radians to degrees
            final double angle = Math.toDegrees(Math.atan2(dy, dx));
            // To us, the direction of the line doesn't really matter.
            // A line from ( x1, y1 ) to ( x2, y2 ) looks just like
            // a line from ( x2, y2 ) to ( x1, y1 ).
            // But the computed angle would differ.
            // 'Horizontal' lines can have angle 0, 180 or -180.
            // 'Vertical' lines have angle 90 or -90.
            // --> Normalize all angles to be within 0..180
            final int norm_angle = ((int) angle + 180) % 180;
            // Is it close to the desired 'left' or 'right' marker's angle?
            if (Math.abs(left_angle - norm_angle) < angle_width)
            {
                // Compute average of start and end of all 'left' lines
                // so we can later point into the general direction of them
                l_count += 2;
                l_x += line[0] * scale + line[2] * scale;
            }
            else if (Math.abs(right_angle - norm_angle) < angle_width)
            {   // Found a 'right' line. Add its start & end to average
                r_count += 2;
                r_x += line[0] * scale + line[2] * scale;
            }
            else
                continue;

            // Found a line that looks about right.
            // Draw it into the original(!) image.
            // -> Need to scale coordinates back from the resized image.
            Imgproc.line(original,
                         new Point(line[0] * scale, line[1] * scale),
                         new Point(line[2] * scale, line[3] * scale),
                         overlay_bgr);
        }

        // Did we find at least one line (i.e. 2 points) at the 'left' and 'right'?
        if (l_count >= 2  &&  r_count >= 2)
        {
            int left_x = l_x / l_count;
            int right_x = r_x / r_count;
            // Is the 'left' marker actually on the left?
            if (left_x < right_x)
            {
                final int markers_x = (left_x + right_x)/2;
                direction = markers_x - center.x;
            }
            else
                direction = Double.NaN;
        }
        else
            direction = Double.NaN;
        
        Imgproc.circle(original, center, 3, overlay_bgr);
        if (! Double.isNaN(direction))
            Imgproc.arrowedLine(original,
                                center,
                                new Point(center.x + direction, center.y),
                                overlay_bgr);
        
        // Publish the source with overlay as 'Processed'
        processed.putFrame(original);
    }

    /**@return Direction to markers.
     *         Negative for 'left',
     *         positive for 'right',
     *         NaN when not known.
     *         Units: Image pixels.
     */
    public double getDirection()
    {
        return direction;
    }
}
