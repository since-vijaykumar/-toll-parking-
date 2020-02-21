package toll.parking.library;

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
  
  public int getIndex() {
    return index;
  }
  
  public String getTypeName() {
    return typeName;
  }
  
  public double getFixedPrice()
  {
    return fixedPrice;
  }
}
