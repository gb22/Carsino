package group10.algorithm;

/**
 * Created by gb22 on 2015-03-22.
 * Used for testing of the algorithm
 */
public class algTest {
    public static void main(String[] args) {
        algorithm slots=new algorithm();
        slots.spin();
        System.out.println(slots.getResult());
        System.out.println();
        System.out.println(slots.get1()+" "+slots.get2()+" "+slots.get3());
    }
}
