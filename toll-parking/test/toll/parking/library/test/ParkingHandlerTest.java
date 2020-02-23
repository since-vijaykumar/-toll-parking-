package toll.parking.library.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import toll.parking.library.Car;
import toll.parking.library.Floor;
import toll.parking.library.ParkingHandler;
import toll.parking.library.ParkingSlot;
import toll.parking.library.PricingPolicy;
import toll.parking.library.Type;
import toll.parking.library.Vehicle;

/**
 * Unit test for Parking Toll assignment.
 */
public class ParkingHandlerTest {
  
  String[]       names          = {"Vijay", "Johan", "Maya", "Rati", "Charli"};
  String[]       addresses      = {"BLR", "AUS", "ENG", "USA", "GER"};
  int[]          carNumbers     = {1986, 2001, 5922, 7886, 7777};
  
  Car            vijayCar;
  Car            johanCar;
  Car            mayaCar;
  Car            ratiCar;
  Car            charliCar;
  ParkingHandler parkingHandler = ParkingHandler.getInstance();
  
  @Before
  public void initCars() {
    //clear existing parking
    parkingHandler.emptyParking();
    
    vijayCar = new Car(Type.GASOLINE, names[0], addresses[0], carNumbers[0]);
    johanCar = new Car(Type.ELECTRIC_20KW, names[1], addresses[1], carNumbers[1]);
    mayaCar = new Car(Type.ELECTRIC_20KW, names[2], addresses[2], carNumbers[2]);
    ratiCar = new Car(Type.ELECTRIC_50KW, names[3], addresses[3], carNumbers[3]);
    charliCar = new Car(Type.ELECTRIC_50KW, names[4], addresses[4], carNumbers[4]);
    
  }
  
  @Test
  public void testSingleton() {
    ParkingHandler instance1 = ParkingHandler.getInstance();
    ParkingHandler instance2 = ParkingHandler.getInstance();
    
    assertSame(instance1, instance2);
  }
  
  @Test
  public void testParkVehicle() {
    
    //park the vehicle
    boolean parkVehicle = parkingHandler.parkVehicle(vijayCar);
    assertTrue(parkVehicle);
    
    Floor floorForVehicle = parkingHandler.getFloorForVehicle(vijayCar);
    assertNotNull(floorForVehicle);
    
    int floorNumber = floorForVehicle.getFloorNumber();
    assertTrue(floorNumber == Type.GASOLINE.getIndex());
    assertSame(floorForVehicle.getType(), Type.GASOLINE);
    
    assertSame(floorForVehicle.getType(), vijayCar.getType());
    
    ParkingSlot slot = floorForVehicle.getSlot(vijayCar);
    assertNotNull(slot);
    
    assertTrue(slot.getType() == vijayCar.getType());
    assertNotNull(slot.getAssingedVehicle());
    assertEquals((Car) slot.getAssingedVehicle(), vijayCar);
    
  }
  
  @Test
  public void testUnParkVehicle() {
    //park vehicle
    boolean parkVehicle = parkingHandler.parkVehicle(johanCar);
    assertTrue(parkVehicle);
    
    Floor floorForVehicle = parkingHandler.getFloorForVehicle(johanCar);
    assertNotNull(floorForVehicle);
    
    int floorNumber = floorForVehicle.getFloorNumber();
    assertTrue(floorNumber == Type.ELECTRIC_20KW.getIndex());
    assertSame(floorForVehicle.getType(), Type.ELECTRIC_20KW);
    
    assertSame(floorForVehicle.getType(), johanCar.getType());
    
    ParkingSlot slot = floorForVehicle.getSlot(johanCar);
    assertNotNull(slot);
    
    assertTrue(slot.getType() == johanCar.getType());
    assertNotNull(slot.getAssingedVehicle());
    assertEquals((Car) slot.getAssingedVehicle(), johanCar);
    //Unpack vehicle
    ParkingSlot unparkVehicleSlot = parkingHandler.unparkVehicle(johanCar);
    assertNotNull(unparkVehicleSlot);
    assertSame(unparkVehicleSlot, slot);
    
    Vehicle assingedVehicle = unparkVehicleSlot.getAssingedVehicle();
    assertNull(assingedVehicle);
    
    assertNull(floorForVehicle.getSlot(johanCar));
  }
  
