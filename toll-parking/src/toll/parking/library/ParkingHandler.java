package toll.parking.library;

import java.util.ArrayList;
import java.util.List;

public final class ParkingHandler {
  
  final static private ParkingHandler INSTANCE           = new ParkingHandler();
  final static public int             MAX_SLOT_PER_FLOOR = 10;
  final private List<Floor>           parkingArea;
  
  private ParkingHandler() {
    parkingArea = new ArrayList<Floor>();
    init();
  }
  
  private void init() {
    Type[] values = Type.values();
    for(Type type : values) {
      parkingArea.add(type.getIndex(), new Floor(type.getIndex(), MAX_SLOT_PER_FLOOR, type));
    }
  }
  
  public static ParkingHandler getInstance() {
    return INSTANCE;
  }
  
  public boolean parkVehicle(Vehicle vehicle) {
    
    Floor floorForVehicle = getFloorForVehicle(vehicle);
    
    if(floorForVehicle.isFull()) {
      return false;
    }
    
    return floorForVehicle.assignSlot(vehicle);
  }
  
  /**
   * unpark the vehicle.
   * 
   * @param vehicle
   * @return
   */
  public ParkingSlot unparkVehicle(Vehicle vehicle)
  {
    Floor floorForVehicle = getFloorForVehicle(vehicle);
    
    if(floorForVehicle.isEmpty()) {
      throw new UnsupportedOperationException("Parking is empty, Vehicle Type : " + vehicle.getType().getTypeName()
                                          + " is not allocated to floor:" + 
                         floorForVehicle.getFloorNumber());
    }
    
    ParkingSlot unassignSlot = floorForVehicle.unassignSlot(vehicle);
    return unassignSlot;
    //    return calculateFare(vehicle, pricePolicy, rate, unassignSlot);
  }

  public double calculateFare(Vehicle vehicle, PricingPolicy pricePolicy, double rate,
                               ParkingSlot unassignSlot) {
    double charges = 0;
    //calculate the price 
    long minuteSpend = unassignSlot.totalTimeSpent();
    
    if(minuteSpend < 0)
    {
      return charges;
    }
    else
    {
      switch(pricePolicy)
      {
        case PER_HOUR:
          charges = ((double) minuteSpend / 60) * rate;
          break;
        case FIXED_PLUS_PER_HOUR:
          charges = vehicle.getType().getFixedPrice() + ((minuteSpend / 60) * rate);
          break;
         default:
          throw new RuntimeException("Invalid Pricing policy + " + pricePolicy);
      }
      return charges;
    }
  }
  
  public Floor getFloorForVehicle(Vehicle vehicle) {
    try
    {
      return getFloorForType(vehicle.getType());
    }
    catch(IndexOutOfBoundsException | NullPointerException ex)
    {
      throw new RuntimeException("Unsupported Vehicle type : " + vehicle.getType().getTypeName());
    }
  }

  public Floor getFloorForType(Type type) {
    return parkingArea.get(type.getIndex());
  }
  
  public int getTotalNumberOfFloor() {
    return parkingArea.size();
  }
  
  public void emptyParking() {
    parkingArea.clear();
    init();
  }
}
