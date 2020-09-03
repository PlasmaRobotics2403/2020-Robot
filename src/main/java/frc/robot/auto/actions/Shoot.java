package frc.robot.auto.actions;

import frc.robot.auto.util.Action;
import frc.robot.Constants;
import frc.robot.Intake;
import frc.robot.Shooter;
import frc.robot.Turret;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;

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

    double angle;

    boolean timeCollected;
    double startTime;

    public Shoot(Turret turret, Shooter shooter, Intake intake, NetworkTable table, double angle){
        this.turret = turret;
        this.shooter = shooter;
        this.intake = intake;
        this.table = table;
        distance = 0;
        vision_Area = 0;
        this.angle = angle;
        this.ballCount = intake.getAutonBallCount();
        this.timeCollected = false;

        startTime = 200.0;
    }

    @Override
    public boolean isFinished() {
        return startTime + 2.5 < Timer.getFPGATimestamp();
        
    }

    @Override
    public void start() {
        tx = table.getEntry("tx");
        ty = table.getEntry("ty");
        ta = table.getEntry("ta");
        table.getEntry("ledMode").setNumber(3);

        ballCounted = false;

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

        shooter.autoHood(vision_Y, 1);
        shooter.spinToRPM(15000);
        if(shooter.getLeftShooterRPM() > 14500) {
            if(timeCollected == false){
                startTime = Timer.getFPGATimestamp();
                timeCollected = true;
            }
            shooter.feedBalls(Constants.MAX_BALL_FEED_SPEED);
            intake.indexBall(Constants.MAX_INDEX_SPEED);
            intake.intakeBall(Constants.MAX_INTAKE_SPEED);
            
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