package frc.robot.auto.actions;

import frc.robot.auto.util.Action;
import frc.robot.Constants;
import frc.robot.Intake;
import frc.robot.Shooter;
import frc.robot.Turret;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;

public class Shoot implements Action{
    Turret turret;
    Shooter shooter;
    Intake intake;
    NetworkTable table;
    double distance;

    NetworkTableEntry tx;
    NetworkTableEntry ty;
    NetworkTableEntry ta;

    double vision_X;
    double vision_Y;
    double vision_Area;

    int ballCount;
    boolean ballCounted;
    boolean neverEnd;

    double position;

    public Shoot(Turret turret, Shooter shooter, Intake intake, NetworkTable table, double position, boolean neverEnd){
        this.turret = turret;
        this.shooter = shooter;
        this.intake = intake;
        this.table = table;
        distance = 0;
        vision_Area = 0;
        this.position = position;
        this.neverEnd = neverEnd;
    }

    @Override
    public boolean isFinished() {
        if(neverEnd) {
            return false;
        }
        else{
            return ballCount == 0;
        }
        
    }

    @Override
    public void start() {
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        table.getEntry("ledMode").setNumber(3);

        ballCount = 5;
        ballCounted = false;

        while(turret.getTurretPosition() < position - 1300 || turret.getTurretPosition() > position + 1300){
            turret.setTurretPosition(position);
        }
    }

    @Override
    public void update(){
        vision_X = tx.getDouble(0.0);
        vision_Y = ty.getDouble(0.0);
        vision_Area = ta.getDouble(0.0);

        if (vision_Area == 0) {
            turret.turn(0);
        }
        else {
            double turnVal = vision_X / 20;
            turnVal = Math.min(turnVal, 0.2);
            turnVal = Math.max(-0.2, turnVal);
            turret.turn(turnVal);
        }

        distance = Math.pow(Math.E, -Math.log(vision_Area/1539.1)/2.081);

        shooter.autoHood(distance);
        shooter.spinToRPM(15000);
        if(shooter.getLeftShooterRPM() > 14500 && shooter.getHoodPosition() > shooter.getTargetAngle() - shooter.getErrorRange() && shooter.getHoodPosition() < shooter.getTargetAngle() + shooter.getErrorRange()) {
            shooter.feedBalls(Constants.MAX_BALL_FEED_SPEED);
            intake.indexBall(Constants.MAX_INDEX_SPEED);
            intake.intakeBall(Constants.MAX_INTAKE_SPEED);
            
            if(intake.getBackIndexSensorState() && ballCounted == false){
                ballCount --;
                ballCounted = true;
            }
            else{
                ballCounted = false;
            }
        }
        else {
            shooter.feedBalls(0);
            intake.indexBall(0);
            intake.intakeBall(0);
        }
    }

    public void end() {
        shooter.hoodHidden();
        shooter.stop();
        shooter.feedBalls(0);
        intake.intakeBall(0);
        intake.indexBall(0);
        turret.turn(0);
        table.getEntry("ledMode").setNumber(1);
    }
}