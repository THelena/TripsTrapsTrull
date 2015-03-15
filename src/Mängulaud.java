/**
 * Created by Helena on 13/03/2015.
 */
import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;

public class Mängulaud {
    //Isendid
    private ArrayList<Integer> arvuti_käigud;
    private ArrayList<String> käigud;
    Random juhuslikkus = new Random();

    //konstruktor
    public Mängulaud(ArrayList<Integer> arvuti_käigud, ArrayList<String> käigud) {
        this.arvuti_käigud = arvuti_käigud;
        this.käigud = käigud;
    }

     public void setKäigud(ArrayList<String> käigud) {
        this.käigud = käigud;
    }

    //Kontrollime kas kolmeelemendilises listis on üks tühi sõne ja kaks samasugust sümbolit- ehk võiduseis
    int kahe_elemendi_kontroll(ArrayList järjend, String sümbol){
        int tühja_sagedus = Collections.frequency(järjend,"");
        int sümboli_sagedus = Collections.frequency(järjend, sümbol);
        if (tühja_sagedus == 0){
            return -1;
        }
        else if(sümboli_sagedus == 2){
            int indeks = järjend.indexOf("");
            return indeks;
        }
        else{
            return -1;
        }
    }
    //Kontrollime, kas kolmeelemendilises listis on arvuti sümbol ja ülejäänud list on vaba (ehk kas on mõtet sinna listi enda võitu teostama hakata)
    int ühe_elemendi_kontroll(ArrayList järjend, String sümbol) {
        int tühja_sagedus = Collections.frequency(järjend,"");
        if (tühja_sagedus != 2) {
            return -1;
        }
        else if (järjend.contains("X")) {
            return -1;
        }
        else {
            int number = juhuslikkus.nextInt(2);
            if (number == 0) {
                return järjend.indexOf("");
            }
            else {
                return järjend.lastIndexOf("");
            }
        }
    }
    //Kontrollime, kas arvutil on võimalik võita. Kui ei, siis kontrollime kas inimesel on võimalik võita ja üritame seda takistada
    int võidukäik(String sümbol){
        ArrayList<String> väike_list = new ArrayList<String>();
        //Kontrollime ridade võimalikke käike
        for (int u = 0; u < 7; u += 3){
            väike_list.add(käigud.get(u));
            väike_list.add(käigud.get(u+1));
            väike_list.add(käigud.get(u+2));
            if (kahe_elemendi_kontroll(väike_list, sümbol)!=-1){
                return u + kahe_elemendi_kontroll(väike_list, sümbol); //sobivaim käik
            }
            väike_list.clear(); //teeme väikse listi tühjaks uue rea/veeru kontrollimiseks
        }
        //Kontrollime veergude võimalikke käike
        for (int k = 0; k<3; k++){
            väike_list.add(käigud.get(k));
            väike_list.add(käigud.get(k+3));
            väike_list.add(käigud.get(k+6));
            if (kahe_elemendi_kontroll(väike_list, sümbol)!=-1){
                return k + kahe_elemendi_kontroll(väike_list, sümbol)*3; //sobivaim käik
            }
            väike_list.clear(); //tühjendame listi
            }
        //1. diagonaali võimalik käik
        väike_list.add(käigud.get(0));
        väike_list.add(käigud.get(4));
        väike_list.add(käigud.get(8));
        if (kahe_elemendi_kontroll(väike_list, sümbol)!=-1){
            return kahe_elemendi_kontroll(väike_list, sümbol)*4;
        }
        väike_list.clear(); //tühjendame listi
        //2. diagonaali võimalik käik
        väike_list.add(käigud.get(2));
        väike_list.add(käigud.get(4));
        väike_list.add(käigud.get(6));
        if (kahe_elemendi_kontroll(väike_list, sümbol)!=-1){
            return 2 + kahe_elemendi_kontroll(väike_list, sümbol)*2;
        }
        väike_list.clear(); //tühjendame listi

        return -1; //kui ei olnud võimalikku võidukäiku või võidu takistamise käiku, siis tagastame -1
    }
    //Kontrollime, kas arvutil on selline seis, et reas/veerus/diagonaalil on üks tema sümbol ja ülejäänud on tühjad ehk sinna võimalik võitu looma minna
    int teise_elemendi_käik (String sümbol) {
        ArrayList<String> väike_list = new ArrayList<String>();
        //Kontrollime ridade võimalikke käike
        for (int u = 0; u < 7; u += 3){
            väike_list.add(käigud.get(u));
            väike_list.add(käigud.get(u+1));
            väike_list.add(käigud.get(u+2));
            if (ühe_elemendi_kontroll(väike_list, sümbol) != -1){
                return u + ühe_elemendi_kontroll(väike_list, sümbol); //sobivaim käik
            }
            väike_list.clear(); //teeme väikse listi tühjaks uue rea/veeru kontrollimiseks
        }
        //Kontrollime veergude võimalikke käike
        for (int k = 0; k<3; k++){
            väike_list.add(käigud.get(k));
            väike_list.add(käigud.get(k+3));
            väike_list.add(käigud.get(k+6));
            if (ühe_elemendi_kontroll(väike_list, sümbol)!= -1){
                return k + ühe_elemendi_kontroll(väike_list, sümbol)*3; //sobivaim käik
            }
            väike_list.clear(); //tühjendame listi
        }
        //1. diagonaali võimalik käik
        väike_list.add(käigud.get(0));
        väike_list.add(käigud.get(4));
        väike_list.add(käigud.get(8));
        if (ühe_elemendi_kontroll(väike_list, sümbol)!= -1){
            return ühe_elemendi_kontroll(väike_list, sümbol)*4;
        }
        väike_list.clear(); //tühjendame listi
        //2. diagonaali võimalik käik
        väike_list.add(käigud.get(2));
        väike_list.add(käigud.get(4));
        väike_list.add(käigud.get(6));
        if (ühe_elemendi_kontroll(väike_list, sümbol)!= -1){
            return 2 + ühe_elemendi_kontroll(väike_list, sümbol)*2;
        }
        väike_list.clear(); //tühjendame listi

        return -1; //kui ei olnud sobilikku käiku, siis tagastame -1
    }

