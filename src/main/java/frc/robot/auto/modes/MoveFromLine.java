package frc.robot.auto.modes;

import frc.robot.auto.actions.Straight;
import frc.robot.auto.actions.followTrajectory;
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
public class MoveFromLine extends AutoMode {
	Drive driveTrain;
	NetworkTable table;

    public MoveFromLine(Drive drivetrain, NetworkTable table) {
		this.driveTrain = drivetrain;

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
		runAction(new followTrajectory(0, driveTrain));
		runAction(new followTrajectory(3, driveTrain));
		//runAction(new Straight(0.2, 24, driveTrain, false, intake));
		
		DriverStation.reportWarning("Finished Action", false);
	}

}