/**
 * Created by Helena on 13/03/2015.
 */
import java.util.ArrayList;
import java.util.Scanner;
public class TripsTrapsTrull {

    public static void main(String[] args) {

        ArrayList<Integer> järjend = new ArrayList<Integer>();
        for (int a =0; a < 9; a++) {
            järjend.add(a);
        }

        int u = 0;
        for (int i = 0; i < 7; i++) {
            if (i == 0 || i == 2 || i == 4 || i == 6) {
                System.out.println("+-----------+");
            }
            else {
                System.out.println("| " + järjend.get(u) + " | " + järjend.get(u+1) + " | " + järjend.get(u+2) + " |");
                u += 3;
            }
        }
    }
}
