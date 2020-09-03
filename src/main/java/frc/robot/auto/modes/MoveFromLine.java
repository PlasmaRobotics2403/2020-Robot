package frc.robot.auto.modes;

import frc.robot.auto.actions.IntakeRoller;
import frc.robot.auto.actions.SetTurretPosition;
import frc.robot.auto.actions.Shoot;
import frc.robot.auto.actions.Straight;
import frc.robot.auto.actions.followTrajectory;
import frc.robot.auto.util.AutoMode;
import frc.robot.auto.util.AutoModeEndedException;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Constants;

//import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.Drive;
import frc.robot.Intake;
import frc.robot.Shooter;
import frc.robot.Turret;



/**
 *
 */
public class MoveFromLine extends AutoMode {
	Drive driveTrain;
	Turret turret;
	Shooter shooter;
	Intake intake;
	NetworkTable table;

    public MoveFromLine(Drive drivetrain, Turret turret, Shooter shooter, Intake intake, NetworkTable table) {
		this.driveTrain = drivetrain;
		this.turret = turret;
		this.shooter = shooter;
		this.intake = intake;
		this.table = table;
    }
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.usfirst.frc.team2403.robot.auto.util.AutoMode#routine()
	 */
	@Override
	protected void routine() throws AutoModeEndedException {
		DriverStation.reportWarning("started Action", false);
		runAction(new IntakeRoller(intake, true));
		runAction(new followTrajectory("fiveFeetForward", driveTrain, intake));
		runAction(new SetTurretPosition(Constants.BACK_FACING - 30, turret));
		runAction(new Shoot(turret, shooter, intake, table, Constants.BACK_FACING - 30));
		//runAction(new Straight(0.2, 24, driveTrain, false, intake));
		//
		
		DriverStation.reportWarning("Finished Action", false);
	}

}