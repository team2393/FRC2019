package robot.cameratest;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import edu.wpi.cscore.CvSource;
import edu.wpi.first.cameraserver.CameraServer;
import robot.camera.VisionProcessor;

/** Vision processor that locates the 'center' of some bright spot in the image */
public class FindCenter implements VisionProcessor
{
    private int width, height;
    private CvSource processed;
    private Mat output = null;
    private byte[] data;

    @Override
    public void init(final CameraServer server, final int width, final int height)
    {
        // Remember original image size
        this.width = width;
        this.height = height;
        
        // Create video stream for processed image
        processed = server.putVideo("Processed", width, height);
    }

    @Override
    public void process(final Mat original)
    {
        // Sometimes, the camera cannot get an image...
        if (original == null)
            return;
            
        // Shows "320x240 by 3 of CV_8UC3":
        // System.out.println(original.size() + " by " + original.channels() +
        //                    " of " + CvType.typeToString(original.type()));
        // 8-bit integer (byte), unsigned (0..255) image with 3 channels.
        // Would assume 3 channels = RGB, but OpenCV uses BGR.
        
        // Get data buffer for image
        // https://answers.opencv.org/question/5/how-to-get-and-modify-the-pixel-of-mat-in-java/
        // original.copyTo(output);
        // byte[] data = new byte[width * height * 3];
        // output.get(0, 0, data);

        if (output == null)
        {
            // When called for the first time,
            // get copy of the image Mat..
            output = original.clone();
            // .. and allocate space for data
            data = new byte[width * height * 3];
        }
        // From then on, copy image data into our data buffer
        original.get(0, 0, data);

        // Manipulate data

        // Example: Draw horizontal line through middle of image
        final int mid_y = height / 2;
        for (int x=0; x<width; ++x)
        {
            // Index of B, G, R for the pixel
            final int pixel = 3*(x + mid_y*width);
            data[pixel] = 0;
            data[pixel+1] = (byte)255;
            data[pixel+2] = (byte)255;
        }

        // TODO Find brightest area in some line(s)
        // TODO Draw vertical line where that area was found

        // Place data in output and publish
        output.put(0, 0, data);
        processed.putFrame(output);
    }
}
