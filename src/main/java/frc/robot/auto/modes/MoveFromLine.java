package frc.robot.auto.modes;

import frc.robot.auto.actions.Straight;
import frc.robot.auto.util.AutoMode;
import frc.robot.auto.util.AutoModeEndedException;

//import edu.wpi.first.wpilibj.DriverStation;

import frc.robot.Drive;



/**
 *
 */
public class MoveFromLine extends AutoMode {
    Drive driveTrain;

    public MoveFromLine(Drive drivetrain) {
        driveTrain = drivetrain;
    }
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.usfirst.frc.team2403.robot.auto.util.AutoMode#routine()
	 */
	@Override
	protected void routine() throws AutoModeEndedException {
		runAction(new Straight(1.0, driveTrain));
	}

}