package frc.robot.auto.modes;

import frc.robot.auto.actions.IntakeRoller;
import frc.robot.auto.actions.SetTurretPosition;
import frc.robot.auto.actions.Shoot;
import frc.robot.auto.actions.Straight;
import frc.robot.auto.util.AutoMode;
import frc.robot.auto.util.AutoModeEndedException;
import edu.wpi.first.networktables.NetworkTable;

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
		runActionsParallel(new Straight(0.2, 68, driveTrain, false, intake), new Shoot(turret, shooter, intake, table, 6500, false));
		////runAction(new SetTurretPosition(6000, turret));
		//runAction(new Shoot(turret, shooter, intake, table, 6500, false));
		runAction(new IntakeRoller(intake));
        runActionsParallel(new Straight(0.2, 175, driveTrain, true, intake), new Shoot(turret, shooter, intake, table, 6700, true));
        //runAction(new Shoot(turret, shooter, intake, table, 6700, true));
	}

}