package frc.robot.Util.Swerve;

import org.littletonrobotics.junction.AutoLog;

public interface ModuleIO {
    @AutoLog
    public static class ModuleData{
        public double drivePositionRad = 0.0;
        public double driveVelocityRadPerSec = 0.0;
        public double driveAppliedVolts = 0.0;
        public double[] driveCurrentAmps = new double[] {};
        public double[] driveTempCelcius = new double[] {};
    
        public double turnAbsolutePositionRad = 0.0;
        public double turnPositionRad = 0.0;
        public double turnVelocityRadPerSec = 0.0;
        public double turnAppliedVolts = 0.0;
        public double[] turnCurrentAmps = new double[] {};
        public double[] turnTempCelcius = new double[] {};
    }
    public default void updateInputs(ModuleData data) {}

    /** Run the drive motor at the specified voltage. */
    public default void setDriveVoltage(double volts) {}

    /** Run the turn motor at the specified voltage. */
    public default void setTurnVoltage(double volts) {}

    /** Enable or disable brake mode on the drive motor. */
    public default void setDriveBrakeMode(boolean enable) {}

    /** Enable or disable brake mode on the turn motor. */
    public default void setTurnBrakeMode(boolean enable) {}
}
