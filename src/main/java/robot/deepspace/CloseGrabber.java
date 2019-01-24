/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package robot.deepspace;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Add your docs here.
 */
public class CloseGrabber extends InstantCommand
{
  private PanelGrabber grabber;

  public CloseGrabber(PanelGrabber grabber)
  {
    this.grabber = grabber;
  }

  @Override
  protected void execute()
  {
    grabber.open(false);
  }
}
