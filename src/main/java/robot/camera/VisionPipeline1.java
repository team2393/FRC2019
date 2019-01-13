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
public class VisionPipeline1 implements Runnable
{
    private final CameraServer server;
    private final VideoCamera camera;

    public VisionPipeline1(final CameraServer server,
                           final VideoCamera camera)
    {
        this.server = server;
        this.camera = camera;
    }

    @Override
    public void run()
    {
        // Parameters used to filter image and detect lines
        // TODO Find good hue for the camera with green LED
        final double[] hue = {0.0, 180.0};
        final double[] sat = {0.0, 255.0};
        final double[] lum = {220.0, 255.0};
        final Scalar thres_low = new Scalar(hue[0], lum[0], sat[0]);
        final Scalar thres_high = new Scalar(hue[1], lum[1], sat[1]);
        // Some can be changed via dashboard
        SmartDashboard.setDefaultNumber("Length Threshold", 10.0);
        // TODO Need two angles, one for left and one for right marker
        SmartDashboard.setDefaultNumber("Angle Threshold", 90.0);

        // Get size of original image
        final VideoMode mode = camera.getVideoMode();
        final int width = mode.width, height = mode.height;

        // Size of intermediate image used for processing the data
        final int scale = 2;
        final int proc_width = width / scale, proc_height = height / scale;
        final CvSink original = server.getVideo();
        final CvSource processed = CameraServer.getInstance().putVideo("Processed", width, height);
        
        final Mat source = new Mat();
        final Mat tmp1 = new Mat(), tmp2 = new Mat();
        final LineSegmentDetector lsd = Imgproc.createLineSegmentDetector();

        // Color for drawing overlay
        final Scalar overlay_bgr = new Scalar(220.0, 100.0, 255.0);

        while (!Thread.interrupted())
        {
            if (original.grabFrame(source) == 0)
            {   // Could not fetch frame.
                // Report error..
                System.out.println(original.getError());
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
                // Resize source so using less CPU & memory
                Imgproc.resize(source, tmp1, new Size(proc_width, proc_height));
                // tmp1 is now the smaller image

                // Scale colors to use full 0..255 range
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
                final int desired_angle = (int) SmartDashboard.getNumber("Angle Threshold", 90.0);
                
                // Used to compute average location (= 'center') of detected lines
                int points = 0;
                int cx = 0, cy = 0;

                // Add overlay to the original source
                for (int i=0;  i<tmp2.rows();  ++i)
                {
                    // line = [ x1, y1, x2, y2 ]
                    final double[] line = tmp2.get(i, 0);
                    final double dx = line[2] - line[0];
                    final double dy = line[3] - line[1];
                    
                    // Ignore lines that are too short
                    final double length = Math.sqrt(dx*dx + dy*dy);
                    if (length < min_length)
                        continue;
                
                    // Ignore lines at wrong angle
                    // Note that 'horizontal' lines can have angle 0, 180 or -180.
                    // 'Vertical' lines have angle 90 or -90.
                    // --> Normalize all angles to be within 0..180
                    final double angle = Math.toDegrees(Math.atan2(dy, dx));
                    final int norm_angle = ((int)angle + 180) % 180;
                    if (Math.abs(desired_angle - norm_angle) > 10)
                        continue;

                    // TODO Look for left vs. right marker

                    // Found a line that looks about right
                    Imgproc.line(source, new Point(line[0]*scale, line[1]*scale),
                                         new Point(line[2]*scale, line[3]*scale), overlay_bgr);
                    
                    points += 2;
                    cx += line[0]*scale + line[2]*scale;
                    cy += line[1]*scale + line[3]*scale;
                }

                // TODO Look if both left and right marker was found,
                //      and if left is indeed left of the right marker.
                //      Target should then be between those markers.

                // Arrow from middle of image to detected location
                if (points > 0)
                    Imgproc.arrowedLine(source, new Point(width/2, height/2),
                                                new Point(cx/points, cy/points), overlay_bgr);

                // Publish the source with overlay as 'Processed'
                processed.putFrame(source);
            }
        }
    }
}
