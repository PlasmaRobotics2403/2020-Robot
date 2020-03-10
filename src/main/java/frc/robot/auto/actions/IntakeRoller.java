package frc.robot.auto.actions;

import frc.robot.auto.util.Action;
import frc.robot.Intake;
import edu.wpi.first.wpilibj.DriverStation;

public class IntakeRoller implements Action{
    Intake intake;

    public IntakeRoller(Intake intake){
        this.intake = intake;
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void start() {
        intake.extendForeBar();
    }

    @Override
    public void update(){
    }

    public void end() {
        DriverStation.reportWarning("done", false);
    }
}