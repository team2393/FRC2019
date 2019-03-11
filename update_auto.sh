# Update auto moves
##
# Start GIT bash
# cd git/2393First
# source update_auto.sh

#.. when connected via USB
#IP=172.22.11.2
#.. when connected via Radio
IP=10.23.93.2

# Copy auto file to robot
scp src/main/deploy/deepspace_auto.dat lvuser@$IP:/home/lvuser/deploy

# Restart
ssh lvuser@$IP "/usr/local/frc/bin/frcKillRobot.sh"
