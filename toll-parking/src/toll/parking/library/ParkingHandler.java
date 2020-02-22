package toll.parking.library;

import java.util.ArrayList;
import java.util.List;

public final class ParkingHandler {
  
  final static private ParkingHandler INSTANCE           = new ParkingHandler();
  final int                           MAX_SLOT_PER_FLOOR = 10;
  final List<Floor>                   parkingArea;
  
  private ParkingHandler() {
    Type[] values = Type.values();
    parkingArea = new ArrayList<Floor>();
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
      System.out.println("Parking is full for Vehicle Type : " + vehicle.getType().getTypeName());
      return false;
    }
    
    return floorForVehicle.assignSlot(vehicle);
  }
  
  /**
   * unpark the vehicle and return fare based on uses.
   * 
   * @param vehicle
   * @return
   */
  public double unparkAndReturnFare(Vehicle vehicle, PricingPolicy pricePolicy, double rate)
  {
    Floor floorForVehicle = getFloorForVehicle(vehicle);
    
    if(floorForVehicle.isEmpty()) {
      throw new UnsupportedOperationException("Parking is empty, Vehicle Type : " + vehicle.getType().getTypeName()
                                          + " is not allocated to floor:" + 
                         floorForVehicle.getFloorNumber());
    }
    
    ParkingSlot unassignSlot = floorForVehicle.unassignSlot(vehicle);
    return calculateFare(vehicle, pricePolicy, rate, unassignSlot);
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
      }
      return charges;
    }
  }
  
  private Floor getFloorForVehicle(Vehicle vehicle) {
    try
    {
      return parkingArea.get(vehicle.getType().getIndex());
    }
    catch(IndexOutOfBoundsException ex)
    {
      throw new RuntimeException("Unsupported Vehicle type : " + vehicle.getType().getTypeName());
    }
  }
  
  public static void main(String[] args) {
    //0 is for gasoline
    //1 is for 20kw
    //2 is for 30kw
    Car car = new Car(Type.GASOLINE, "vijay", "BLR", 1);
    ParkingHandler parkHandler = getInstance();
    parkHandler.parkVehicle(car);
    
    Floor floorForVehicle = parkHandler.getFloorForVehicle(car);
    ParkingSlot slot = floorForVehicle.getSlot(car);
    
    //    double fare = parkHandler.unparkAndReturnFare(car, PricingPolicy.PER_HOUR, 20);
    
    //add 2 hours
    /*slot.setOutTime(Instant.now().plus(2, ChronoUnit.HOURS));
    double calculateFare = parkHandler.calculateFare(car, PricingPolicy.PER_HOUR, 20, slot);
    System.out.println(calculateFare);*/
    
    Car car2 = new Car(Type.GASOLINE, "vijay", "BLR", 2);
    parkHandler.parkVehicle(car2);
    
    Floor floorForVehicle2 = parkHandler.getFloorForVehicle(car2);
    ParkingSlot slot2 = floorForVehicle2.getSlot(car2);
    
    Car car3 = new Car(Type.ELECTRIC_20KW, "Prachi", "BLR", 2);
    parkHandler.parkVehicle(car3);
    
    Floor floorForVehicle3 = parkHandler.getFloorForVehicle(car3);
    ParkingSlot slot3 = floorForVehicle3.getSlot(car3);
    
  }
  
}