  @Test
  public void testRandomParking() {
    //park vehicle
    Floor gasolineFloor = parkingHandler.getFloorForType(Type.GASOLINE);
    assertTrue(0 == gasolineFloor.getUsedSlotsCount());
    
    boolean vijayVehicle = parkingHandler.parkVehicle(vijayCar);
    assertTrue(vijayVehicle);
    Floor vijayFloor = parkingHandler.getFloorForVehicle(vijayCar);
    assertSame(gasolineFloor, vijayFloor);
    assertTrue(1 == gasolineFloor.getUsedSlotsCount());
    
    Floor elc20kwFloor = parkingHandler.getFloorForType(Type.ELECTRIC_20KW);
    assertTrue(0 == elc20kwFloor.getUsedSlotsCount());
    
    boolean johanVehicle = parkingHandler.parkVehicle(johanCar);
    assertTrue(johanVehicle);
    Floor johanFloor = parkingHandler.getFloorForVehicle(johanCar);
    assertSame(elc20kwFloor, johanFloor);
    assertTrue(1 == elc20kwFloor.getUsedSlotsCount());
    
    boolean mayaVehicle = parkingHandler.parkVehicle(mayaCar);
    assertTrue(mayaVehicle);
    Floor mayaFloor = parkingHandler.getFloorForVehicle(mayaCar);
    assertSame(elc20kwFloor, mayaFloor);
    assertTrue(2 == elc20kwFloor.getUsedSlotsCount());
    
    Floor elc50kwFloor = parkingHandler.getFloorForType(Type.ELECTRIC_50KW);
    assertTrue(0 == elc50kwFloor.getUsedSlotsCount());
    
    boolean ratiVehicle = parkingHandler.parkVehicle(ratiCar);
    assertTrue(ratiVehicle);
    Floor ratiFloor = parkingHandler.getFloorForVehicle(ratiCar);
    assertSame(elc50kwFloor, ratiFloor);
    assertTrue(1 == elc50kwFloor.getUsedSlotsCount());
    
    boolean charliVehicle = parkingHandler.parkVehicle(charliCar);
    assertTrue(charliVehicle);
    
    Floor charliFloor = parkingHandler.getFloorForVehicle(charliCar);
    assertSame(elc50kwFloor, charliFloor);
    assertTrue(2 == elc50kwFloor.getUsedSlotsCount());
    
    //Unpack vehicle
    //GASOLINE type 
    ParkingSlot vijaySlot = vijayFloor.getSlot(vijayCar);
    assertNotNull(vijaySlot);
    ParkingSlot unparkVehicleSlot = parkingHandler.unparkVehicle(vijayCar);
    assertNotNull(unparkVehicleSlot);
    assertSame(vijaySlot, unparkVehicleSlot);
    assertTrue(0 == gasolineFloor.getUsedSlotsCount());
    
    //ELECTRIC_20KW type
    ParkingSlot mayaSlot = mayaFloor.getSlot(mayaCar);
    assertNotNull(mayaSlot);
    ParkingSlot mayaUnparkVehicleSlot = parkingHandler.unparkVehicle(mayaCar);
    assertNotNull(mayaUnparkVehicleSlot);
    assertSame(mayaSlot, mayaUnparkVehicleSlot);
    assertTrue(1 == elc20kwFloor.getUsedSlotsCount());
    
    //ELECTRIC_50KW type
    ParkingSlot ratiSlot = ratiFloor.getSlot(ratiCar);
    assertNotNull(ratiSlot);
    ParkingSlot ratiUnparkVehicleSlot = parkingHandler.unparkVehicle(ratiCar);
    assertNotNull(ratiUnparkVehicleSlot);
    assertSame(ratiSlot, ratiUnparkVehicleSlot);
    assertTrue(1 == elc50kwFloor.getUsedSlotsCount());
    
    ParkingSlot charliSlot = ratiFloor.getSlot(charliCar);
    assertNotNull(charliSlot);
    ParkingSlot charliUnparkVehicleSlot = parkingHandler.unparkVehicle(charliCar);
    assertNotNull(charliUnparkVehicleSlot);
    assertSame(charliSlot, charliUnparkVehicleSlot);
    assertTrue(0 == elc50kwFloor.getUsedSlotsCount());
    
  }
  
