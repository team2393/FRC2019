# Python simulation of rise
import numpy as np
import matplotlib.pyplot as plt

# Time from 0 to 10 seconds in steps of 20ms
dt = 0.02
t = np.arange(0.0, 10.0, dt)

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

# Alterate examples: Faster front, same back, react for just 2 deg.
#(acc_f, acc_b, threshold) = (5, 2, 2)

# Front outrageously fast, back quite slow, correcting for as little as 1 deg
# --> Looks great, but then again requires very fast front
#(acc_f, acc_b, threshold) = (50, 1, 1)

# Position of Back cylinder, simply accelerating all the time
pos_b = 1/2 * acc_b * t*t
pos_b[np.where(pos_b > top)] = top

# Position of Front cylinder
pos_f = []
tilt = []
f = 0
f_on = 0
time_on = 0;
for i in range(len(t)):
    tlt = np.degrees(np.arctan2(f - pos_b[i], width))
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

pos_f = np.array(pos_f)
tilt = np.array(tilt)

print("%d data points" % len(t))
plt.plot(t, pos_f, 'b-', t, pos_b, 'g-', t, tilt, 'r-')
plt.title("Tilt: %.1f .. %.1f degrees" % (tilt.max(), tilt.min()))
plt.xlabel('Time [s]')
plt.ylabel('Height [cm] resp. Angle [degree]')
plt.legend([ 'Front', 'Back', 'Tilt' ])
plt.grid(True)
plt.show()
