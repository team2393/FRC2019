2393 First Tutorial
===================

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

To later get an update:
 * Right-click on the "2393First" project
 * Team
 * "Pull" (not "Pull..")


Running
-------

Code in the `first` and `bank` packages can run via 'Run As', 'Java Application'.

For anything in `robot` you need the WPILib:

 * Unpack `EclipsePluginsV2018.4.1.zip`
 * In Eclipse, "Help", "Install new software", "Add Repository", "Local",
 * Select the `eclipse` directory inside the expanded `EclipsePluginsV2018.4.1`.
 * Select WPILib Robot Development, Robot _Java_ Development.
 
In addition, you need to install the roboRIO support and Driver Station from `FRCUpdateSuite.zip`.

To then run robot code, use 'Run As', 'WPILib Java Deploy'.
