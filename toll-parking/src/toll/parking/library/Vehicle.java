package toll.parking.library;


/**
 * Marker Interface to support different type Vehicles.
 * 
 * @author ashokv
 *
 */
public interface Vehicle {
  /**
   * return {@link toll.parking.library.Type Type} of Vehicle.
   * 
   * @return
   */
  public Type getType();
}
