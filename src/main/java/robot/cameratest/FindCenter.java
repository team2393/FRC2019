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
        // https://answers.opencv.org/question/5/how-to-get-and-modify-the-pixel-of-mat-in-java/
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

        // We check pixels along a horizontal line at the middle
        // of the image
        final int mid_y = height / 2;
        int max_val = 0;
        int max_x = -1;
        for (int x=0; x<width; ++x)
        {
            // Index of B, G, R for the pixel at coordinate (x, mid_y)
            int pixel = 3*(x + mid_y*width);
            // Three bytes starting at data[pixel],
            // note that we need the 'unsigned' value 0..255,
            // not the 'signed' value -128..127
            int blue = Byte.toUnsignedInt(data[pixel]);
            int green = Byte.toUnsignedInt(data[pixel + 1]);
            int red = Byte.toUnsignedInt(data[pixel + 2]);

            // Compute brightness of that RGB pixel by taking average
            int average = (blue  + red + green) / 3;

            // Is this the brightest pixel?
            if(average > max_val)
            {
                max_val = average;
                max_x = x;
            }

            int y = mid_y - average * 100 / 255;
            pixel = 3*(x + y*width);
            data[pixel] = 50;
            data[pixel+1] = (byte)0;
            data[pixel+2] = (byte)100;
        }

        if(max_x > 0)
        {
            for (int y=0; y<height; ++y)
            {
                // Index of B, G, R for the pixel
                final int pixel = 3*(max_x + y*width);
                data[pixel] = 50;
                data[pixel+1] = (byte)0;
                data[pixel+2] = (byte)100;
            }    
        }

        for (int x=0; x<width; ++x)
        {
            // Index of B, G, R for the pixel
            final int pixel = 3*(x + mid_y*width);
            data[pixel] = 50;
            data[pixel+1] = (byte)0;
            data[pixel+2] = (byte)100;
        }

        // Place data in output and publish
        output.put(0, 0, data);
        processed.putFrame(output);
    }
}
