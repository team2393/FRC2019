package robot.camera;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoException;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoProperty;
import edu.wpi.first.wpilibj.DriverStation;

/** Helper for showing camera info */
public class CameraInfo
{
	/** Show camera info (and test if there is one)
	 *  @param camera Camera to use
	 *  @return true if there is a camera, false if nothing found
	 */
    public static boolean show(final UsbCamera camera)
    {
		// Another option to check for camera:
		// if (! new File("/dev/video0").exists())
		//    return false;	
		try
		{
			final VideoMode[] modes = camera.enumerateVideoModes();
			System.out.println("Video Modes:");
			for (VideoMode mode : modes)
			System.out.println(mode.width + "x" + mode.height + " @ " + mode.fps + "fps");
			
			System.out.println("Properties:");
			for (VideoProperty prop : camera.enumerateProperties())
			System.out.println(prop.getName() + " = " + prop.get());
		}
		catch (VideoException ex)
		{
			DriverStation.reportWarning("Dude, no camera?!", false);
			return false;
		}
		return true;
    }
}
