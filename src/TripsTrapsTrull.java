/**
 * Created by Helena on 13/03/2015.
 */
import java.util.ArrayList;
import java.util.Scanner;
public class TripsTrapsTrull {


    public static void main(String[] args) throws Exception {

        System.out.println("Tere tulemast mängima Trips-Traps-Trulli!");
        System.out.println("Teie olete X ja arvuti on 0.");
        System.out.println("Edukat mängimist!");

        //boolean mängu_jätk = true;
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> järjend = new ArrayList<Integer>();
        ArrayList<Integer> arvuti_käigud = new ArrayList<Integer>();
        ArrayList<String> käigud = new ArrayList<String>();
        for (int a =0; a < 9; a++) {
            järjend.add(a);
            käigud.add("");
            arvuti_käigud.add(a);
        }

        Mängulaud a = new Mängulaud(järjend, arvuti_käigud, käigud);

        while (true) {
            System.out.println(a);
            System.out.println("Millisesse ruutu soovite oma käigu teha? ");
            int number = sc.nextInt();
            if (number == 7) {
                break;
            }
            if (käigud.get(number).equals("X") || käigud.get(number).equals("O")) {
                System.out.println("Sinna ruutu ei ole võimalik kahjuks käia!");
            }
            else {
                käigud.set(number, "X");
                a.setKäigud(käigud);
                int arvuti_number = a.teeKaik();
                käigud.set(arvuti_number, "O");
                a.setKäigud(käigud);

            }
        }




    }
}
