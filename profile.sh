# This configures the RoboRIO to enable profiling
#
# Run from  from the GIT bash:
#
# Start GIT bash
# cd git/2393First
# source profile.sh
#
# For details, see README.md

#.. when connected via USB
IP=172.22.11.2
#.. when connected via Radio
#IP=10.23.93.2

# Show current robot command
ssh lvuser@$IP "cat robotCommand"

# Insert the profile settings
# Basic idea is to insert the new settings at the (X) in
# /usr/local/frc/JRE/bin/java (X) -Djava.library.path..
ssh lvuser@$IP "sed -i s/java\ -Djava/java\ -Dcom.sun.management.jmxremote=true\ -Dcom.sun.management.jmxremote.port=1099\ -Dcom.sun.management.jmxremote.local.only=false\ -Dcom.sun.management.jmxremote.ssl=false\ -Dcom.sun.management.jmxremote.authenticate=false\ -Djava.rmi.server.hostname=$IP\ -Djava/ robotCommand"

# Show (hopefully) updated robot command
ssh lvuser@$IP "cat robotCommand"

# Restart
ssh lvuser@$IP "/usr/local/frc/bin/frcKillRobot.sh"
