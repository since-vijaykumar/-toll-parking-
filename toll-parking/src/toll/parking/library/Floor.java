package toll.parking.library;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Floor, this class is representation of area where parking information are stored.
 * 
 * @author ashokv
 *
 */
public class Floor {
  
  private int                       flrNum = -1;
  private int                       capacity = -1;
  private BitSet                    floorPositions;
  private Map<Vehicle, ParkingSlot> reservedSlotMap;
  private Type                      type;
  
  public Floor(int flrNum, int capacity, Type type) {
    
    this.flrNum = flrNum;
    this.capacity = capacity;
    this.type = type;
    floorPositions = new BitSet();
    reservedSlotMap = new HashMap<Vehicle, ParkingSlot>();
  }
  
  /**
   * Assign slot to vehicle. see {@link toll.parking.library.ParkingSlot ParkingSlot}.
   * 
   * 
   * @param vehicle
   *          -Vehicle need to assign to this slot.
   * @return boolean - true if slot is assinged else false.
   */
  public boolean assignSlot(Vehicle vehicle) {
    if(vehicle.getType() != type) {
      throw new RuntimeException("Unsupported Vehicle type : " + vehicle.getType().getTypeName() + "for floor type: "
                                 + type.getTypeName());
    }
    //index available to use
    int spaceAvaialbeOnFlr = getFreeSlotIndex();
    // not free space, all are occupied
    if(spaceAvaialbeOnFlr < 0) {
      return false;
    }
    
    //mark floor index as used
    floorPositions.set(spaceAvaialbeOnFlr);
    //assign slot to vehicle
    ParkingSlot parkingSlot = new ParkingSlot(vehicle);
    parkingSlot.setLocation(new Pair<>(flrNum, spaceAvaialbeOnFlr));
    reservedSlotMap.put(vehicle, parkingSlot);
    //      avaialbeSpaces.set(spaceAvaialbeOnFlr, vehicle);
    return true;
  }
  
  /**
   * Unassign and return slot used by vehicle. see {@link toll.parking.library.ParkingSlot ParkingSlot}
   * 
   * @param vehicle
   *          - Vehicle need to unassign from this slot.
   * @return parkingSlot- ParkingSlot or null is nothing is assigned to vehicle.
   */
  public ParkingSlot unassignSlot(Vehicle vehicle) {
    //remove existing slot for vehicle
    ParkingSlot parkingSlot = reservedSlotMap.remove(vehicle);
    
    if(null == parkingSlot) {
      return null;
    }
    
    //stop the timer and unassigned car from slot.
    parkingSlot.unAssignSlot();
    Pair<Integer, Integer> location = parkingSlot.getLocation();
    //mark floor index as free
    floorPositions.clear(location.getSecond());
    return parkingSlot;
  }
  
  /**
   * Return next available {@link toll.parking.library.ParkingSlot ParkingSlot} index.
   * 
   * @return index- positive integer or -1 if floor is full.
   */
  public int getFreeSlotIndex() {
    
    int nextClearBit = floorPositions.nextClearBit(0);
    //limit the index's to capacity
    if(nextClearBit >= capacity) {
      return -1;
    }
    return nextClearBit;
  }
  
  private int getBitSetLength(BitSet bitSet) {
    return new Long(bitSet.stream().count()).intValue();
  }
  
  /**
   * Return floor capacity.
   * 
   * @return integer values
   */
  public int getCapacity() {
    return capacity;
  }
  
  /**
   * Set Floor capacity.
   * 
   * @param capacity
   *          - positive integer.
   */
  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }
  
  /**
   * Get All used Floor position, position start from 0.
   * 
   * @return BitSet - Representing floor position, empty if no position on floor is used.
   */
  public BitSet getFloorPosition() {
    return floorPositions;
  }
  
  /**
   * Set Available position at Floor.
   * 
   * @param floorPositions-
   *          BitSet representing avaialbe position at this floor.
   */
  public void setFloorPosition(BitSet floorPositions) {
    this.floorPositions = floorPositions;
  }
  
  /**
   * Return current Floor number.
   * 
   * @return Integer values to represent floor number, start from 0.
   */
  public int getFloorNumber() {
    return flrNum;
  }
  
  /**
   * Set Floor number, start from 0
   * 
   * @param flrNum
   *          - integer values
   */
  public void setFloorNumber(int flrNum) {
    this.flrNum = flrNum;
  }
  
  /**
   * Return {@link toll.parking.library.Type Type} of Floor
   * 
   * @return- Floor Type
   */
  public Type getType() {
    return type;
  }

  /**
   * Set {@link toll.parking.library.Type Type} of Floor.
   * 
   * @param type
   *          - Type of floor
   */
  public void setType(Type type) {
    this.type = type;
  }

  /**
   * Return {@link toll.parking.library.ParkingSlot ParkingSlot} assign to Vehicle.
   * 
   * @param vehicle
   *          - Vehicle
   * @return - parkingslot
   */
  public ParkingSlot getSlot(Vehicle vehicle) {
    return reservedSlotMap.get(vehicle);
  }

  /**
   * Clear all {@link toll.parking.library.ParkingSlot ParkingSlot} information and reinitialize complete Floor.
   */
  public void reinitialiseFloor() {
    this.flrNum = -1;
    this.capacity = -1;
    this.type = null;
    floorPositions = new BitSet();
    reservedSlotMap = new HashMap<Vehicle, ParkingSlot>();
  }
  
  /**
   * Return Already used {@link toll.parking.library.ParkingSlot ParkingSlot} count.
   * 
   * @return - Integer value
   */
  public int getUsedSlotsCount() {
    return reservedSlotMap.size();
  }
  
  /**
   * Validate if Floor reached to Max Capacity.
   * 
   * @return- boolean - true if floor is full else false.
   */
  public boolean isFull() {
    //index available to use
    int totalOccupiedSlot = getBitSetLength(floorPositions);
    // not free space, all are occupied
    if(totalOccupiedSlot >= capacity) {
      return true;
    }
    return false;
  }
  
  /**
   * Validate if no {@link toll.parking.library.ParkingSlot ParkingSlot} is allocated to any vehicle at this floor.
   * 
   * @return boolean- true if floor is empty else false.
   */
  public boolean isEmpty() {
    return getBitSetLength(floorPositions) == 0;
  }
  
}
