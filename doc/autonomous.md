# Autonomous

## Basic procedure

Here's a basic overview of how autonomous works (assuming that we start from the parking zone):

1. Take a reading of the IR direction
2. Based on that reading, determine which position the center goal is
3. Using the position, dead-reckon to somewhere on the field where we're facing the IR sensor
4. Run the linefollower algorithm (based on Tim's sensor data) to close in on the goal
5. Dispense

## Tuning

### Adjusting how the field position is determined

Take a look at `GetIRFieldPosition()` in `autonomous/IRLineFollower.c`. You can basically do whatever you want in that function, and as long as it returns a number from 1-3, the rest of the code should Just Work(tm).
