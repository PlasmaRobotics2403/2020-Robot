package frc.robot;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.util.Color;

public class Constants {
    /* front of robot has electronics */
	/* right & left sides from robot's perspective */

	/* CONTROLLER CONSTANTS */
	public static final int JOYSTICK1_PORT = 0;
	public static final int JOYSTICK2_PORT = 1;

	/* MOTOR ID CONSTANTS */
	public static final int R_DRIVE_ID = 1; // right side motor farthest from talons
	public static final int R_DRIVE_SLAVE_ID = 2; //
	public static final int L_DRIVE_ID = 3; // left side motor farthest from talons
	public static final int L_DRIVE_SLAVE_ID = 4; //
	public static final int TURRET_MOTOR_ID = 13;
	public static final int LEFT_CLIMB_MOTOR_ID = 14;
	public static final int RIGHT_CLIMB_MOTOR_ID = 15;
	
	/* PNUEMATIC CONSTANTS */
	public static final int INTAKE_SOLENOID_ID = 0;
	public static final int CLIMB_LATCH_ID = 1;

	/* DIO ID CONSTANTS */
	public static final int FRONT_INDEX_SENSOR_ID = 0;
	public static final int BACK_INDEX_SENSOR_ID = 1;
	public static final int MAX_LIMIT_SWITCH_ID = 2;
	public static final int MIN_LIMIT_SWITCH_ID = 3;

	/* DRIVETRAIN CONSTANTS */
	public static final double MAX_AUTO_DRIVE_SPEED = 0.9;
	public static final double MAX_DRIVE_SPEED = 1;
	public static final double MAX_DRIVE_TURN = 0.75;
	public static final double DRIVE_ENCODER_CONVERSION = 0.00003083; // ticks to meters //0.001581037;
	public static final double WHEEL_BASE = 0.65; //distance between left and right wheel in meters
	public static final int UNITS_PER_METER = 32848;

	/* CLIMB CONSTANTS */
	public static final double MAX_SPOOL_SPEED = .9;

	/* TALON CONFIG CONSTANTS */
	public static final int TALON_TIMEOUT = 30;

	/* AUTO CONSTRAINTS */
	public static final double kMaxSpeedMetersPerSecond = 0;
	public static final double kMaxAccelerationMetersPerSecondSquared = 0;
	public static final DifferentialDriveKinematics kDriveKinematics = null;
	

}