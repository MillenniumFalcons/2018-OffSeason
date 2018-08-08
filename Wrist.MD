# Programming a wrist #

## Introduction ##
Programming a wrist is very similar to programming an elevator. The primary differences are that the elevator has 5 different levels, whereas the wrist only had 3. Other notable differences are that the elevator is controlled by 2 SPX's and 2 SRX's, while the wrist is controlled by one SRX. The wrist also has a limit switch, that tell you wheater the wrist is flat or not (very similar to how the banner sensor tells you if the elevator is all the way down or not).

To write the wrist program, we will be working with both the [Robot.java](https://github.com/MillenniumFalcons/2018-PowerUp/blob/master/Robot_Code/src/org/usfirst/frc/team3647/robot/Robot.java) file, and the [Wrist.java](https://github.com/MillenniumFalcons/2018-PowerUp/blob/master/Robot_Code/src/team3647elevator/Wrist.java) file. You can also feel free to look at the [Elevator.java](https://github.com/MillenniumFalcons/2018-PowerUp/blob/master/Robot_Code/src/team3647elevator/Elevator.java) file for assitance.

## Step 1: Make sure the wrist motor works ##
1. First create a static ```WPI_TalonSRX``` motor called ```wristMotor```.
2. Create a static function called ```moveWrist()``` that takes in the parameter ```speed```. In this function you will set the speed of the ```wristMotor``` to the ```speed``` variable. ```wristMotor.set(ControlMode.PercentOutput, speed);```
3. Create a function called ```stopWrist``` that sets the speed of the ```wristMotor``` to 0.
4. _For testing purposes go to the ```runElevator()``` function and add ```Wrist.moveWrist(joy.leftJoySticky1);``` after ```eleVader.setElevatorEncoder();```. To test if this works you would have to move the right joystick on the co-driver controller._
5. Make sure that the wrist moves up when you send it a positive value to the ```moveWrist()``` function that the wrist rotates upward, and that the wrist moves downward if you send a negative value to the ```moveWrist()``` function. If it is flipped, change ```wristMotor.set(ControlMode.PercentOutput, -speed);``` to ```wristMotor.set(ControlMode.PercentOutput, speed);```

For steps 1 - 3 use the [Wrist.java](https://github.com/MillenniumFalcons/2018-PowerUp/blob/master/Robot_Code/src/team3647elevator/Wrist.java) file, and for step 4 use the [Robot.java](https://github.com/MillenniumFalcons/2018-PowerUp/blob/master/Robot_Code/src/org/usfirst/frc/team3647/robot/Robot.java) file.

## Step 2: Adjust the speed of the wrist ##
Since I am out of town, set the wrist to a constant speed for now. Try to pick a speed value that is not too fast, not too slow. So for example if you set ```.2``` as your speed, make the wrist move at ```.2``` whenever the joystick value is greater than ```0```,  and make the wrist move at ```-.2``` when the joystick value is less than ```0```. You guys are smart enough to figure this out.
