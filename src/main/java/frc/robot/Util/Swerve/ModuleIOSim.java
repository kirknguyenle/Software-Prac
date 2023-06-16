package frc.robot.Util.Swerve;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import frc.ExternalLib.FusionLib.util.COTSFalconSwerveConstants;
import frc.robot.Constants;

public class ModuleIOSim implements ModuleIO {
  

    private FlywheelSim driveSim;
    private FlywheelSim turnSim;
    private double turnRelativePositionRad = 0.0;
    private double turnAbsolutePositionRad = Math.random() * 2.0 * Math.PI;
    private double driveAppliedVolts = 0.0;
    private double turnAppliedVolts = 0.0;

    public ModuleIOSim(COTSFalconSwerveConstants constants) {
        driveSim = new FlywheelSim(DCMotor.getNEO(1), constants.driveGearRatio, 0.025);
        turnSim = new FlywheelSim(DCMotor.getNEO(1), constants.angleGearRatio, 0.004);
        System.out.println("[Init] Creating ModuleIOSim");
      }

      public void updateInputs(ModuleData inputs) {
        driveSim.update(Constants.loopPeriodSeconds);
        turnSim.update(Constants.loopPeriodSeconds);
    
        double angleDiffRad = turnSim.getAngularVelocityRadPerSec() * Constants.loopPeriodSeconds;
        turnRelativePositionRad += angleDiffRad;
        turnAbsolutePositionRad += angleDiffRad;
        while (turnAbsolutePositionRad < 0) {
          turnAbsolutePositionRad += 2.0 * Math.PI;
        }
        while (turnAbsolutePositionRad > 2.0 * Math.PI) {
          turnAbsolutePositionRad -= 2.0 * Math.PI;
        }
    
        inputs.drivePositionRad =
            inputs.drivePositionRad
                + (driveSim.getAngularVelocityRadPerSec() * Constants.loopPeriodSeconds);
        inputs.driveVelocityRadPerSec = driveSim.getAngularVelocityRadPerSec();
        inputs.driveAppliedVolts = driveAppliedVolts;
        inputs.driveCurrentAmps = new double[] {Math.abs(driveSim.getCurrentDrawAmps())};
        inputs.driveTempCelcius = new double[] {};
    
        inputs.turnAbsolutePositionRad = turnAbsolutePositionRad;
        inputs.turnPositionRad = turnRelativePositionRad;
        inputs.turnVelocityRadPerSec = turnSim.getAngularVelocityRadPerSec();
        inputs.turnAppliedVolts = turnAppliedVolts;
        inputs.turnCurrentAmps = new double[] {Math.abs(turnSim.getCurrentDrawAmps())};
        inputs.turnTempCelcius = new double[] {};
      }
    
      public void setDriveVoltage(double volts) {
        driveAppliedVolts = MathUtil.clamp(volts, -12.0, 12.0);
        driveSim.setInputVoltage(driveAppliedVolts);
      }
    
      public void setTurnVoltage(double volts) {
        turnAppliedVolts = MathUtil.clamp(volts, -12.0, 12.0);
        turnSim.setInputVoltage(turnAppliedVolts);
      }

}
