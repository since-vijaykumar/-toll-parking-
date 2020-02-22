package toll.parking.library;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

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
   * unassign and return slot used by vehicle.
   * 
   * @param vehicle
   * @return
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
   * Return next available slot index.
   * 
   * @return
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
  
  public int getCapacity() {
    return capacity;
  }
  
  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }
  
  public BitSet getFloorPosition() {
    return floorPositions;
  }
  
  public void setFloorPosition(BitSet freeIndex) {
    this.floorPositions = freeIndex;
  }
  
  public int getFloorNumber() {
    return flrNum;
  }
  
  public void setFloorNumber(int flrNum) {
    this.flrNum = flrNum;
  }
  
  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public ParkingSlot getSlot(Vehicle vehicle) {
    return reservedSlotMap.get(vehicle);
  }

  
  public void reinitialiseFloor() {
    this.flrNum = -1;
    this.capacity = -1;
    this.type = null;
    floorPositions = new BitSet();
    reservedSlotMap = new HashMap<Vehicle, ParkingSlot>();
  }
  
  public int getUsedSlotsCount() {
    return reservedSlotMap.size();
  }
  
  public boolean isFull() {
    //index available to use
    int totalOccupiedSlot = getBitSetLength(floorPositions);
    // not free space, all are occupied
    if(totalOccupiedSlot >= capacity) {
      return true;
    }
    return false;
  }
  
  public boolean isEmpty() {
    return getBitSetLength(floorPositions) == 0;
  }
  

  
}
