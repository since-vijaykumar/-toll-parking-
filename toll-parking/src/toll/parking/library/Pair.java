package toll.parking.library;

/**
 * Retain information in Pair.
 * 
 * @author ashokv
 *
 * @param <T>
 *          first element
 * @param <K>
 *          second element
 */
public class Pair<T, K> {
  T first;
  K second;
  
  public Pair() {
  }
  
  public Pair(T first, K second) {
    this.first = first;
    this.second = second;
  }
  
  public T getFirst() {
    return first;
  }
  
  public void setFirst(T first) {
    this.first = first;
  }
  
  public K getSecond() {
    return second;
  }
  
  public void setSecond(K second) {
    this.second = second;
  }
  
}
