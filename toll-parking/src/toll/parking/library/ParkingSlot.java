package toll.parking.library;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class ParkingSlot {
  
  private Type   type;
  private Instant assingedTime;
  private Instant unassignedTime;
  private Vehicle                assingedVehicle;
  private Pair<Integer, Integer> location;
  
  public ParkingSlot(Vehicle assingedVehicle) {
    this.assingedVehicle = assingedVehicle;
    this.type = assingedVehicle.getType();
    this.assingedTime = Instant.now();
  }
  
  public Type getType() {
    return this.type;
  }
  
  public Instant getInTime() {
    return assingedTime;
  }
  
  public void setInTime(Instant inTime) {
    this.assingedTime = inTime;
  }
  
  public Instant getOutTime() {
    return unassignedTime;
  }
  
  public void setOutTime(Instant outTime) {
    this.unassignedTime = outTime;
  }
  
  public void setType(Type type) {
    this.type = type;
  }
  
  public boolean assignSlot(Vehicle vehicle) {
    if(null != (assingedVehicle = vehicle)) {
      assingedTime = Instant.now();
      return true;
    }
    return false;
  }
  
  public void unAssignSlot() {
    assingedVehicle = null;
    unassignedTime = Instant.now();
  }
  
  public boolean isParkingAllowed(Vehicle vehicle) {
    return vehicle.getType() == type;
  }

  /**
   * Slot use's time ( time is measure in minutes)
   * 
   * @return
   */
  public long totalTimeSpent() {
    if(null == assingedTime || null == unassignedTime) {
      return -1;
    }
    
    return assingedTime.until(unassignedTime, ChronoUnit.MINUTES);
  }

  public Pair<Integer, Integer> getLocation() {
    return location;
  }

  public void setLocation(Pair<Integer, Integer> location) {
    this.location = location;
  }
  
  //no need to override hashCode as we are relaying on object reference.
}
