package robot.camera;

import java.io.File;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoException;
import edu.wpi.cscore.VideoMode;
import edu.wpi.cscore.VideoProperty;
import edu.wpi.first.wpilibj.DriverStation;

/** Helper for showing camera info */
public class CameraInfo
{
	/** @return true if we have indications that a camera is available */
	public static boolean haveCamera()
	{
		return new File("/dev/video0").exists();	
	}

	/** Show camera info (and test if there is one)
	 *  @param camera Camera to use
	 *  @return true if there is a camera, false if nothing found
	 */
    public static boolean show(final UsbCamera camera)
    {
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
