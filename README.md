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
 * In Eclipse, "Help", "Install new software", "Add", "Local",
 * Select the `eclipse` directory inside the expanded `EclipsePluginsV2018.4.1`.
 * Click the drop-down arraoy near "WPILib Robot Development" to expand, then select "Robot _Java_ Development".
 * Press Next, Accept, ..., Finish
 * You'll see "Installing Software" in the bottom right corner.
 * When prompted to restart Eclipse, do that.
 
In addition, you need to install the roboRIO support and Driver Station from `FRCUpdateSuite.zip`.

To then run robot code, use 'Run As', 'WPILib Java Deploy'.
