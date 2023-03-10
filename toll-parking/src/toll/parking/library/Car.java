package toll.parking.library;

/**
 * Car containing information of car type, owner , owner address and car registered number.
 * 
 * @author ashokv
 *
 */
public class Car implements Vehicle {
  
  private Type type;
  private int    carNumber;
  private String owner;
  private String address;
  
  public Car(Type type, String owner, String address, int carNumber) {
    
    this.type = nullCheck(type);
    this.carNumber = nullCheck(carNumber);
    this.owner = nullCheck(owner);
    this.address = nullCheck(address);
  }
  

  private <T> T nullCheck(T object) {
    //throw null pointer exception if object is null;
    object.getClass();
    return object;
  }
  
  /**
   * Return {@link toll.parking.library.Type Type} of car.
   * 
   */
  public Type getType() {
    return this.type;
  }
  
  /**
   * Set {@link toll.parking.library.Type Type} for car.
   * 
   * @param type
   *          of vehicle
   * 
   */
  public void setType(Type type) {
    this.type = type;
  }
  
  /**
   * Return car registered number.
   * 
   * @return integer value
   */
  public int getCarNumber() {
    return carNumber;
  }
  
  /**
   * Set registered number.
   * 
   * @param carNumber
   *          integer value
   */
  public void setCarNumber(int carNumber) {
    this.carNumber = carNumber;
  }
  
  /**
   * return car owner.
   * 
   * @return String
   */
  public String getOwner() {
    return owner;
  }
  
  /**
   * Set car owner.
   * 
   * @param owner
   *          String value
   */
  public void setOwner(String owner) {
    this.owner = owner;
  }
  
  /**
   * Return owner address.
   * 
   * @return String
   */
  public String getAddress() {
    return address;
  }
  
  /**
   * Set owner address.
   * 
   * @param address
   *          String
   */
  public void setAddress(String address) {
    this.address = address;
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.hashCode());
    result = prime * result + carNumber;
    result = prime * result + ((owner == null) ? 0 : owner.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
    return result;
  }
  
  @Override
  public boolean equals(Object obj) {
    if(this == obj)
      return true;
    if(obj == null)
      return false;
    if(getClass() != obj.getClass())
      return false;
    Car other = (Car) obj;
    if(address == null) {
      if(other.address != null)
        return false;
    }
    else if(!address.equals(other.address))
      return false;
    if(carNumber != other.carNumber)
      return false;
    if(owner == null) {
      if(other.owner != null)
        return false;
    }
    else if(!owner.equals(other.owner))
      return false;
    if(type != other.type)
      return false;
    return true;
  }

}