  @Test
  public void testParkVehicle_RandomFloorParking() {
    
    List<Vehicle> vehicleList = new ArrayList<>();
    Floor ele20kwFloor = parkingHandler.getFloorForType(Type.ELECTRIC_20KW);
    assertNotNull(ele20kwFloor);
    assertTrue(0 == ele20kwFloor.getUsedSlotsCount());
    
    for(int i = 0; i < ParkingHandler.MAX_SLOT_PER_FLOOR; i++) {
      int nextFreeSlotIndex = ele20kwFloor.getFreeSlotIndex();
      assertTrue(i == nextFreeSlotIndex);
      
      Car car = new Car(Type.ELECTRIC_20KW, "User_" + i, "Address_" + i, i * 3);
      vehicleList.add(car);
      boolean parkVehicle = parkingHandler.parkVehicle(car);
      assertTrue(parkVehicle);
      
    }
    
    assertTrue(ParkingHandler.MAX_SLOT_PER_FLOOR == ele20kwFloor.getUsedSlotsCount());
    
    Vehicle vehicleAt0th = vehicleList.get(0);
    ParkingSlot unparkVehicleSlotAt0th = parkingHandler.unparkVehicle(vehicleAt0th);
    assertNotNull(unparkVehicleSlotAt0th);
    assertTrue(ParkingHandler.MAX_SLOT_PER_FLOOR - 1 == ele20kwFloor.getUsedSlotsCount());
    assertTrue(0 == ele20kwFloor.getFreeSlotIndex());
    
    boolean parkVehicle = parkingHandler.parkVehicle(vehicleAt0th);
    assertTrue(parkVehicle);
    
    Vehicle vehicleAt5nd = vehicleList.get(5);
    ParkingSlot unparkVehicleSlotAt5nd = parkingHandler.unparkVehicle(vehicleAt5nd);
    assertNotNull(unparkVehicleSlotAt5nd);
    assertTrue(ParkingHandler.MAX_SLOT_PER_FLOOR - 1 == ele20kwFloor.getUsedSlotsCount());
    assertTrue(5 == ele20kwFloor.getFreeSlotIndex());
    
    boolean parkVehicle1 = parkingHandler.parkVehicle(vehicleAt5nd);
    assertTrue(parkVehicle1);
    
    Vehicle vehicleAtLast = vehicleList.get(ParkingHandler.MAX_SLOT_PER_FLOOR - 1);
    ParkingSlot unparkVehicleSlotLast = parkingHandler.unparkVehicle(vehicleAtLast);
    assertNotNull(unparkVehicleSlotLast);
    assertTrue(ParkingHandler.MAX_SLOT_PER_FLOOR - 1 == ele20kwFloor.getUsedSlotsCount());
    assertTrue(9 == ele20kwFloor.getFreeSlotIndex());
    
  }
  
