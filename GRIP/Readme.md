GRIP
----

These are example images and config files for the GRIP vision pipeline development tool.

Unfortunately, the *.grip files contain the complete path to test images.
So instead of for example just "rocket.png", the file will contain something like

    <grip:ImageFile>
      <property name="path" value="C:\Users\USERNAME\git\2393First\GRIP\rocket.png"/>

So before loading the file into GRIP, you might have to edit that file path.

Brief explanation of all operations:
https://github.com/WPIRoboticsProjects/GRIP/wiki/Operation-Reference-Table

The "VisionProcessing" doc includes examples from previous years:
https://wpilib.screenstepslive.com/s/currentCS/m/vision