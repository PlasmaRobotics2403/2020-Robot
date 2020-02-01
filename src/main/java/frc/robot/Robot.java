/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.controllers.PlasmaJoystick;

import java.util.concurrent.TimeUnit;

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
  Drive driveTrain;
  Shooter shooter;
  Intake intake;
  Turret turret;
  ControlPanel controlPanel;

  NetworkTable table;
  NetworkTableEntry tx;
  NetworkTableEntry ty;
  NetworkTableEntry ta;

  CameraServer server;

  double vision_X;
  double vision_Y;
  double vision_Area;

  double distance;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {

    joystick = new PlasmaJoystick(Constants.JOYSTICK1_PORT);

    driveTrain = new Drive(Constants.L_DRIVE_ID, Constants.L_DRIVE_SLAVE_ID, Constants.R_DRIVE_ID,
        Constants.R_DRIVE_SLAVE_ID);
    shooter = new Shooter(Constants.SHOOTER_MOTOR_A_ID,
                          Constants.SHOOTER_MOTOR_B_ID);
    turret = new Turret(Constants.TURRET_MOTOR_ID);
    controlPanel = new ControlPanel();

    driveTrain.resetEncoders();
    driveTrain.zeroGyro();

    table = NetworkTableInstance.getDefault().getTable("limelight");
    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");

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
    vision_X = tx.getDouble(0.0);
    vision_Y = ty.getDouble(0.0);
    vision_Area = ta.getDouble(0.0);

    SmartDashboard.putNumber("LimelightX", vision_X);
    SmartDashboard.putNumber("LimelightY", vision_Y);
    SmartDashboard.putNumber("LimelightArea", vision_Area);

    if(table.getEntry("getpipe").getDouble(0) == 2) {
      SmartDashboard.putString("Camera Mode", "driver camera");
    }
    else if(table.getEntry("getpipe").getDouble(0) == 1) {
      SmartDashboard.putString("Camera Mode", "x2 Zoom");
    }
    else {
      SmartDashboard.putString("Camera Mode", "x1 Zoom");
    }

    // x2 hardware zoom camera feed
    if(table.getEntry("getpipe").getDouble(0) == 1) {
      distance = (Constants.OUTERPORT_HEIGHT - Constants.CAMERA_HEIGHT) / Math.tan(Math.toRadians(vision_Y) + Math.toRadians(Constants.CAMERA_ANGLE));
      distance /= 12; // convert from inches to feet
    }
    else {
      distance = (Constants.OUTERPORT_HEIGHT - Constants.CAMERA_HEIGHT) / Math.tan(Math.toRadians(vision_Y) + Math.toRadians(Constants.CAMERA_ANGLE));
      distance /= 12; // convert from inches to feet 
    }
    
    SmartDashboard.putNumber("Distance", distance);
  }

  public void disabledInit() {
    driveTrain.zeroGyro();
  }

  public void disabledPeriodic() {

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
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {

  }

  @Override
  public void teleopPeriodic() {
    driverControls(joystick);
  }

  public void driverControls(final PlasmaJoystick joystick) {
    driveTrain.FPSDrive(joystick.LeftY, joystick.RightX);
    if (joystick.LB.isPressed()) {
      shooter.shoot();
    } else {
      shooter.stop();
    }

    if (joystick.RB.isPressed()){
      shooter.extendHood();
    }
    if (joystick.X.isPressed()){
      shooter.retractHood();
    }

    if (joystick.A.isPressed()) {
      turret.turn(1);
    } else if (joystick.B.isPressed()) {
      turret.turn(-1);
    } else {
      turret.turn(0);
    }
 
    if(joystick.Y.isPressed()) { 
      visionTurretLineUp(); 
    }
      
    /*if(joystick.X.isPressed()) { 
      controlPanel.detectColor(); 
    }*/
  }

  public void visionLineUp() {

    double turnVal = vision_X / 45;
    turnVal = Math.min(turnVal, 0.3);
    turnVal = Math.max(-0.3, turnVal);

    double forwardVal = (1.2 - vision_Area) / 3;
    forwardVal = Math.min(forwardVal, 0.3);
    forwardVal = Math.max(-0.3, forwardVal);
    forwardVal *= -1;

    //driveTrain.FPSDrive(forwardVal, turnVal);
  }

  public void visionTurretLineUp() {
    if (vision_Area == 0) {
      turret.turn(0);
    }
    else {
      double turnVal = vision_X / 10;
      turnVal = Math.min(turnVal, 1);
      turnVal = Math.max(-1, turnVal);
      turret.turn(-turnVal);
    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
