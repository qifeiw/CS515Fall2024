
import java.util.ArrayList;
import java.util.Random;

import static mystery.Sorts.*;

public class Detective {

    public static void main(String[] args) {

        ArrayList<Integer> arr = new ArrayList<>();
        Random rand = new Random(0);

        for(int i = 0; i < 500; i++) {
            arr.add(rand.nextInt(1001));
        }

        System.out.println("original:");
        System.out.println(arr.toString());

        //sort4(arr, 150);

        //interrupt sort after 150 iterations of the sort algorithm
        sort1(arr, 150);
        //sort3(arr, 150);


        System.out.println("current:");
        System.out.println(arr.toString());

    }
}
