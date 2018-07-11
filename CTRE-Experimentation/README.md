# CTRE Experimentation with PID #

In this project you will be making changes to the ```driveTrainInitialization()``` function in the [Drivetrain.java](https://github.com/MillenniumFalcons/2018-OffSeason/blob/master/CTRE-Experimentation/src/org/usfirst/frc/team3647/robot/Drivetrain.java) file. More specifically, you will be configuring the PID values for each individual SRX controller. You will be changing the code in the following areas:
* Right motor
```
rightSRX.config_kF(Constants.kPIDLoopIdx, 0.1097, Constants.kTimeoutMs);
rightSRX.config_kP(Constants.kPIDLoopIdx, 0.113333, Constants.kTimeoutMs);
rightSRX.config_kI(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
rightSRX.config_kD(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
```
* Left motor
```
leftSRX.config_kF(Constants.kPIDLoopIdx, 0.1097, Constants.kTimeoutMs);
leftSRX.config_kP(Constants.kPIDLoopIdx, 0.113333, Constants.kTimeoutMs);
leftSRX.config_kI(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
leftSRX.config_kD(Constants.kPIDLoopIdx, 0, Constants.kTimeoutMs);
```


Here are 2 resources that will help you with setting the PID values:
* [CTRE Documentation](https://github.com/CrossTheRoadElec/Phoenix-Documentation/blob/master/README.md#feed-forward-kf)
* [Some Other Resource](http://www.ecircuitcenter.com/Circuits/pid1/pid1.htm)


If you have trouble finding where the file is, just download the zip from [here](https://github.com/MillenniumFalcons/2018-OffSeason/blob/master/CTRE-Experimentation.zip).
