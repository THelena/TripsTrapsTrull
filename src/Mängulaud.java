/**
 * Created by Helena on 13/03/2015.
 */
import java.util.Collections;
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

    int väike_kontroll(ArrayList l, String sümbol){
        int tühja_sagedus = Collections.frequency(l,"");
        int sümboli_sagedus = Collections.frequency(l, sümbol);
        if (tühja_sagedus ==0){
            return -1;
        }
        else if(sümboli_sagedus==2){
            int indeks = l.indexOf("");
            return indeks;
        }
        else{
            return -1;
        }
    }

    int suur_meetod(String sümbol){
        ArrayList<String> väike_list = new ArrayList<String>();
        for (int u = 0; u<7; u+=3){
            väike_list.add(käigud.get(u));
            väike_list.add(käigud.get(u+1));
            väike_list.add(käigud.get(u+2));
            if (väike_kontroll(väike_list,sümbol)!=-1){
                return u+väike_kontroll(väike_list,sümbol);
            }
            väike_list.clear();
        }
        for (int k = 0; k<3; k++){
            väike_list.add(käigud.get(k));
            väike_list.add(käigud.get(k+3));
            väike_list.add(käigud.get(k+6));
            if (väike_kontroll(väike_list,sümbol)!=-1){
                return k+väike_kontroll(väike_list,sümbol)*3;
            }
            väike_list.clear();
            }

        väike_list.add(käigud.get(0));
        väike_list.add(käigud.get(4));
        väike_list.add(käigud.get(8));
        if (väike_kontroll(väike_list,sümbol)!=-1){
            return väike_kontroll(väike_list,sümbol)*4;
        }
        väike_list.clear();

        väike_list.add(käigud.get(2));
        väike_list.add(käigud.get(4));
        väike_list.add(käigud.get(6));
        if (väike_kontroll(väike_list,sümbol)!=-1){
            return 2+väike_kontroll(väike_list,sümbol)*2;
        }
        väike_list.clear();

        return -1;
    }


    int teeKaik() {
        if (suur_meetod("O")!=-1){
            return suur_meetod("O");
        }
        else if (suur_meetod("X")!=-1){
            return suur_meetod(("X"));
        }

        for (int i =0; i < 9; i++) {
            if (käigud.get(i).equals("X") || käigud.get(i).equals("O")) {
                if(arvuti_käigud.contains(i)) {
                    arvuti_käigud.remove(new Integer(i));
                }
            }
        }

        System.out.println(arvuti_käigud);
        int number = juhuslikkus.nextInt(arvuti_käigud.size());
        System.out.println(number);
        System.out.println(arvuti_käigud.get(number));
        return arvuti_käigud.get(number);

    }


    boolean Kontroll(String võistleja){
        for (int u = 0; u<7; u+=3){
            if (käigud.get(u).equals(võistleja) && käigud.get(u+1).equals(võistleja)&&käigud.get(u+2).equals(võistleja)){
                return true;
            }
        }
        for (int k = 0; k<3; k++){
            if (käigud.get(k).equals(võistleja) && käigud.get(k+3).equals(võistleja)&&käigud.get(k+6).equals(võistleja)){
                return true;
            }
        }

        if (käigud.get(0).equals(võistleja) && käigud.get(4).equals(võistleja)&&käigud.get(8).equals(võistleja)){
            return true;
        }
        if (käigud.get(2).equals(võistleja) && käigud.get(4).equals(võistleja)&&käigud.get(6).equals(võistleja)){
            return true;
        }

        else{
            return false;
        }

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
