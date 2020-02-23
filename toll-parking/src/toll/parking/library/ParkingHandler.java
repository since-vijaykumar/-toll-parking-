package toll.parking.library;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton ParkingHandler is responsible for handling parking and unparking of vehicle and calculating fare based on
 * uses.
 * 
 * @author ashokv
 *
 */
public final class ParkingHandler {
  
  final static private ParkingHandler INSTANCE           = new ParkingHandler();
  final static public int             MAX_SLOT_PER_FLOOR = 10;
  final private List<Floor>           parkingArea;
  
  private ParkingHandler() {
    parkingArea = new ArrayList<Floor>();
    init();
  }
  
  /**
   * Initialize different floors and max capacity of each floor.
   */
  private void init() {
    Type[] values = Type.values();
    for(Type type : values) {
      parkingArea.add(type.getIndex(), new Floor(type.getIndex(), MAX_SLOT_PER_FLOOR, type));
    }
  }
  
  /**
   * Return instance Parking Handler.
   * 
   * @return instance
   */
  public static ParkingHandler getInstance() {
    return INSTANCE;
  }
  
  /**
   * Assign {@link toll.parking.library.Vehicle Vehicle} to {@link toll.parking.library.ParkingSlot ParkingSlot}.
   * 
   * @param vehicle
   *          - Vehicle
   * @return boolean - true for successful parking else false.
   */
  public boolean parkVehicle(Vehicle vehicle) {
    
    Floor floorForVehicle = getFloorForVehicle(vehicle);
    
    if(floorForVehicle.isFull()) {
      return false;
    }
    
    return floorForVehicle.assignSlot(vehicle);
  }
  
  /**
   * Unassign {@link toll.parking.library.Vehicle Vehicle} from {@link toll.parking.library.ParkingSlot ParkingSlot}.
   * 
   * @param vehicle
   *          - Vehicle
   * @throws UnsupportedOperationException
   *           if {@link toll.parking.library.Floor Floor} is empty.
   * @return boolean - true for successful unparking else false.
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
  }

  /**
   * Calculate fare based on how much time {@link toll.parking.library.Vehicle Vehicle} was assigned to
   * {@link toll.parking.library.ParkingSlot ParkingSlot} and Charging Rate.
   * 
   * @param vehicle
   *          - {@link toll.parking.library.Vehicle Vehicle}
   * @param pricePolicy
   *          - {@link toll.parking.library.PricingPolicy PricingPolicy}
   * @param charingRate
   *          - double
   * @param unassignSlot
   *          - {@link toll.parking.library.ParkingSlot ParkingSlot}
   * @throws RuntimeException
   *           in case of Invalid {@link toll.parking.library.PricingPolicy PricingPolicy}
   * @return double - price
   */
  public double calculateFare(Vehicle vehicle, PricingPolicy pricePolicy, double charingRate,
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
          charges = ((double) minuteSpend / 60) * charingRate;
          break;
        case FIXED_PLUS_PER_HOUR:
          charges = vehicle.getType().getFixedPrice() + ((minuteSpend / 60) * charingRate);
          break;
         default:
          throw new RuntimeException("Invalid Pricing policy + " + pricePolicy);
      }
      return charges;
    }
  }
  
  /**
   * Return {@link toll.parking.library.Floor Floor} assigned to vehicle.
   * 
   * @param vehicle
   *          floor vehicle.
   * @throws RuntimeException
   *           in case of Unsupported {@link toll.parking.library.Vehicle Vehicle} type
   * @return floor
   */
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

  /**
   * Return Floor for {@link toll.parking.library.Type Type}
   * 
   * @param type
   *          floor type
   * @return floor matching the type.
   */
  public Floor getFloorForType(Type type) {
    return parkingArea.get(type.getIndex());
  }
  
  /**
   * Return number of Floors available.
   * 
   * @return integer
   */
  public int getTotalNumberOfFloor() {
    return parkingArea.size();
  }
  
  /**
   * Clear all parking and reinitialize again.
   */
  public void emptyParking() {
    parkingArea.clear();
    init();
  }
}
