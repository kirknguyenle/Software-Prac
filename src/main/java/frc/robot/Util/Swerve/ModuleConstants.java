package frc.robot.Util.Swerve;

import frc.ExternalLib.FusionLib.util.COTSFalconSwerveConstants;

public class ModuleConstants {
    private final COTSFalconSwerveConstants m_constants;
    private final int m_driveID;
    private final int m_turnID;
    private final int m_encoderID;
    private final double m_encoderOffset;

    public ModuleConstants(
        int driveID,
        int turnID,
        int encoderID,
        double encoderOffset,
        COTSFalconSwerveConstants constants
    ){
    m_driveID = driveID;
    m_turnID = turnID;
    m_encoderID = encoderID;
    m_encoderOffset = encoderOffset;
    m_constants = constants;


    }

    public int getDriveID(){
        return m_driveID;
    }
    public int getTurnID(){
        return m_turnID;
        
    }
}
