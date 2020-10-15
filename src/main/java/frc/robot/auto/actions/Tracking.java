package frc.robot.auto.actions;

import frc.robot.auto.util.Action;
import frc.robot.Constants;
import frc.robot.Intake;
import frc.robot.Turret;
import edu.wpi.first.wpilibj.DriverStation;

public class Tracking implements Action{
    Turret turret;
    boolean tracking;
    double angle;

    public Tracking(Turret turret, boolean tracking, double angle){
        this.turret = turret;
        this.tracking = tracking;
        this.angle = angle;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void start() {
        turret.setIsTracking(tracking);
        if(tracking == false){
            turret.setTurretPosition(angle);
        }
    }

    @Override
    public void update(){
    }

    public void end() {
        DriverStation.reportWarning("done", false);
    }
}