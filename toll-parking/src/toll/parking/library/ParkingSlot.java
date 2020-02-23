package toll.parking.library;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Class Represent Parking linked to {@link toll.parking.library.Vehicle Vehicle}
 * 
 * @author ashokv
 *
 */
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
  
  /**
   * Return {@link toll.parking.library.Type Type} of Parking Slot.
   * 
   * @return Type
   */
  public Type getType() {
    return this.type;
  }
  
  /**
   * Set {@link toll.parking.library.Type Type} of Parking Slot.
   * 
   * @param type
   */
  public void setType(Type type) {
    this.type = type;
  }
  
  /**
   * Return time when vehicle assigned to ParkingSlot.
   * 
   * @return assignedTime - time unit as Instance.
   */
  public Instant getAssingedTime() {
    return assingedTime;
  }
  
  /**
   * Set time when the Vehicle assigned to ParkingSlot.
   * 
   * @param assignedTime
   *          - time unit as Instance.
   */
  public void setAssingedTime(Instant assingedTime) {
    this.assingedTime = assingedTime;
  }
  
  /**
   * Return time when vehicle Unassigned from ParkingSlot.
   * 
   * @return unassignedTime - time unit as Instance.
   */
  public Instant getUnassignedTime() {
    return unassignedTime;
  }
  
  /**
   * Set time when the Vehicle unassignedTime from ParkingSlot.
   * 
   * @param unassignedTime
   *          - time unit as Instance.
   */
  public void setUnassignedTime(Instant outTime) {
    this.unassignedTime = outTime;
  }
  
  /**
   * Assign parking slot to vehicle and set the assigned time.
   * 
   * @param vehicle
   *          - Vehicle
   * @return boolean - true successful slot assignment else return false.
   */
  public boolean assignSlot(Vehicle vehicle) {
    if(null != (assingedVehicle = vehicle)) {
      assingedTime = Instant.now();
      return true;
    }
    return false;
  }
  
  /**
   * Remove assigned of vehicle and set unassigned time.
   */
  public void unAssignSlot() {
    assingedVehicle = null;
    unassignedTime = Instant.now();
  }
  
  /**
   * Validate if parking slot as compatibility for vehicle.
   * 
   * @param vehicle
   *          - Vehicle
   * @return boolean - true if slot is compatible with vehicle else false.
   */
  public boolean isParkingAllowed(Vehicle vehicle) {
    return vehicle.getType() == type;
  }

  /**
   * Return hom much time parking slot used by the vehicle ( time is measured in minutes).
   * 
   * @return long - time in minutes.
   */
  public long totalTimeSpent() {
    if(null == assingedTime || null == unassignedTime) {
      return -1;
    }
    
    return assingedTime.until(unassignedTime, ChronoUnit.MINUTES);
  }

  /**
   * Get Parking Slot location ( Floor index and position over Floor)
   * 
   * @return Pair - Pair of Floor and Location on Floor.
   */
  public Pair<Integer, Integer> getLocation() {
    return location;
  }

  /**
   * Set Parking Slot location ( Floor index and position over Floor)
   * 
   * @param location
   *          - Pair of Floor and Location on Floor.
   */
  public void setLocation(Pair<Integer, Integer> location) {
    this.location = location;
  }
  
  /**
   * Return vehicle assigned to parking slot.
   * 
   * @return Vehicle
   */
  public Vehicle getAssingedVehicle() {
    return assingedVehicle;
  }
  
  @Override
  public int hashCode() {
    //no need to override hashCode as we are relaying on object references to identify the uniqueness.
    return super.hashCode();
  }
}
