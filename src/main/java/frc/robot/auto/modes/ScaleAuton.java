package frc.robot.auto.modes;

import frc.robot.auto.actions.Straight;
import frc.robot.auto.actions.followTrajectory;
import frc.robot.auto.actions.gyroAngle;
import frc.robot.auto.actions.pivotToAngle;
import frc.robot.auto.util.AutoMode;
import frc.robot.auto.util.AutoModeEndedException;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Constants;

//import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.Drive;



/**
 *
 */
public class ScaleAuton extends AutoMode {
	Drive driveTrain;
	NetworkTable table;

    public ScaleAuton(Drive driveTrain, NetworkTable table) {
		this.driveTrain = driveTrain;
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
		
		runAction(new followTrajectory(4, driveTrain));
		runAction(new pivotToAngle(driveTrain, 180));
		runAction(new followTrajectory(7, driveTrain));

		DriverStation.reportWarning("Finished Action", false);
	}

}