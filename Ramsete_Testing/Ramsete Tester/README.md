# RamseteTester

## Testing Non-Linear path correction using forward kinematics
  - Implements equation 5.12 from this paper https://www.dis.uniroma1.it/~labrob/pub/papers/Ramsete01.pdf
  - Takes the resulting wheel command (left and right velocities) and converts back into linear and angular velocity.
  - Integrates both linear and angle velocity with respect to time and sums to current robot position.
  - Add resulting new robot Odometry to ArrayList of Odometry objects.
  - Repeat process for entire Trajectory.
  - Save both entire path and whole list of Odometry objects to csv (comma separated values) file.
  - Use Excel to read both files and plot them on the same graph.
