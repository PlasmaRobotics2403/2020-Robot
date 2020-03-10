package frc.robot.auto.actions;

import frc.robot.auto.util.Action;
import frc.robot.Drive;
import frc.robot.Turret;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DriverStation;

public class SetTurretPosition implements Action{
    
    double position;
    Turret turret;

    public SetTurretPosition(double position, Turret turret){
        this.position = position;
        this.turret = turret;
    }

    @Override
    public boolean isFinished() {
        return turret.getTurretPosition() > position - 1300 && turret.getTurretPosition() < position + 1300; 
    }

    @Override
    public void start() {
        
    }

    @Override
    public void update(){
        turret.setTurretPosition(position);
    }

    public void end() {
        turret.turn(0);
    }
}