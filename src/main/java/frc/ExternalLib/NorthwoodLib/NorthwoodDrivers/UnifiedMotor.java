package frc.ExternalLib.NorthwoodLib.NorthwoodDrivers;

public interface UnifiedMotor {
      /** Run closed loop at the specified velocity. */
      public default void setVelocity(double velocityRadPerSec, double ffVolts,int slotID) {
    }
    /*Move to position using internal PIDF controller */
    public default void setPosition(double positionRad, int slotID){   
    }
    /* Set internal Encoder Value */
    public default void setEncoder(double positionRad){
        
    }
  
    /** Stop in open loop. */
    public default void stop() {
    }
  
    /** Set velocity PID constants. */
    public default void configurePID(double kP, double kI, double kD, double ff, int slotID) {
    }
    
    /** Returns Current Motor Velocity in Radians per second */
    public double getVelocity();
    /** Returns Current Motor Positon in Radians */
    public double getPosition();

    /* Retuns the motor's current draw in Amps */
    public double[] getStatorAmps();

    /* Returns the motor's temp in Celcius */
    public double[] getStatorTempCelcius();

    /*Returns the Applied Volts of the Motor */
    public double getAppliedVolts();

    
}
