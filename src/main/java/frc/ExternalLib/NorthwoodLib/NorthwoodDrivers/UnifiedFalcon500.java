package frc.ExternalLib.NorthwoodLib.NorthwoodDrivers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;

import edu.wpi.first.math.util.Units;

public class UnifiedFalcon500 implements UnifiedMotor{
    private static final double TICKS_PER_REV = 2048;
    private final int motorID;
    private final TalonFX motor;
    private final TalonFXConfiguration config;
    private int supplyCurrentLimit;
    
    public UnifiedFalcon500(int motorID){
        this.motorID = motorID;
        this.motor = new TalonFX(this.motorID);
        this.config = new TalonFXConfiguration();
        this.config.voltageCompSaturation = 12.0;
        this.config.statorCurrLimit.enable = true;
        this.config.statorCurrLimit.currentLimit = 40;
        this.config.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;
        motor.configFeedbackNotContinuous(true, motorID);
        motor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        motor.enableVoltageCompensation(true);
        motor.setSensorPhase(true);
        motor.setNeutralMode(NeutralMode.Brake);
        motor.setInverted(false);
        motor.configAllSettings(this.config);
    }

    public UnifiedFalcon500(int motorID, boolean motorinvert, int statorCurrLimit){
      this.motorID = motorID;
      this.motor = new TalonFX(this.motorID);
      this.config = new TalonFXConfiguration();
      this.config.voltageCompSaturation = 12.0;
      this.config.statorCurrLimit.enable = true;
      this.config.statorCurrLimit.currentLimit = statorCurrLimit;
      this.config.primaryPID.selectedFeedbackSensor = FeedbackDevice.IntegratedSensor;
      motor.setInverted(motorinvert);
      motor.configAllSettings(this.config);
  }
   
  @Override 
  public void setVelocity(double velocityRadPerSec, double ffVolts, int slotID){
    motor.selectProfileSlot(slotID, 0);
    double velocityFalconUnits = Units.radiansToRotations(velocityRadPerSec)
         * TICKS_PER_REV / 10.0;
    motor.set(ControlMode.Velocity, velocityFalconUnits,
        DemandType.ArbitraryFeedForward, ffVolts / 12.0);
        
  }

  public void configCurrentLimit(int supplyCurrLimit){
    this.supplyCurrentLimit = supplyCurrLimit;
    this.config.supplyCurrLimit.currentLimit = supplyCurrLimit;
    this.config.supplyCurrLimit.enable = true;
  }
  public void setCurrentLimit(boolean enabled){
    SupplyCurrentLimitConfiguration config = new SupplyCurrentLimitConfiguration();
        config.currentLimit = this.supplyCurrentLimit;
        config.enable = enabled;

    motor.configSupplyCurrentLimit(config);
  }
  @Override
  public void stop() {
    motor.set(ControlMode.PercentOutput, 0.0);
  }
  @Override
  public void setPosition(double positionRad, int slotID){
    motor.selectProfileSlot(slotID, 0);
    double positionFalconUnits = Units.radiansToRotations(positionRad)*TICKS_PER_REV;
    motor.set(ControlMode.Position, positionFalconUnits);
  }
  
  /*
   * Sets Falcon Motor Position in radians 
   * This use the internal motion profile follower and generator. 
   */
  public void setMotionMagicPosition(double positionRad, double ff, int slotID){
    motor.selectProfileSlot(slotID, 0);
    double positionFalconUnits = Units.radiansToRotations(positionRad)*TICKS_PER_REV;
    motor.set(ControlMode.MotionMagic, positionFalconUnits);
  }
  @Override
  public void setEncoder(double positionRad){
    motor.setSelectedSensorPosition(Units.radiansToRotations(positionRad)*TICKS_PER_REV, 0, 250);
    
  }


  @Override
  public void configurePID(double kP, double kI, double kD, double ff, int slotID) {
    this.config.slot0.kP = kP;
    this.config.slot0.kI = kI;
    this.config.slot0.kD = kD;
    this.config.slot0.kF = ff;
    motor.configAllSettings(config);
  }
  public void configureMotionMagic(double maxVelocity, double maxAcceleration, int curveStrength){
    this.config.motionCurveStrength = curveStrength;
    this.config.motionCruiseVelocity = maxVelocity;
    this.config.motionAcceleration = maxAcceleration;
    motor.configAllSettings(config);
    

  }

  @Override
  public double getPosition(){
    return Units.rotationsToRadians(motor.getSelectedSensorPosition() / TICKS_PER_REV);
  }
  @Override
  public double getVelocity(){
    return Units.rotationsPerMinuteToRadiansPerSecond(motor.getSelectedSensorVelocity() * 10 / TICKS_PER_REV);
  }

  @Override 
  public double[] getStatorAmps(){
    return new double[] { motor.getSupplyCurrent()};
  }

  @Override
  public double[] getStatorTempCelcius(){
    return new double[]{motor.getTemperature()};
  }

  @Override 
  public double getAppliedVolts(){
    return motor.getMotorOutputVoltage();
  }
    
}
