package frc.robot.auto.actions;

import frc.robot.auto.util.Action;
import frc.robot.Drive;
import frc.robot.Turret;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;

public class SetTurretPosition implements Action{
    
    double angle;
    Turret turret;
    double startingPosition;

    public SetTurretPosition(double angle, Turret turret){
        this.angle = angle;
        this.turret = turret;
    }

    @Override
    public boolean isFinished() {
        return (turret.getTurretAngle() > angle - 0.5 && turret.getTurretAngle() < angle + 0.5 && Math.abs(turret.getVelocity()) < 1); 
    }

    @Override
    public void start() {
        startingPosition = turret.getTurretPosition();
    }

    @Override
    public void update(){
        turret.setTurretPosition(angle);
    }

    public void end() {
        turret.turn(0);
    }
}