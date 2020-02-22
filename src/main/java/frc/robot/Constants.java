package frc.robot;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.util.Color;

public class Constants {
    /* front of robot has electronics */
	/* right & left sides from robot's perspective */

	/* CONTROLLER CONSTANTS */
	public static final int JOYSTICK1_PORT = 0;

	/* TALON ID CONSTANTS */
	public static final int R_DRIVE_ID = 1; // right side motor farthest from talons
	public static final int R_DRIVE_SLAVE_ID = 2; //
	public static final int L_DRIVE_ID = 3; // left side motor farthest from talons
	public static final int L_DRIVE_SLAVE_ID = 4; //
	public static final int INTAKE_ID = 5;
	public static final int INDEXER_ID = 6;
	public static final int SPIN_CONTROL_PANEL_MOTOR_ID = 7;
	public static final int LEFT_FLY_WHEEL_MOTOR_ID = 8;
	public static final int RIGHT_FLY_WHEEL_MOTOR_ID = 9;
	public static final int HOOD_MOTOR_ID = 10;
	public static final int FRONT_ROLLER_MOTOR_ID = 11;
	public static final int BACK_ROLLER_MOTOR_ID = 12;
	public static final int TURRET_MOTOR_ID = 13;
	public static final int LEFT_CLIMB_MOTOR_ID = 14;
	public static final int RIGHT_CLIMB_MOTOR_ID = 15;
	

	/* PNUEMATIC CONSTANTS */
	public static final int INTAKE_FORWARD_ID = 0;
	public static final int INTAKE_REVERSE_ID = 1;
	public static final int CLIMB_LATCH_ID = 2;


	/* DRIVETRAIN CONSTANTS */
	public static final double MAX_AUTO_DRIVE_SPEED = 0.9;
	public static final double MAX_DRIVE_SPEED = 1;
	public static final double MAX_DRIVE_TURN = 1;
	public static final double DRIVE_ENCODER_CONVERSION = 0.004665594;

	/* SHOOTER CONSTANTS */
	public static final double MAX_SHOOTER_SPEED = 1;
	public static final double MAX_BALL_FEED_SPEED = 0.9;

	/* INTAKE CONSTANTS */
	public static final double MAX_INDEX_SPEED = 0.9;
	public static final double MAX_INTAKE_SPEED = 0.9;

	/* TURRET CONSTANTS */
	public static final double MAX_TURRET_SPEED = 1; 

	/* CLIMB CONSTANTS */
	public static final double MAX_SPOOL_SPEED = .7;

	/* CONTROL PANEL CONSTANTS */
	public final static Color BLUE_TARGET = ColorMatch.makeColor(0.143, 0.427, 0.429);
	public final static Color GREEN_TARGET = ColorMatch.makeColor(0.197, 0.561, 0.240);
	public final static Color RED_TARGET = ColorMatch.makeColor(0.561, 0.232, 0.114);
	public final static Color YELLOW_TARGET = ColorMatch.makeColor(0.361, 0.524, 0.113);

	public static final double MAX_CONTROL_PANEL_SPEED = 1;

	/* VISION CONSTANTS */
	public static final double CAMERA_HEIGHT = 25.625; //inches
	public static final double CAMERA_ANGLE = -2.7; //degrees
	public static final double OUTERPORT_HEIGHT = 32; // 89 inches

	/* TALON CONFIG CONSTANTS */
	public static final int TALON_TIMEOUT = 30;

	
	

}