package frc.robot.auto.modes;

import frc.robot.auto.actions.IntakeRoller;
import frc.robot.auto.actions.SetTurretPosition;
import frc.robot.auto.actions.Shoot;
import frc.robot.auto.actions.SpinUp;
import frc.robot.auto.actions.Straight;
import frc.robot.auto.actions.followTrajectory;
import frc.robot.auto.actions.gyroAngle;
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
public class TrenchRun extends AutoMode {
	Drive driveTrain;
	Turret turret;
	Shooter shooter;
	Intake intake;
	NetworkTable table;

    public TrenchRun(Drive driveTrain, Turret turret, Shooter shooter, Intake intake, NetworkTable table) {
		this.driveTrain = driveTrain;
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
		runAction(new Shoot(turret, shooter, intake, table, 1, 15000));
		runAction(new IntakeRoller(intake, true));
		runAction(new SpinUp(shooter, 18000));
		runAction(new followTrajectory(1, driveTrain, intake));
		
		runAction(new followTrajectory(2, driveTrain, intake));
		runAction(new IntakeRoller(intake, false));
		runAction(new Shoot(turret, shooter, intake, table, 3, 18000));
		//
		
		DriverStation.reportWarning("Finished Action", false);
	}

}