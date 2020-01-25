package frc.robot;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class ControlPanel {
    public ColorSensorV3 colorSensor;

    public ColorMatch colorMatcher;
    public ColorMatchResult matchedColor;
    public Color detectedColor;
    public String color;
    

    public ControlPanel (){

        colorSensor = new ColorSensorV3(I2C.Port.kOnboard);

        colorMatcher = new ColorMatch();

        colorMatcher.addColorMatch(Constants.BLUE_TARGET);
        colorMatcher.addColorMatch(Constants.GREEN_TARGET);
        colorMatcher.addColorMatch(Constants.RED_TARGET);
        colorMatcher.addColorMatch(Constants.YELLOW_TARGET);
    }

    public void detectColor() {
       
        detectedColor = colorSensor.getColor();

        matchedColor = colorMatcher.matchClosestColor(detectedColor);

        if(matchedColor.color == Constants.BLUE_TARGET) {
            color = "Blue";
        }
        else if(matchedColor.color == Constants.RED_TARGET) {
            color = "Red";
        }
        else if(matchedColor.color == Constants.GREEN_TARGET) {
            color = "Green";
        }
        else if(matchedColor.color == Constants.YELLOW_TARGET) {
            color = "Yellow";
        }
        else {
            color = "Unknown";
        }

        SmartDashboard.putNumber("Blue", colorSensor.getBlue());
        SmartDashboard.putNumber("Red", colorSensor.getRed());
        SmartDashboard.putNumber("Green", colorSensor.getGreen());
        SmartDashboard.putNumber("Confidence", matchedColor.confidence);
        SmartDashboard.putString("Detected Color", color);
        
    }
}