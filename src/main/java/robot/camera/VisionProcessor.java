package robot.camera;

import org.opencv.core.Mat;

import edu.wpi.first.cameraserver.CameraServer;

/** Interface for a class that processes images */
public interface VisionProcessor
{
    /** Called once to initialize
     * 
     *  May for example create a "Processed" video
     *  @param server CameraServer
     *  @param width Size of original images
     *  @param height Size of original images
     */
    public void init(CameraServer server, int width, int height);

    /** Called with images from camera
     *  or null when there is no image
     *  @param original Original image from camera
     */
    public void process(final Mat original);
}
