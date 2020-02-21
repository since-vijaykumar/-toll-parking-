package toll.parking.library;

import java.util.BitSet;

public class TimeTesting {
  
  public static int getBitSetLength(BitSet bitSet) {
    return bitSet.isEmpty() ? bitSet.length() : bitSet.length() - 1;
  }
  static public void bitSet() {
    BitSet bs1 = new BitSet(10);
    BitSet bs2 = new BitSet(6);
    
    /* set is BitSet class method 
       expalined in next articles */
    /* System.out.println("before any value");
    System.out.println(bs1);
    System.out.println("Size : " + bs1.size());
    System.out.println("Length : " + bs1.length());
    System.out.println("actual Length : " + getBitSetLength(bs1));
    System.out.println("next free : " + bs1.nextClearBit(0));*/
    

    bs1.set(0);
    bs1.set(1);
    bs1.set(2);
    bs1.set(3);
    System.out.println("+++++++++++++++++after any value++++++++++++++");
    //    System.out.println(bs1.get(1));
    //    System.out.println(bs1.get(4));
    //    System.out.println(bs1.get(5));
    System.out.println(bs1);
    System.out.println(bs1.get(4));
    System.out.println("Size : " + bs1.size());
    System.out.println("Length : " + bs1.length());
    System.out.println("actual Length : " + getBitSetLength(bs1));
    System.out.println("next free : " + bs1.nextClearBit(0));
    /*   
    // assign values to bs2 
    bs2.set(4);
    bs2.set(6);
    bs2.set(5);
    bs2.set(1);
    bs2.set(2);
    bs2.set(3);
    bs2.set(10);
    bs2.set(11);
    bs2.set(12);
    
    System.out.println("+++==2 second===============");
    System.out.println(bs2);
    System.out.println("Size : " + bs2.size());
    System.out.println("Length : " + bs2.length());*/

    
  }
  public static void main(String[] args) {
    bitSet();
    /*Instant now = Instant.now();
    long long1 = now.getLong(ChronoField.MILLI_OF_SECOND);
    System.out.println("milli second of days :  " + long1);
    System.out.println("" + now);
    try {
      Thread.sleep(1000);
    }
    catch(InterruptedException e) {
      e.printStackTrace();
    }
    Instant now2 = Instant.now();
    System.out.println(now2);
    long until = now.until(now2, ChronoUnit.MILLIS);
    System.out.println(until);
    Duration ofMillis = Duration.ofMillis(until);
    System.out.println(ofMillis.getSeconds());
    
    long until2 = now.until(now2, ChronoUnit.SECONDS);
    System.out.println(until2);
    
    long until3 = now2.until(now, ChronoUnit.SECONDS);
    System.out.println(until3);*/
  }
}