    int teeKaik() {
        if (võidukäik("O") != -1){
            return võidukäik("O");
        }
        else if (võidukäik("X")!= -1){
            return võidukäik(("X"));
        }
        else if (teise_elemendi_käik("O") != -1) {
            return teise_elemendi_käik("O");
        }
        else {
            for (int i = 0; i < 9; i++) {
                if (käigud.get(i).equals("X") || käigud.get(i).equals("O")) {
                    if (arvuti_käigud.contains(i)) {
                        arvuti_käigud.remove(new Integer(i)); //jätame alles ainult võimalikud käigud
                    }
                }
            }
            int number = juhuslikkus.nextInt(arvuti_käigud.size());
            return arvuti_käigud.get(number); //tagastame võimalikest käikudest täiesti suvalise käigu
        }
    }

    boolean kontroll(String võistleja){
        //Kontrollime ridade võitu
        for (int u = 0; u < 7; u += 3){
            if (käigud.get(u).equals(võistleja) && käigud.get(u+1).equals(võistleja)&&käigud.get(u+2).equals(võistleja)){
                return true;
            }
        }
        //Kontrollime veergude võitu
        for (int k = 0; k < 3; k++){
            if (käigud.get(k).equals(võistleja) && käigud.get(k+3).equals(võistleja)&&käigud.get(k+6).equals(võistleja)){
                return true;
            }
        }
        //Kontrollime diagonaalide võitu
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
                    sb.append("| " + u + " | ");
                }
                if (käigud.get(u+1).equals("X") || käigud.get(u+1).equals("O")){
                    sb.append(käigud.get(u + 1) + " | ");
                }
                else {
                    sb.append((u + 1) + " | ");
                }
                if (käigud.get(u+2).equals("X") || käigud.get(u+2).equals("O")) {
                    sb.append(käigud.get(u + 2) + " |" + "\n");
                }
                else {
                    sb.append((u + 2) + " |" + "\n");
                }
                u += 3;
            }
        }
        return sb.toString();
    }
}
