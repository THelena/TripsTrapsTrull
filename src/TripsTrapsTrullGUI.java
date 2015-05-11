import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class TripsTrapsTrullGUI extends Application {
    static List<Text> ruudu_tekstid = new ArrayList<>();
    static String alustaja = "arvuti";
    static ArrayList<String> käigud = new ArrayList<String>();
    static int käik;
    static String tulemuseSõne = "";
    static String inimese_nimi;
    static String kontroll_Sõne = "";
    static boolean tulemuseleVajutati = false;


    public static boolean isNumeric(String sõne){
        ArrayList<String> numbrid = new ArrayList<>();
        numbrid.addAll(Arrays.asList("1","2","3","4","5","6","7","8","9"));
        String[] tükid = sõne.split("");
        for (String tükk: tükid){
            if (!numbrid.contains(tükk)){
                return false;
            }
        }
        return true;
    }

    Group tervitusAken(Stage peaLava){
        //AVAAKNA TEKSTID, NUPUD JMS ASJAD
        Group juur = new Group();

        GridPane gp = new GridPane();
        BorderPane bp = new BorderPane();
        Text tervitus = new Text();
        tervitus.setText("Tere tulemast mängima Trips-Traps-Trulli!");
        tervitus.setTextAlignment(TextAlignment.CENTER);
        tervitus.setFont(new Font(20));
        tervitus.setWrappingWidth(400);
        tervitus.setLayoutX(50);
        tervitus.setLayoutY(50);
        juur.getChildren().add(tervitus);

        Text juhis = new Text();
        juhis.setText("Alustamiseks vali, kes teeb esimese käigu, sisesta oma nimi ja vajuta ENTER.");
        juhis.setTextAlignment(TextAlignment.CENTER);
        juhis.setWrappingWidth(300);
        juhis.setLayoutX(100);
        juhis.setLayoutY(80);
        juur.getChildren().add(juhis);

        ToggleGroup raadionupud = new ToggleGroup();
        RadioButton arvuti = new RadioButton("Arvuti");
        arvuti.setToggleGroup(raadionupud);
        arvuti.setSelected(true);
        RadioButton inimene = new RadioButton("Sina");
        inimene.setToggleGroup(raadionupud);
        arvuti.setLayoutX(250);
        arvuti.setLayoutY(105);
        inimene.setLayoutX(250);
        inimene.setLayoutY(125);
        Text alustajaTekst = new Text();
        alustajaTekst.setText("Alustab: ");
        alustajaTekst.setLayoutX(200);
        alustajaTekst.setLayoutY(127);
        arvuti.setUserData("arvuti");
        inimene.setUserData("inimene");
        //RAADIONUPU VÄÄRTUSE SAAMINE
        raadionupud.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (raadionupud.getSelectedToggle() != null) {
                    alustaja = raadionupud.getSelectedToggle().getUserData().toString();
                }
            }
        });
        juur.getChildren().addAll(arvuti, inimene, alustajaTekst);

        TextField nimi = new TextField();
        nimi.setPromptText("Siia sisesta oma nimi.");
        nimi.setLayoutX(175);
        nimi.setLayoutY(150);
        nimi.setAlignment(Pos.CENTER);
        juur.getChildren().add(nimi);


        //ENTERI VAJUTAMINE AVAB MÄNGUAKNA
        nimi.setOnKeyPressed(new EventHandler<KeyEvent>()  {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {



                    //MÄNGUPLATSI TEGEMINE
                    inimese_nimi = nimi.getText();
                    peaLava.close();
                    final Stage mäng = new Stage();
                    mäng.setMinWidth(410);
                    mäng.setMinHeight(500);
                    mäng.initModality(Modality.APPLICATION_MODAL);
                    mäng.initOwner(peaLava);

                    //LOOME MÄNGIMISEKS VAJALIKU LAUA, LISTI
                    for (int a =0; a < 9; a++) {
                        käigud.add("");
                    }
                    Mängulaud laud = new Mängulaud(käigud);

                    //-------------------------------------------------------------
                    //MÄNGUAKNA TEKSTID, NUPUD JMS
                    Group juur = new Group();
                    Text tekst = new Text("Oma käigu tegemiseks vajuta soovitud ruutu." + "\n" + "Edukat mängimist, " + inimese_nimi + "!");
                    tekst.setTextAlignment(TextAlignment.CENTER);
                    tekst.setFont(new Font(15));

                    Button tulemus = new Button();
                    tulemus.setText("Tulemused");

                    tulemus.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        //TULEMUSE NUPULE VAJUTAMINE AVAB TULEMUSTE TABELI
                        @Override
                        public void handle(MouseEvent event) {
                            final Stage tulemused = new Stage();
                            tulemused.initModality(Modality.APPLICATION_MODAL);
                            tulemused.initOwner(mäng);
                            ScrollPane sc = new ScrollPane();
                            GridPane tulemuseTabel = new GridPane();
                            ColumnConstraints col1 = new ColumnConstraints();
                            col1.setPercentWidth(50);
                            ColumnConstraints col2 = new ColumnConstraints();
                            col2.setPercentWidth(25);
                            col2.setHalignment(HPos.CENTER);
                            ColumnConstraints col3 = new ColumnConstraints();
                            col3.setPercentWidth(25);
                            col3.setHalignment(HPos.CENTER);
                            ColumnConstraints col4 = new ColumnConstraints();
                            col4.setPercentWidth(25);
                            col4.setHalignment(HPos.CENTER);
                            tulemuseTabel.getColumnConstraints().addAll(col1, col2, col3, col4);
                            tulemuseTabel.setHgap(10);
                            tulemuseTabel.setVgap(10);
                            tulemuseTabel.setPadding(new Insets(0, 10, 0, 10));
                            //----------------------------------------
                            List<String> andmed = new ArrayList<String>();

                            //FAILIST LUGEMINE, VAJALIKU LISTI TEGEMINE
                            File fail_1 = new File("tulemused.txt");
                            if (fail_1.exists()) {
                                try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fail_1), "UTF-8"))) {
                                    String rida;
                                    int i = 0;
                                    while ((rida = br.readLine()) != null) {
                                        String[] tükid = rida.split(";");
                                        if (i == 0) {
                                            andmed.add(tükid[0]);
                                            andmed.add(tükid[1]);
                                            andmed.add(tükid[2]);
                                            andmed.add(tükid[3]);
                                            i++;
                                        }
                                        else {
                                            if (!tulemuseSõne.equals("") && !tulemuseleVajutati) {
                                                if (inimese_nimi.equals(tükid[0])) {
                                                    int võidud = Integer.parseInt(tükid[1]);
                                                    int viigid = Integer.parseInt(tükid[2]);
                                                    int kaotused = Integer.parseInt(tükid[3]);
                                                    if (tulemuseSõne.equals("võit")) {
                                                        võidud++;
                                                    } else if (tulemuseSõne.equals("viik")) {
                                                        viigid++;
                                                    }
                                                    else {
                                                        kaotused++;
                                                    }
                                                    andmed.add(tükid[0]);
                                                    andmed.add(String.valueOf(võidud));
                                                    andmed.add(String.valueOf(viigid));
                                                    andmed.add(String.valueOf(kaotused));

                                                } else {
                                                    andmed.add(tükid[0]);
                                                    andmed.add(tükid[1]);
                                                    andmed.add(tükid[2]);
                                                    andmed.add(tükid[3]);
                                                }
                                            }
                                            else {
                                                andmed.add(tükid[0]);
                                                andmed.add(tükid[1]);
                                                andmed.add(tükid[2]);
                                                andmed.add(tükid[3]);
                                            }
                                        }
                                    }
                                }
                                catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                try {
                                    fail_1.createNewFile();
                                }
                                catch (IOException e) {
                                    e.printStackTrace();
                                }
                                andmed.add("Nimi");
                                andmed.add("Võidud");
                                andmed.add("Viigid");
                                andmed.add("Kaotused");
                            }
                            if (!tulemuseSõne.equals("") && !andmed.contains(inimese_nimi) && !tulemuseleVajutati) {
                                andmed.add(inimese_nimi);
                                if (tulemuseSõne.equals("võit")) {
                                    andmed.add(String.valueOf(1));
                                    andmed.add(String.valueOf(0));
                                    andmed.add(String.valueOf(0));
                                }
                                else if (tulemuseSõne.equals("viik")) {
                                    andmed.add(String.valueOf(0));
                                    andmed.add(String.valueOf(1));
                                    andmed.add(String.valueOf(0));
                                }
                                else {
                                    andmed.add(String.valueOf(0));
                                    andmed.add(String.valueOf(0));
                                    andmed.add(String.valueOf(1));
                                }
                            }

                            //TULEMUSTE TABELI KUVAMINE
                            int indeks = 0;
                            int rida = 0;
                            while (indeks < andmed.size()) {
                                tulemuseTabel.add(new Text(andmed.get(indeks)), 0, rida);
                                tulemuseTabel.add(new Text(andmed.get(indeks + 1)), 1, rida);
                                tulemuseTabel.add(new Text(andmed.get(indeks + 2)), 2, rida);
                                tulemuseTabel.add(new Text(andmed.get(indeks + 3)), 3, rida);
                                indeks += 4;
                                rida++;
                            }

                            // FAILI UUTE ANDMETE KIRJUTAMINE
                            File fail_2 = new File("tulemused.txt");
                            try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fail_2), "UTF-8"))) {
                                int indeks2 = 0;
                                while (indeks2 < andmed.size()) {
                                    bw.write(andmed.get(indeks2) + ";" + andmed.get(indeks2 + 1) +
                                            ";" + andmed.get(indeks2 + 2) + ";" + andmed.get(indeks2 + 3));
                                    bw.write(System.getProperty("line.separator"));
                                    indeks2 += 4;
                                }
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            //------------------------------------------
                            if (!tulemuseSõne.equals("")) {
                                tulemuseleVajutati = true;
                            }
                            sc.setContent(tulemuseTabel);
                            Scene tulemuseStseen = new Scene(sc, 300, 300);
                            tulemused.setScene(tulemuseStseen);
                            tulemused.setResizable(false);
                            tulemused.show();
                        }
                    });


                    Button uus_mäng = new Button();
                    uus_mäng.setText("Uus mäng");
                    //UUE MÄNGU NUPULE VAJUTAMINE LOOB UUE MÄNGUAKNA
                    uus_mäng.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            for (int i = 0; i < 9; i++) {
                                ruudu_tekstid.get(i).setText("");
                                käigud.set(i, "");
                            }
                            tulemuseleVajutati = false;
                            tulemuseSõne = "";
                            tervitusAken(peaLava);



                            Image võit = new Image("file:sun.png");
                            //Image viik = new Image("file:pokerface.jpg");
                            //Image kaotus = new Image("file:sad.jpg");

                            //ImageView iv1 = new ImageView();
                            //iv1.setImage(võit);
                            ImageView iv2 = new ImageView();
                            iv2.setImage(võit);
                            iv2.setFitHeight(100);
                            iv2.setFitWidth(100);
                            iv2.setPreserveRatio(true);
                            iv2.setSmooth(true);
                            iv2.setCache(true);
                            iv2.setLayoutX(400);
                            iv2.setLayoutY(400);
                            //juur.getChildren().add(iv2);
                        }
                    });

                    //LOOME MÄNGUAKNA RUUDUSTIKU
                    //Ruudustik
                    GridPane gp = new GridPane();
                    for (int i = 1; i < 4; i++) {
                        for (int j = 0; j < 3; j++) {
                            StackPane kuhi = new StackPane();
                            Text ruudu_tekst = new Text("");
                            ruudu_tekst.setFont(new Font(70));
                            ruudu_tekstid.add(ruudu_tekst);
                            Rectangle ruut = new Rectangle(100, 100);
                            ruut.setFill(Color.WHITE);
                            ruut.setStroke(Color.BLACK);
                            ruut.setArcWidth(20);
                            ruut.setArcHeight(20);
                            kuhi.getChildren().addAll(ruut, ruudu_tekst);
                            gp.add(kuhi, j, i);
                        }
                    }

                    Text seis = new Text();
                    seis.setText("Mäng on pooleli.");
                    gp.add(tekst, 0, 0, 3, 1);
                    gp.add(seis, 1, 9);
                    gp.add(uus_mäng,2,8);
                    gp.add(tulemus,0,8);
                    gp.setLayoutX(50);
                    gp.setLayoutY(50);
                    juur.getChildren().add(gp);

                    //--------------------------------------------------------------
                    Scene mänguStseen = new Scene(juur, 400, 500);
                    mäng.setScene(mänguStseen);
                    mäng.show();

                    //MÄNGUAKNA SUURUSE MUUTUMISE JÄLGIMINE
                    mänguStseen.widthProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observableValue, Number vanaStseeniLaius, Number uusStseeniLaius) {
                            double uuslaius = uusStseeniLaius.intValue();
                            double vanalaius = vanaStseeniLaius.intValue();
                            double laiuse_muutus = vanalaius/uuslaius;
                            StackPane sp = new StackPane();
                            Rectangle r = new Rectangle();
                            for (Node N : gp.getChildren()){
                                if (N.getClass()!= sp.getClass()){
                                    continue;
                                }
                                     for (Node s : ((StackPane) N).getChildren()){
                                         if (s.getClass()==r.getClass()){
                                             double vana = ((Rectangle)s).getWidth();
                                             ((Rectangle)s).setWidth(vana/laiuse_muutus);
                                         }
                                     }
                            }
                        }
                    });

                    mänguStseen.heightProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number vanaStseeniKõrgus, Number uusStseeniKõrgus) {
                            double uuskõrgus = uusStseeniKõrgus.intValue();
                            double vanakõrgus = vanaStseeniKõrgus.intValue();
                            double kõrguse_muutus = vanakõrgus/uuskõrgus;
                            StackPane sp = new StackPane();
                            Rectangle r = new Rectangle();
                            for (Node N : gp.getChildren()){
                                if (N.getClass()!= sp.getClass()){
                                    continue;
                                }
                                for (Node s : ((StackPane) N).getChildren()){
                                    if (s.getClass()==r.getClass()){
                                        double vana = ((Rectangle)s).getHeight();
                                        ((Rectangle)s).setHeight(vana / kõrguse_muutus);
                                    }
                                }

                            }
                        }
                    });
                    if (alustaja.equals("arvuti")){
                        int arvuti_number = laud.teeKaik();
                        käigud.set(arvuti_number, "O");
                        laud.setKäigud(käigud);
                        ruudu_tekstid.get(arvuti_number).setText("O");
                    }
                    //RUUDULE VAJUTADES SAAME TEADA, KUHU RUUDULE VAJUTATI
                    gp.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            for (Node node : gp.getChildren()) {
                                if (node instanceof StackPane) {
                                    if (node.getBoundsInParent().contains(event.getSceneX() - 100, event.getSceneY() - 100)) {
                                        int rida = gp.getRowIndex(node);
                                        int veerg = gp.getColumnIndex(node);
                                        if (rida == 1) {
                                            käik = veerg;
                                        } else if (rida == 2) {
                                            käik = veerg + 3;
                                        } else if (rida == 3) {
                                            käik = veerg + 6;
                                        }
                                        System.out.println(käik);
                                    }
                                }
                            }
                            try {
                                if ((käigud.get(käik).equals("X") || käigud.get(käik).equals("O")) && tulemuseSõne.equals("")) {
                                    throw new ValeKastiErind("Sinna kasti ei saa käia.");
                                }
                                //else if(!tulemuseSõne.equals("")) {
                                //    throw new MängLäbiErind("Mäng on läbi. Ei ole mõtet enam proovida käia.");
                                //}
                                if (!(käigud.get(käik).equals("X") || käigud.get(käik).equals("O")) && tulemuseSõne.equals("")) {
                                    käigud.set(käik, "X");
                                    laud.setKäigud(käigud);
                                    ruudu_tekstid.get(käik).setText("X");
                                    if (laud.kontroll("X")) {
                                        System.out.println("võit");
                                        tulemuseSõne = "võit";
                                        seis.setText("VÕIT!");
                                    }
                                    else if (!käigud.contains("")) {
                                        System.out.println("viik");
                                        tulemuseSõne = "viik";
                                        seis.setText("VIIK!");
                                    }
                                    else if (tulemuseSõne.equals("")) {
                                        try {
                                            int arvuti_number = laud.teeKaik();
                                            käigud.set(arvuti_number, "O");
                                            laud.setKäigud(käigud);
                                            sleep(10);
                                            ruudu_tekstid.get(arvuti_number).setText("O");
                                            if (laud.kontroll("O")) {
                                                System.out.println("kaotus");
                                                tulemuseSõne = "kaotus";
                                                seis.setText("KAOTUS!");

                                            }
                                            else if (!käigud.contains("")) {
                                                System.out.println("viik");
                                                tulemuseSõne = "viik";
                                                seis.setText("VIIK!");
                                            }
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                }
                            }
                            catch (ValeKastiErind e) {
                                System.out.println(e.getMessage());
                            }
                            //catch (MängLäbiErind e) {
                            //    System.out.println(e.getMessage());
                            //}
                        }
                    });
                    //MÄNGUPLATSI TEGEMINE LÕPPEB
                }
            }
        });

        return juur;
    }

    @Override
    public void start(Stage peaLava) throws Exception {
        //peaLava.setMinWidth();
        Scene peaStseen = new Scene(tervitusAken(peaLava), 500, 175);
        peaLava.setTitle("Trips-Traps-Trull");
        peaLava.setScene(peaStseen);
        peaLava.setResizable(false);
        peaLava.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