  @Test
  public void testCalculateFare() {
    //park vehicle 
    boolean parkVehicle = parkingHandler.parkVehicle(charliCar);
    assertTrue(parkVehicle);
    
    Floor floorForVehicle = parkingHandler.getFloorForVehicle(charliCar);
    assertNotNull(floorForVehicle);
    
    int floorNumber = floorForVehicle.getFloorNumber();
    assertTrue(floorNumber == Type.ELECTRIC_50KW.getIndex());
    assertSame(floorForVehicle.getType(), Type.ELECTRIC_50KW);
    
    assertSame(floorForVehicle.getType(), charliCar.getType());
    
    ParkingSlot slot = floorForVehicle.getSlot(charliCar);
    assertNotNull(slot);
    
    assertSame(slot.getType(), charliCar.getType());
    assertNotNull(slot.getAssingedVehicle());
    assertEquals((Car) slot.getAssingedVehicle(), charliCar);
    
    PricingPolicy fixedPlusPerHour = PricingPolicy.FIXED_PLUS_PER_HOUR;
    int rate = 20;
    
    //No fare until vehicle is still using parking slot
    double fareBefore = parkingHandler.calculateFare(charliCar, fixedPlusPerHour, rate, slot);
    assertTrue(fareBefore == 0.0);
    
    //unpark vehicle
    ParkingSlot unparkVehicleSlot = parkingHandler.unparkVehicle(charliCar);
    
    assertNotNull(unparkVehicleSlot);
    assertSame(unparkVehicleSlot, slot);
    
    Vehicle assingedVehicle = unparkVehicleSlot.getAssingedVehicle();
    assertNull(assingedVehicle);
    assertNull(floorForVehicle.getSlot(charliCar));
    
    //add 2 hours from current time
    Instant endTime = Instant.now().plus(2, ChronoUnit.HOURS);
    slot.setUnassignedTime(endTime);
    
    long minuteSpend = slot.getAssingedTime().until(endTime, ChronoUnit.MINUTES);
    double totalPrice = Type.ELECTRIC_50KW.getFixedPrice() + ((minuteSpend / 60) * rate);
    
    double fareAfter = parkingHandler.calculateFare(charliCar, fixedPlusPerHour, rate, slot);
    assertTrue(totalPrice == fareAfter);
    
  }
  
  @Test
  public void testParkVehicle_InvalidParking() {
    
    //park the vehicle
    boolean parkVehicle = false;
    vijayCar.setType(null);
    try {
      parkVehicle = parkingHandler.parkVehicle(vijayCar);
      fail("This parking should fail as Parking type is :" + null);
    }
    catch(IndexOutOfBoundsException | NullPointerException ex) {
      assertFalse(parkVehicle);
    }
    
  }
  
  @Test
  public void testParkVehicle_InvalidUnparking() {
    
    //park the vehicle
    boolean parkVehicle = parkingHandler.parkVehicle(vijayCar);
    assertTrue(parkVehicle);
    
    Floor floorForVehicle = parkingHandler.getFloorForVehicle(vijayCar);
    assertNotNull(floorForVehicle);
    
    int floorNumber = floorForVehicle.getFloorNumber();
    assertTrue(floorNumber == Type.GASOLINE.getIndex());
    assertSame(floorForVehicle.getType(), Type.GASOLINE);
    
    assertSame(floorForVehicle.getType(), vijayCar.getType());
    
    ParkingSlot slot = floorForVehicle.getSlot(vijayCar);
    assertNotNull(slot);
    
    assertTrue(slot.getType() == vijayCar.getType());
    assertNotNull(slot.getAssingedVehicle());
    assertEquals((Car) slot.getAssingedVehicle(), vijayCar);
    
    ParkingSlot unparkVehicleSlot = null;
    try {
      //Unpack vehicle ( invalid parking )
      unparkVehicleSlot = parkingHandler.unparkVehicle(johanCar);
      fail("This parking should fail as Parking type is :" + null);
    }
    catch(UnsupportedOperationException uoe) {
      assertNull(unparkVehicleSlot);
    }
    
  }
  
  @Test
  public void testParkVehicle_FloorIsFull() {
    
    for(int i = 1; i <= ParkingHandler.MAX_SLOT_PER_FLOOR; i++) {
      Car car = new Car(Type.GASOLINE, "User_" + i, "Address_" + i, i * 3);
      boolean parkVehicle = parkingHandler.parkVehicle(car);
      assertTrue(parkVehicle);
    }
    
    boolean parkVehicle = parkingHandler.parkVehicle(vijayCar);
    assertFalse(parkVehicle);
  }
  
}
