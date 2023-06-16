package frc.robot.Util.Swerve;

import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.ExternalLib.NorthwoodLib.NorthwoodDrivers.UnifiedFalcon500;

public class FalconModuleIO implements ModuleIO{

    private final UnifiedFalcon500 turnMotor;
    private final UnifiedFalcon500 driveMotor;
    private final CANCoder turnEncoder;
    private final boolean isTurnMotorInverted = true;
    private final Rotation2d absoluteEncoderOffset;


    public FalconModuleIO(){
        
    }


}
