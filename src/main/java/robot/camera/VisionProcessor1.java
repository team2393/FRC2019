package robot.camera;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.LineSegmentDetector;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.VideoCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/** Video processor that looks for target markers */
public class VisionProcessor1 implements Runnable
{
    private final CameraServer server;
    private final VideoCamera camera;

    public VisionProcessor1(final CameraServer server, final VideoCamera camera)
    {
        this.server = server;
        this.camera = camera;
    }

    @Override
    public void run()
    {
        // Parameters used to filter image and detect lines:
        // "Green" hue for the camera with green LED
        final double[] hue = { 70.0, 90.0 };
        // Only well saturated colors
        final double[] sat = { 200.0, 255.0 };
        // In principle, we're looking for bright colors near 255,
        // but in tests including darker values worked better.
        final double[] lum = { 130.0, 255.0 };

        final Scalar thres_low = new Scalar(hue[0], lum[0], sat[0]);
        final Scalar thres_high = new Scalar(hue[1], lum[1], sat[1]);
        // Some can be changed via dashboard
        SmartDashboard.setDefaultNumber("Length Threshold", 10.0);
        // Angle range +- the nominal 14.5 deg
        SmartDashboard.setDefaultNumber("Angle Width", 10.0);

        // Get size of original image
        final VideoMode mode = camera.getVideoMode();
        final int width = mode.width, height = mode.height;
        final Point center = new Point(width / 2, height / 2);
        final CvSink video = server.getVideo();

        // Size of intermediate image used for processing the data
        final int scale = 2;
        final int proc_width = width / scale, proc_height = height / scale;
        final CvSource processed = CameraServer.getInstance().putVideo("Processed", width, height);

        // Mats for the original image processed image information
        final Mat original = new Mat();
        final Mat tmp1 = new Mat(), tmp2 = new Mat();

        // Line finder
        final LineSegmentDetector lsd = Imgproc.createLineSegmentDetector();

        // Color for drawing overlay
        final Scalar overlay_bgr = new Scalar(200.0, 100.0, 255.0);

        // Keep processing images until this thread is interrupted
        while (!Thread.interrupted())
        {
            if (video.grabFrame(original) == 0)
            {   // Could not fetch frame.
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
            {
                // Resize to use less CPU & memory to process 
                Imgproc.resize(original, tmp1, new Size(proc_width, proc_height));
                // tmp1 is now the smaller image

                // Scale colors to use full 0..255 range in case image was dark
                Core.normalize(tmp1, tmp2, 0.0, 255.0, Core.NORM_MINMAX);
                // tmp2 is normalized

                // Find specific hue, saturation, lunimance
                Imgproc.cvtColor(tmp2, tmp1, Imgproc.COLOR_BGR2HLS);
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
                int l_x = 0, l_y = 0, r_x = 0, r_y = 0;

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
                        // Compute average of start and end of all lines
                        // so we can later point into the general direction of them
                        l_count += 2;
                        l_x += line[0] * scale + line[2] * scale;
                        l_y += line[1] * scale + line[3] * scale;
                    }
                    else if (Math.abs(right_angle - norm_angle) < angle_width)
                    {
                        r_count += 2;
                        r_x += line[0] * scale + line[2] * scale;
                        r_y += line[1] * scale + line[3] * scale;
                    }
                    else
                        continue;

                    // Found a line that looks about right.
                    // Draw it into the original(!) image.
                    // -> Need to scale coordinates back from the resized image.
                    Imgproc.line(original, new Point(line[0] * scale, line[1] * scale),
                            new Point(line[2] * scale, line[3] * scale), overlay_bgr);
                }

                // Did we find at least one line (i.e. 2 points) at the 'left' and 'right'?
                if (l_count >= 2  &&  r_count >= 2)
                {
                    int left_x = l_x / l_count;
                    int right_x = r_x / r_count;
                    // Is the 'left' marker actually on the left?
                    if (left_x < right_x)
                    {
                        // Imgproc.line(original, center,
                        //         new Point(left_x, l_y / l_count), overlay_bgr);
                        // Imgproc.line(original, center,
                        //         new Point(right_x, r_y / r_count), overlay_bgr);
                        Imgproc.arrowedLine(original, center,
                              new Point((left_x + right_x)/2, center.y), overlay_bgr);
                    }
                }
        
                // Publish the source with overlay as 'Processed'
                processed.putFrame(original);
            }
        }
    }
}
