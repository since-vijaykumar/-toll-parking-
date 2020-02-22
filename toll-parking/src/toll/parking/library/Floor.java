package toll.parking.library;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class Floor {
  
  private int                       flrNum = -1;
  private int                       capacity = -1;
  private BitSet                    freeIndex;
  private BitSet                    occupitedIndex;
  private Map<Vehicle, ParkingSlot> reservedSlotMap;
  private Type                      type;
  
  public Floor(int flrNum, int capacity, Type type) {
    
    this.flrNum = flrNum;
    this.capacity = capacity;
    this.type = type;
    freeIndex = new BitSet();
    occupitedIndex = new BitSet();
    reservedSlotMap = new HashMap<Vehicle, ParkingSlot>();
  }
  
  public boolean assignSlot(Vehicle vehicle) {
    if(vehicle.getType() != type) {
      throw new RuntimeException("Unsupported Vehicle type : " + vehicle.getType().getTypeName() + "for floor type: "
                                 + type.getTypeName());
    }
    //index available to use
    int spaceAvaialbeOnFlr = getFreeIndexFromStart();
    // not free space, all are occupied
    if(spaceAvaialbeOnFlr < 0) {
      return false;
    }
    
    //mark floor index as used
    freeIndex.set(spaceAvaialbeOnFlr);
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
    freeIndex.clear(location.getSecond());
    return parkingSlot;
  }
  
  private int getFreeIndexFromStart() {
    
    int nextClearBit = freeIndex.nextClearBit(0);
    //limit the index's to capacity
    if(nextClearBit >= capacity) {
      return -1;
    }
    return nextClearBit;
  }
  
  public int getBitSetLength(BitSet bitSet) {
    return bitSet.isEmpty() ? bitSet.length() : bitSet.length() - 1;
  }
  
  /* public boolean isFull()
  {
    return avaialbeSpaces.size() >= capacity;
  }*/
  
  public int getCapacity() {
    return capacity;
  }
  
  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }
  
  /*public List<Integer> getAvaialbeSpaces() {
    return avaialbeSpaces;
  }
  
  public void setAvaialbeSpaces(List<Integer> avaialbeSpaces) {
    this.avaialbeSpaces = avaialbeSpaces;
  }
  */
  public BitSet getFreeIndex() {
    return freeIndex;
  }
  
  public void setFreeIndex(BitSet freeIndex) {
    this.freeIndex = freeIndex;
  }
  
  public BitSet getOccupitedIndex() {
    return occupitedIndex;
  }
  
  public void setOccupitedIndex(BitSet occupitedIndex) {
    this.occupitedIndex = occupitedIndex;
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

  public boolean isFull() {
    //index available to use
    int spaceAvaialbeOnFlr = getFreeIndexFromStart();
    // not free space, all are occupied
    if(spaceAvaialbeOnFlr < 0) {
      return true;
    }
    return false;
  }
  
  public boolean isEmpty() {
    return reservedSlotMap.isEmpty();
  }
  
  public ParkingSlot getSlot(Vehicle vehicle) {
    return reservedSlotMap.get(vehicle);
  }
  
  public void reinitialiseFloor() {
    this.flrNum = -1;
    this.capacity = -1;
    this.type = null;
    freeIndex = new BitSet();
    occupitedIndex = new BitSet();
    reservedSlotMap = new HashMap<Vehicle, ParkingSlot>();
  }
}
