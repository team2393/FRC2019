package robot.camera;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoProperty;

/** Helper for showing camera info */
public class CameraInfo
{
    public static void show(UsbCamera camera)
    {
		System.out.println("Video Modes:");
		for (VideoMode mode : camera.enumerateVideoModes())
			System.out.println(mode.width + "x" + mode.height + " @ " + mode.fps + "fps");

		System.out.println("Properties:");
		for (VideoProperty prop : camera.enumerateProperties())
			System.out.println(prop.getName() + " = " + prop.get());
    }
}
