package frc.robot.auto.actions;

import frc.robot.auto.util.Action;
import frc.robot.Constants;
import frc.robot.Drive;
import frc.robot.Intake;
import edu.wpi.first.wpilibj.DriverStation;

public class pivotToAngle implements Action{
    Drive drive;
    boolean engaged;
    double angle;
    double angleDiff;
    double speed;

    public pivotToAngle(Drive driveTrain, double angle){
        this.drive = driveTrain;
        this.engaged = engaged;
        this.angle = angle;
    }

    @Override
    public boolean isFinished() {
        return (Math.abs(angleDiff) < 1 && Math.abs(speed) < 0.3);
    }

    @Override
    public void start() {
        angleDiff = 360;
    }

    @Override
    public void update(){
      angleDiff = drive.getGyroAngle() - angle;
      
      if (Math.abs(angleDiff) > 10){
        speed = (Math.abs(angleDiff) / 9.0) * 0.05 + 0.05;
      } else {
        speed = 0.20;
      }


      if (angleDiff > 0) {
        drive.autonTankDrive(-speed/2, speed/2);
      } else {
        drive.autonTankDrive(speed/2, -speed/2);
      }
    }

    public void end() {
        drive.stopDrive();
        drive.zeroGyro();
        drive.resetOdometry();
        DriverStation.reportWarning("done", false);
    }
}