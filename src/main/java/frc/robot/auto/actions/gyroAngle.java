package frc.robot.auto.actions;

import frc.robot.auto.util.Action;
import frc.robot.Constants;
import frc.robot.Drive;
import edu.wpi.first.wpilibj.DriverStation;

public class gyroAngle implements Action{
    Drive drive;
    double angle;

    public gyroAngle(Drive drive, double angle){
        this.drive = drive;
        this.angle = angle;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void start() {
        drive.setGyroAngle(angle);
    }

    @Override
    public void update(){
    }

    public void end() {
        DriverStation.reportWarning("done", false);
    }
}