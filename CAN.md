CAN Bus
=======

The CAN bus uses a daisy-chain of two wires, usually a twisted pair of green and yellow,
to connect up to 64 devices to the roboRIO.
Each device has a CAN ID, a number from 0 to 63.

The chain starts at the roboRIO, runs to the first device, on to the next device and so on.
The bus must be _terminated_ with a 120 Ohm resistor.
The Power Distribution Panel includes such a termination resistor,
so the PDP must be at the __end__ of the daisy-chain
and the PDP termination must be enabled via a jumper next to its CAN connector.
If there is no PDP at the end, you need to terminate the bus.

The WPIlib includes several classes for CAN-based devices.
You can put those marked as [DB] onto the SmartDashboard to display their state or operate them in teleop.

 * `PowerDistributionPanel`: Get info from PDP [DB]
 * `Solenoid`: One of the Pnematic Control Module (PCM) outputs [DB]
 * `DoubleSolenoid`: Similar, controls 2 outputs at once [DB]
 * `Compressor`: PCM status info [DB]
 * `CAN`: Would allow reading/writing raw data to a CAN device.

In principle you could thus use the PDP and PCM without further ado,
except that their CAN ID defaults to 0, so you could only use one of them.

To program CAN IDs, to monitor the state of the CAN bus, and to get support
for additional CAN devices like motor controllers, you need to install the
Cross-The-Road-Electronics "Phoenix" software,
https://phoenix-documentation.readthedocs.io/en/latest/index.html:

 * Download / unzip CTRE Phoenix Framework ZIP and run the *.exe.
   It should auto-select Lifeboard, Tuner, C++/Java.
 * Start the CTRE Phoenix Tuner via desktop link
   (Should be in
    C:\Users\Public\Documents\Cross The Road Electronics\Phoenix-Tuner)
 * Install Phoenix Library / Diagnostics with Tuner.

Now you can configure your CAN devices:

 1) Connect the roboRIO CAN bus to the Power Distribution Panel
    (with termination enabled), and use the Tuner to set its CAN ID.
 2) Change the CAN bus to go roboRIO to Pneumatics to PDP,
    then use the Tuner to set the PDP's CAN ID.
 3) Add more devices, one at a time, to set their CAN ID.


To use CTRE devices like `TalonSRX` in Java code:

 1) Right-click on the file `build.gradle`
 2) Select "Manage Vendor Libraries"
 3) Select "Install new libraries (offline)"
 4) Select "CTRE-Phoenix"
