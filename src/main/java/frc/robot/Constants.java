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
	
	public static final int SHOOTER_MOTOR_A_ID = 9;
	public static final int SHOOTER_MOTOR_B_ID = 8; 
	
	public static final int TURRET_MOTOR_ID = 3;
	
	
	/*public static final int R_ELEVATOR_ID = 7;
	public static final int L_ELEVATOR_ID = 10;
	public static final int PIVOT_ID = 13; // motor id that pivots the intake
	public static final int INTAKE_ID = 9; // motor id that intakes cargo
	public static final int L_HAB_ELEVATOR_ID = 11;
	public static final int R_HAB_ELEVATOR_ID = 8;
	public static final int HAB_DRIVE = 12;
	public static final int R_HAB_ARM_ID = 14;
	public static final int L_HAB_ARM_ID = 15;*/

	/* VICTORSPX ID CONSTANTS */
	/*public static final int R_DRIVE_MID_SLAVE_ID = 2; // right side motor in the middle
	public static final int R_DRIVE_FRONT_SLAVE_ID = 3; // right side motor closest to talons
	public static final int L_DRIVE_MID_SLAVE_ID = 5; // left side motor in the middle
	public static final int L_DRIVE_FRONT_SLAVE_ID = 6; // left side motor closest to talons*/


	/* DRIVETRAIN CONSTANTS */
	public static final double MAX_AUTO_DRIVE_SPEED = 0.9;
	public static final double MAX_DRIVE_SPEED = 1;
	public static final double MAX_DRIVE_TURN = 1;
	public static final double DRIVE_ENCODER_CONVERSION = 0.004665594;

	/* SHOOTER CONSTANTS */
	public static final double MAX_SHOOTER_SPEED = 1;

	/* TURRET CONSTANTS */
	public static final double MAX_TURRET_SPEED = 1; 

	/* CONTROL PANEL CONSTANTS */
	public final static Color BLUE_TARGET = ColorMatch.makeColor(0.143, 0.427, 0.429);
	public final static Color GREEN_TARGET = ColorMatch.makeColor(0.197, 0.561, 0.240);
	public final static Color RED_TARGET = ColorMatch.makeColor(0.561, 0.232, 0.114);
	public final static Color YELLOW_TARGET = ColorMatch.makeColor(0.361, 0.524, 0.113);

	/* VISION CONSTANTS */
	public static final double CAMERA_HEIGHT = 25.625; //inches
	public static final double CAMERA_ANGLE = -2.7; //degrees
	public static final double OUTERPORT_HEIGHT = 32; // 89 inches

	/* TALON CONFIG CONSTANTS */
	public static final int TALON_TIMEOUT = 30;

	
	

}