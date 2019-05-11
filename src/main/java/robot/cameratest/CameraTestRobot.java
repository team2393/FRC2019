
package robot.cameratest;

import robot.BasicRobot;
import robot.camera.CameraHandler;
import robot.camera.VisionProcessor;

public class CameraTestRobot extends BasicRobot
{
    private CameraHandler camera;
    private VisionProcessor processor = new FindCenter();

    @Override
    public void robotInit()
    {
		camera = new CameraHandler(320, 240, 10, processor);
    }
}
