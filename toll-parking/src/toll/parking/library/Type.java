package toll.parking.library;

/**
 * Type of Vehicle, Each type have it's own {@link toll.parking.library.Floor Floor} and Fixed price associated with it.
 * 
 * @author ashokv
 *
 */
public enum Type {
  GASOLINE(0, "Gasoline", 0), ELECTRIC_20KW(1, "20KW_Electric", 10), ELECTRIC_50KW(2, "50KW_Electric", 25);
  
  private int index;
  private String typeName;
  
  private double fixedPrice;
  
  
  Type(int index, String typeName, double fixedPrice) {
    this.index = index;
    this.typeName = typeName;
    this.fixedPrice = fixedPrice; 
  }
  
  /**
   * Index position of each type.
   * 
   * @return index
   */
  public int getIndex() {
    return index;
  }
  
  /**
   * String representation of type.
   * 
   * @return String
   */
  public String getTypeName() {
    return typeName;
  }
  
  /**
   * Fixed price associated with type.
   * 
   * @return double
   */
  public double getFixedPrice()
  {
    return fixedPrice;
  }
}
