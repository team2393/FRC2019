/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot.deepspace.riser;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;
import robot.deepspace.RobotMap;

/**
 * Add your docs here.
 */
public class Riser {
    private final Solenoid front_riser = new Solenoid(RobotMap.FRONT_RISER_SOLENOID);
    private final Solenoid back_riser = new Solenoid(RobotMap.BACK_RISER_SOLENOID);
    private final Victor drive = new Victor(RobotMap.RISER_MOTOR);

    public void dropFront(boolean down)
    {
        front_riser.set(down);
    }

    public void dropBack(boolean down)
    {
        back_riser.set(down);
    }

    public void setSpeed(double speed)
    {
        drive.set(speed);
    }
}
