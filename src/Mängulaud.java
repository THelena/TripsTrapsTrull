/**
 * Created by Helena on 13/03/2015.
 */
import java.util.ArrayList;
import java.util.Random;

public class Mängulaud {
    private ArrayList<Integer> ruudud;
    private ArrayList<Integer> arvuti_käigud;
    private ArrayList<String> käigud;
    Random juhuslikkus = new Random();

    public Mängulaud(ArrayList<Integer> ruudud, ArrayList<Integer> arvuti_käigud, ArrayList<String> käigud) {
        this.ruudud = ruudud;
        this.arvuti_käigud = arvuti_käigud;
        this.käigud = käigud;
    }

     public ArrayList<String> getKäigud() {
        return käigud;
    }

    public void setKäigud(ArrayList<String> käigud) {
        this.käigud = käigud;
    }

    int teeKaik() {
        for (int i =0; i < 9; i++) {
            if (käigud.get(i).equals("X") || käigud.get(i).equals("O")) {
                if(arvuti_käigud.contains(i)) {
                    arvuti_käigud.remove(i);
                }
            }
        }

        System.out.println(arvuti_käigud);
        int number = juhuslikkus.nextInt(arvuti_käigud.size());
        System.out.println(number);
        System.out.println(arvuti_käigud.get(number));
        return arvuti_käigud.get(number);

    }



    public String toString() {
        StringBuilder sb = new StringBuilder();
        int u = 0;
        for (int i = 0; i < 7; i++) {
            if (i == 0 || i == 2 || i == 4 || i == 6) {
                sb.append("+-----------+" + "\n");
            }
            else {
                if (käigud.get(u).equals("X") || käigud.get(u).equals("O")) {
                    sb.append("| " + käigud.get(u) + " | ");
                }
                else {
                    sb.append("| " + ruudud.get(u) + " | ");
                }
                if (käigud.get(u+1).equals("X") || käigud.get(u+1).equals("O")){
                    sb.append(käigud.get(u + 1) + " | ");
                }
                else {
                    sb.append(ruudud.get(u + 1) + " | ");
                }
                if (käigud.get(u+2).equals("X") || käigud.get(u+2).equals("O")) {
                    sb.append(käigud.get(u + 2) + " |" + "\n");
                }
                else {
                    sb.append(ruudud.get(u + 2) + " |" + "\n");
                }
                u += 3;
            }
        }
        return sb.toString();
    }
}
