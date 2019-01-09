2393 First
==========

![Simple Robot](blacky.jpg)

Setup for the 2019 Season
-------------------------

See 
https://www.firstinspires.org/resource-library/frc/competition-manual-qa-system and 
http://wpilib.screenstepslive.com/s/4485

The software team has a USB stick with all the software,
passwords and step-by-step instructions.

We're using Java and WPILib,
the robotics library started at Worcester Polytechnic Institute (WPI)
that is used for controlling FIRST robots.
It's an open-source project on GitHub where anybody can
make suggestions and contributions,
see https://github.com/wpilibsuite/allwpilib/issues/954

![Java Duke](duke.png)

Git Clone & Open in VS Code
---------------------------

If you already had a git clone of this repo from previous Eclipse-based work,
go there in a git bash, type `git pull` to get the latest,
then use File, Open Folder in VS Code to open.

If you do not have a git clone, open VS Studio,
invoke View, Command Palette, and type "Git: Clone".
Enter this URL: `https://github.com/kasemir/2393First`
Create & browse to a folder `git` in your home directory.

Git Update
----------

To later get an update, i.e. to fetch the latest,
invoke View, Command Palette, and type "Git: Pull".
You can also use View, SCM, and then invoke "Pull"
from the "..." menu.



Running
-------

Code in the `first` and `bank` packages can run by opening those
files that contain a `main()` method and pushing the "Run" button
that will appear above the `main()` method.
When running code that reads from `System.in` you might notice that
the code runs in the internal console which doesn't permit keyboard input.
Locate the launch configuration in the file `.vscode/launch.json`.
Add a line
```
"console": "integratedTerminal",
```
and now the code will run in a terminal that allows keyboard input.

For anything in `robot` you need the WPILib. That way you can view and edit the code.
To compile, invoke "Build Robot Code" from the upper right "..." menu.
To actually run it, you deploy to the roboRIO hardware via "Deploy Robot Code"
in the same menu.
