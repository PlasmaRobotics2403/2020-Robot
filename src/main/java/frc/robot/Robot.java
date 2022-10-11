/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.auto.modes.MoveFromLine;
import frc.robot.auto.modes.Nothing;
import frc.robot.auto.modes.ScaleAuton;
import frc.robot.auto.util.AutoMode;
import frc.robot.auto.util.AutoModeRunner;
import frc.robot.auto.util.GenerateTrajectory;
import frc.robot.controllers.PlasmaJoystick;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  PlasmaJoystick joystick;
  PlasmaJoystick joystick2;
  Drive driveTrain;
  Climb climb;

  GenerateTrajectory generateTraj;

  Compressor compressor; 

  AutoModeRunner autoModeRunner;
  AutoMode[] autoModes;
  int autoModeSelection;

  NetworkTable table;
  NetworkTableEntry tx;
  NetworkTableEntry ty;
  NetworkTableEntry ta;
  NetworkTableEntry tv;
  NetworkTableEntry ts;

  double[] zeroArray = new double[] { 0, 0, 0, 0, 0, 0, 0, 0 };

  boolean setDriveToCoast;

  int climbIterator;
  int climbPosition;
  boolean climbRecorded;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */

  @Override
  public void robotInit() {

    joystick = new PlasmaJoystick(Constants.JOYSTICK1_PORT);
    joystick2 = new PlasmaJoystick(Constants.JOYSTICK2_PORT);

    driveTrain = new Drive(Constants.L_DRIVE_ID, Constants.L_DRIVE_SLAVE_ID, Constants.R_DRIVE_ID, Constants.R_DRIVE_SLAVE_ID);

    climb = new Climb(Constants.LEFT_CLIMB_MOTOR_ID,
                      Constants.RIGHT_CLIMB_MOTOR_ID,
                      Constants.CLIMB_LATCH_ID);

    compressor = new Compressor();

    generateTraj = new GenerateTrajectory();

    driveTrain.resetEncoders();
    driveTrain.zeroGyro();
  
    table.getEntry("pipeline").setNumber(0);

    autoModeRunner = new AutoModeRunner();
    autoModes = new AutoMode[10];
    for(int i = 0; i < 10; i++){
      autoModes[i] = new Nothing();
    }

    autoModeSelection = 0;
    setDriveToCoast = false;

    climbIterator = 0;
    climbPosition = 0;
    climbRecorded = false;

    driveTrain.setToCoast();
  }

  

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {

    double[] xCorners = table.getEntry("tcornx").getDoubleArray(zeroArray);
    double[] yCorners = table.getEntry("tcorny").getDoubleArray(zeroArray);

    SmartDashboard.putNumberArray("x Corner Values ", xCorners);
    SmartDashboard.putNumberArray("y Corner Values ", yCorners);
    //DriverStation.reportWarning("exists? " + table.getEntry("tcornx").exists(), false);


    autoModeSelection = (int) SmartDashboard.getNumber("Auton Mode", 0.0);
    SmartDashboard.putNumber("Auton Mode", autoModeSelection);

    setDriveToCoast = SmartDashboard.getBoolean("Set Drive to Coast", false);
    SmartDashboard.putBoolean("Set Drive to Coast", setDriveToCoast);


  
    SmartDashboard.putNumber("drive Distance", driveTrain.getDistance());
    SmartDashboard.putNumber("left Distance", driveTrain.getLeftDistance());
    SmartDashboard.putNumber("right Distance", driveTrain.getRightDistance());


    SmartDashboard.putNumber("gyro angle", driveTrain.getGyroAngle());


    SmartDashboard.putNumber("climb encoder value", climb.getLeftEncoderValue());
    SmartDashboard.putNumber("climb position", climbPosition);

    driveTrain.updateOdometry();
  }

  public void disabledInit() {
    driveTrain.zeroGyro();
    climb.engageLatch();
    table.getEntry("ledMode").setNumber(1);
  }

  public void disabledPeriodic() {
    //table.getEntry("ledMode").setNumber(1);
    if(setDriveToCoast == true){
      driveTrain.setToCoast();
    }
    else {
      driveTrain.setToBrake();
    }
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */

  @Override
  public void autonomousInit() {
    DriverStation.reportWarning("starting auto", false);
    driveTrain.resetEncoders();
    driveTrain.zeroGyro();
    driveTrain.setToBrake();
    setDriveToCoast = false;

    autoModes[0] = new Nothing();
    autoModes[1] = new MoveFromLine(driveTrain, table);
    autoModes[4] = new ScaleAuton(driveTrain, table);

    table.getEntry("ledMode").setNumber(3);

    autoModeRunner.chooseAutoMode(autoModes[autoModeSelection]);
    autoModeRunner.start();
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
      driveTrain.getDistance();
      
  }

  @Override
  public void teleopInit() {
    autoModeRunner.stop();
    driveTrain.diffDrive.close();
    table.getEntry("ledMode").setNumber(1);
    driveTrain.setToBrake();
    driveTrain.setGyroAngle(0);
    setDriveToCoast = false;
  }
  
  @Override
  public void teleopPeriodic() {
    driverControls(joystick);
    //compressor.start();
  }

  public void driverControls(final PlasmaJoystick joystick) {
    driveTrain.FPSDrive(joystick.LeftY, joystick.RightX);

    if(joystick.dPad.getPOV() == 0)  {
      climb.releaseLatch();
    }

    if(joystick.dPad.getPOV() == 90) {
  
     if(climbRecorded == false){
        climbIterator += 1;
        climbRecorded = true;
      }

      switch(climbIterator){
        case 0:
          climbPosition = 0;
          break;
        case 1:
          climbPosition = 5400;
          break;
        case 2:
          climbPosition = 12410;
          break;
      }
      climb.setPosition(climbPosition);
    }
    else if(joystick.dPad.getPOV() == 180 && climb.getLeftEncoderValue() < 45000) {
      climb.spoolCable(Constants.MAX_SPOOL_SPEED);
    }
    else {
      climb.spoolCable(0);
      climbRecorded = false;
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    if(joystick.B.isPressed() && climb.getLeftEncoderValue() > 0){
      climb.spoolCable(-0.3);
    }
    else {
      climb.spoolCable(0);
    }
  }
}
