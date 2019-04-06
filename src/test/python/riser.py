# Python simulation of rise
# python 3, numpy, scipy
from pylab import *

# Time from 0 to 10 seconds in steps of 20ms
dt = 0.02
t = arange(0.0, 10.0, dt)

# Height when robot is all 'up'
top = 80
# Distance between front and back cylinders,
# i.e. 'width' of the tilt-triangle
width = 50

# Acceleration of front and back cylinders [cm/s/s],
# assuming constant force and thus constant acceleration
acc_f = 2.5
acc_b = 2.0
# Angle threshold for throttling (high number to disable)
threshold = 8

# Position of Back cylinder, simply accelerating all the time
pos_b = 1/2 * acc_b * t*t
pos_b[where(pos_b > top)] = top

# Position of Front cylinder
pos_f = []
tilt = []
f = 0
f_on = 0
time_on = 0;
for i in range(len(t)):
    tlt = degrees(arctan2(f - pos_b[i], width))
    if tlt > threshold:
        # Pause air to the front:
        # f stays where it is,
        # time with air on starts back from zero
        time_on = 0
        f_on = f
    else:
        # Keep air on:
        # Time with air increments one dt step,
        # accelerating from height f_on when air was turned on
        time_on += dt
        f = min(top, f_on + 1/2 * acc_f * time_on*time_on)
        
    pos_f.append(f)
    tilt.append(tlt)

pos_f = array(pos_f)
tilt = array(tilt)
pos_f[where(pos_f > top)] = top

print("%d data points" % len(t))
plot(t, pos_f, 'b-', t, pos_b, 'g-', t, tilt, 'r-')
title("Tilt: %.1f .. %.1f degrees" % (tilt.max(), tilt.min()))
xlabel('Time [s]')
ylabel('Height [cm] resp. Angle [degree]')
legend([ 'Front', 'Back', 'Tilt' ])
grid(True)
show()
