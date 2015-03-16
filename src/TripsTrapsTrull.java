/**
 * Created by Helena on 13/03/2015.
 */
import java.util.ArrayList;
import java.util.Scanner;
public class TripsTrapsTrull {

    public static void main(String[] args) throws Exception {
        //Tervitus
        System.out.println("Tere tulemast mängima Trips-Traps-Trulli!");
        System.out.println("Teie olete X ja arvuti on O. Ärge ajage nulli ja O-d segamini!");
        System.out.println("Edukat mängimist!");

        //Vajalik list, selle loomine, skänner
        Scanner sc = new Scanner(System.in);
        ArrayList<String> käigud = new ArrayList<String>(); //Salvestame indeksile vastava ruudu sees oleva märgi ("X" või "O", "" kui keegi pole sinna käinud)
        for (int a =0; a < 9; a++) {
            käigud.add("");
        }

        Mängulaud laud = new Mängulaud(käigud);

        while (true) { //jätkame senikaua kuni mäng on lõppenud
            System.out.println(laud);
            System.out.println("Millisesse ruutu soovite oma käigu teha? ");
            int number = sc.nextInt();
            if (käigud.get(number).equals("X") || käigud.get(number).equals("O")) {  //kontrollime kas antud ruutu on üldsegi võimalik käia
                System.out.println("Sinna ruutu ei ole võimalik kahjuks käia!");
            }
            else {
                käigud.set(number, "X");
                laud.setKäigud(käigud); //uuendame käikude listi
                if (laud.kontroll("X") == true) { //kontrollime võitu
                    System.out.println(laud);
                    System.out.println("Olete võitnud!");
                    break;
                }
                if (!käigud.contains("")) { //kontrollime viiki (ainult pärast inimese käiku on võimalik viigiseis)
                    System.out.println("Olete jäänud viiki!");
                    break;
                }

                int arvuti_number = laud.teeKaik(); //arvuti teeb oma käigu
                käigud.set(arvuti_number, "O");
                laud.setKäigud(käigud); //uuendame käikude listi
                if (laud.kontroll("O") == true) { //kontrollime võitu
                    System.out.println(laud);
                    System.out.println("Olete kaotanud! Vahest peaksite strateegiat muutma?");
                    break;
                }
                //jätkame, kui keegi pole võitnud ega pole ka viigiseis
            }
        }
    }
}
