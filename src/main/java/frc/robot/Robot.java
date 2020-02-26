/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;

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
  Drive driveTrain;
  Shooter shooter;
  Intake intake;
  Turret turret;
  Climb climb;
  ControlPanel controlPanel;

  Compressor compressor; 

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

    driveTrain = new Drive(Constants.L_DRIVE_ID, Constants.L_DRIVE_SLAVE_ID, Constants.R_DRIVE_ID, Constants.R_DRIVE_SLAVE_ID);

    shooter = new Shooter(Constants.LEFT_FLY_WHEEL_MOTOR_ID,
                          Constants.RIGHT_FLY_WHEEL_MOTOR_ID,
                          Constants.HOOD_MOTOR_ID,
                          Constants.FRONT_ROLLER_MOTOR_ID);

    turret = new Turret(Constants.TURRET_MOTOR_ID);

    intake = new Intake(Constants.INTAKE_ID,
                        Constants.INDEXER_ID,
                        Constants.INTAKE_FORWARD_ID,
                        Constants.INTAKE_REVERSE_ID,
                        Constants.INDEX_SENSOR_ID);

    climb = new Climb(Constants.LEFT_CLIMB_MOTOR_ID,
                      Constants.RIGHT_CLIMB_MOTOR_ID,
                      Constants.CLIMB_LATCH_ID,
                      Constants.CLIMB_LATCH_PLUG_ID);

    controlPanel = new ControlPanel(Constants.SPIN_CONTROL_PANEL_MOTOR_ID);

    compressor = new Compressor();

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
    climb.engageLatch();
    intake.retractForeBar();
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
    //compressor.start();
  }

  public void driverControls(final PlasmaJoystick joystick) {
    driveTrain.FPSDrive(joystick.LeftY, joystick.RightX);
    visionTurretLineUp();

    if(joystick.RB.isPressed()) {
      intake.intakeBall(Constants.MAX_INTAKE_SPEED);
      if(intake.getIndexSensorState()) {
        // run index for set amount
      }
    }
    else {
      intake.intakeBall(0);
    }

    if(joystick.A.isPressed()) {
      intake.indexBall(Constants.MAX_INDEX_SPEED);
    }
    else{
      intake.indexBall(0);
      shooter.feedBalls(0);
    }

    if(joystick.R3.isPressed()) {
      intake.extendForeBar();
    }
    if(joystick.L3.isPressed()) {
      intake.retractForeBar();
      //climb.engageLatch();
    }

    if(joystick.Y.isPressed()) {
      controlPanel.spinControlPanel(Constants.MAX_CONTROL_PANEL_SPEED);
      //extend control panel
    }
    else{
      controlPanel.spinControlPanel(0);
      //retract control panel
    }

    if(joystick.RT.isPressed()){
      //extend hood
      shooter.shoot(Constants.MAX_SHOOTER_SPEED);
    }
    else {
      //retract hood
      shooter.stop();
    }
    if(joystick.X.isPressed()) {
      shooter.feedBalls(Constants.MAX_BALL_FEED_SPEED);
    }

    if(joystick.dPad.getPOV() == 0)  {
      climb.releaseLatch();
    }

    if(joystick.dPad.getPOV() == 180) {
      climb.spoolCable(Constants.MAX_SPOOL_SPEED);
    }
    else if(joystick.LT.isPressed()) {
      climb.spoolCable(-1);
    }
    else {
      climb.spoolCable(0);
    }
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
