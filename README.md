2393 First
==========

![Simple Robot](blacky.jpg)

Setup for the 2019 season, generally described at 
https://www.firstinspires.org/resource-library/frc/competition-manual-qa-system and 
http://wpilib.screenstepslive.com/s/4485

May not be required, but to start fresh you can remove the 2018 software:

 * Uninstall "National Instruments Software" to remove the 2018 FRC
 * Delete folders %HOME%/wpilib, Shuffleboard, SmartDashboard, RobotBuilder

Install new tools:

 * Install .NET update from https://support.microsoft.com/en-us/help/3151800/the-net-framework-4-6-2-offline-installer-for-windows. It may tell you that it's already installed, so then just cancel.
 * Install 
WPILibInstaller_Windows64-2019.1.1.zip from https://github.com/wpilibsuite/allwpilib/releases.
  * You can select to install only for the current user (yourself).
  * In the first dialog, select to download VS Code.
  * Select all but "C++ Compiler"
 * Install FRCUpdateSuite from http://www.ni.com/download/first-robotics-software-2017/7904/en/ Yes, link looks like 2017 but there'll be a file FRCUpdateSuite_2019.0.0.zip. To unpack this ZIP, you need the code from the Kit-of-Parts.
 * Install git from https://git-scm.com/downloads


As a result, you should have FRC desktop icons for VS Code,
SmartDashboard etc which all point to software installed under 
C:\Users\Public\frc2019.

We'll update this repo to the new format.
Meanwhile, it can be imported into VS Code via the command "WPILib: Import a WPILib Eclipse project".


Below is the older information for the 2018 setup...
into Eclipse,


Git Clone
---------

Import into Eclipse:
 * Menu File
 * Import
 * Git
 * Projects from Git
 * Clone URI
 * Enter `https://github.com/kasemir/2393First`
 * Next, Next, ..., Finish
 
--> Now you have a "2393First" project in your Eclipse workspace.


Git Update
----------

To later get an update, i.e. to fetch the latest:
 * Right-click on the "2393First" project
 * Team
 * "Pull" (there's a similar option "Pull..", don't use that. Use the one just named "Pull")

If you changed the source code on your computer, you might get an error message
that mentions some "conflict".
In that case:
 * Right-click on the "2393First" project
 * Replace-With
 * HEAD Revision

.. and then try the "Pull" again.

Install WPILib
--------------

To work with the robot code, you need to install the WPIlib.

You can try this directly from the web:

 * In Eclipse, "Help", "Install new software", and then enter
   "http://first.wpi.edu/FRC/roborio/release/eclipse"


Alternatively, use a EclipsePluginsV2018.4.1.zip that you get
from a USB stick during a meetup or download from
http://first.wpi.edu/FRC/roborio/release/EclipsePluginsV2018.4.1.zip.

 * In the Windows File Explorer, unpack/extract the downloaded `EclipsePluginsV2018.4.1.zip`
 * In Eclipse, "Help", "Install new software", press "Add", "Local",
   and select the `eclipse` directory inside the extracted `EclipsePluginsV2018.4.1`.


Either way, you then continue like this:

 * Click the drop-down arrow near "WPILib Robot Development" to expand, then select "Robot _Java(!)_ Development".
 * Press Next, Accept, ..., Finish
 * You'll see "Installing Software" in the bottom right corner.
 * When prompted to restart Eclipse, do that.

WPILib is the robotics library started at Worcester Polytechnic Institute (WPI)
that is used for controlling FIRST robots.
It's an open-source project on GitHub where anybody can
make suggestions and contributions,
see https://github.com/wpilibsuite/allwpilib/issues/954

![Java Duke](duke.png)

The WPILib requires that the JAVA_HOME environment variable is set,
and the Java tools are on the Path.
To set these:

 * Open the Windows File Explorer
 * In File Explorer right-click on the This PC (or Computer) icon,
   then click Properties -> Advanced System Settings -> Environmental Variables.
 * Add
   `JAVA_HOME = C:\Program Files\Java\jdk1.8.0_171`
 * Select the `Path` variable, and add an entry for `C:\Program Files\Java\jdk1.8.0_171`


Running
-------

Code in the `first` and `bank` packages can run via 'Run As', 'Java Application'.

For anything in `robot` you need the WPILib. That way you can view and edit the code.
To actually run it, you'll need the roboRIO hardware which we'll have in the meetings.


Eclipse Editor Tweaks
---------------------

 * Menu Window, Preferences, Java, Code Style, Formatter, Import
 * Select YourHomeDir, git, 2393First, Formatter.xml


 * Menu Window, Preferences, Java, Editor, Save Actions:
 * [x] Perform the selection actions on save,
 * [x] Organize imports
 * [x] Additional Actions, 'Configure':
     * Code Organizing: Remove training whitespace
     * Unnecessary Code: Remove unused imports

