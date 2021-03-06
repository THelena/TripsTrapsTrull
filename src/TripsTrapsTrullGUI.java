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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class TripsTrapsTrullGUI extends Application {

    //VAJALIKUD STAATILISED MUUTUJAD
    static List<Text> ruudu_tekstid = new ArrayList<>();
    static String alustaja = "arvuti";
    static ArrayList<String> käigud = new ArrayList<String>();
    static int käik;
    static String tulemuseSõne = "";
    static String inimese_nimi;
    static boolean nime_kontroll = false;
    static boolean tulemusVärskendatud = false;

    //PÄRAST IGAT MÄNGU VÄRSKENDAME FAILIS ANDMEID
    public static void värskendaFailiAndmeid() {
        if (!tulemusVärskendatud) {
            //FAILIST LUGEMINE, VAJALIKU LISTI TEGEMINE
            List<String> andmed = new ArrayList<String>();
            File fail_1 = new File("tulemused.txt");
            if (fail_1.exists()) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fail_1), "UTF-8"))) {
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
                        } else {
                            if (inimese_nimi.equals(tükid[0])) {
                                int võidud = Integer.parseInt(tükid[1]);
                                int viigid = Integer.parseInt(tükid[2]);
                                int kaotused = Integer.parseInt(tükid[3]);
                                if (tulemuseSõne.equals("võit")) {
                                    võidud++;
                                } else if (tulemuseSõne.equals("viik")) {
                                    viigid++;
                                } else {
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
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    fail_1.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                andmed.add("Nimi");
                andmed.add("Võidud");
                andmed.add("Viigid");
                andmed.add("Kaotused");
            }
            if (!andmed.contains(inimese_nimi)) {
                andmed.add(inimese_nimi);
                if (tulemuseSõne.equals("võit")) {
                    andmed.add(String.valueOf(1));
                    andmed.add(String.valueOf(0));
                    andmed.add(String.valueOf(0));
                } else if (tulemuseSõne.equals("viik")) {
                    andmed.add(String.valueOf(0));
                    andmed.add(String.valueOf(1));
                    andmed.add(String.valueOf(0));
                } else {
                    andmed.add(String.valueOf(0));
                    andmed.add(String.valueOf(0));
                    andmed.add(String.valueOf(1));
                }
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    Group tervitusAken(Stage peaLava){

        //ALGAB AVAAKNA TEGEMINE
        Group juur = new Group();

        //AVAAKNA TERVITUSTEKST
        Text tervitus = new Text();
        tervitus.setText("Tere tulemast mängima Trips-Traps-Trulli!");
        tervitus.setTextAlignment(TextAlignment.CENTER);
        tervitus.setFont(new Font(20));
        tervitus.setWrappingWidth(400);
        tervitus.setLayoutX(50);
        tervitus.setLayoutY(50);
        tervitus.setFont(Font.font(null, FontWeight.BOLD, 20));
        tervitus.setFill(Color.TOMATO);
        juur.getChildren().add(tervitus);

        //AVAAKNA JUHIS
        Text juhis = new Text();
        juhis.setText("Alustamiseks vali, kes teeb esimese käigu, sisesta oma nimi ja vajuta ENTER.");
        juhis.setFont(Font.font(null, FontPosture.ITALIC, 13));
        juhis.setTextAlignment(TextAlignment.CENTER);
        juhis.setWrappingWidth(300);
        juhis.setLayoutX(100);
        juhis.setLayoutY(75);
        juur.getChildren().add(juhis);

        //ALUSTAJA VALIMINE
        ToggleGroup raadionupud = new ToggleGroup();
        RadioButton arvuti = new RadioButton("ARVUTI");
        arvuti.setToggleGroup(raadionupud);
        arvuti.setSelected(true);
        RadioButton inimene = new RadioButton("SINA");
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

        //INIMESE NIME KÜSIMINE
        TextField nimi = new TextField();
        nimi.setPromptText("Siia sisesta oma nimi.");
        nimi.setLayoutX(175);
        nimi.setLayoutY(150);
        nimi.setAlignment(Pos.CENTER);
        juur.getChildren().add(nimi);

        //AVAAKNA TEGEMINE LÕPPEB
        //-----------------------------------------------------------------------------------------------------------------------

        while(true) {
            //ENTERI VAJUTAMINE AVAB MÄNGUAKNA
            nimi.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    peaLava.show();
                    nime_kontroll = false;
                    inimese_nimi = nimi.getText();

                    if (!nime_kontroll) {
                        if (event.getCode().equals(KeyCode.ENTER)) {
                            try {
                                //KUI INIMESE NIMI ON TÜHI SÕNE, SIIS MÄNG EI ALGA
                                if (inimese_nimi.equals("")) {
                                    throw new TühjaSõneErind("Nimi ei tohi olla tühi sõne!");
                                } else {
                                    nime_kontroll = true;
                                    peaLava.close();

                                    //ALGAB MÄNGUAKNA LOOMINE
                                    final Stage mäng = new Stage();
                                    mäng.setMinWidth(510);
                                    mäng.setMinHeight(510);
                                    mäng.initModality(Modality.APPLICATION_MODAL);
                                    mäng.initOwner(peaLava);

                                    //LOOME MÄNGIMISEKS VAJALIKU LAUA, LISTI
                                    for (int a = 0; a < 9; a++) {
                                        käigud.add("");
                                    }
                                    Mängulaud laud = new Mängulaud(käigud);

                                    //JUHISE- JA TERVITUSTEKST
                                    Group juur = new Group();
                                    Text tekst = new Text("Oma käigu tegemiseks vajuta soovitud ruutu. Edukat mängimist, " + inimese_nimi + "!");
                                    tekst.setTextAlignment(TextAlignment.CENTER);
                                    tekst.setFill(Color.MIDNIGHTBLUE);
                                    tekst.setLayoutX(100);
                                    tekst.setLayoutY(50);
                                    tekst.setWrappingWidth(300);
                                    tekst.setFont(Font.font(null, FontWeight.BOLD, 15));
                                    juur.getChildren().add(tekst);

                                    //TULEMUSE PILT
                                    Image tühiPilt = new Image("file:tühiPilt.png");
                                    ImageView pildiVaade = new ImageView();
                                    pildiVaade.setImage(tühiPilt);
                                    pildiVaade.setFitHeight(100);
                                    pildiVaade.setFitWidth(100);
                                    pildiVaade.setPreserveRatio(true);
                                    pildiVaade.setSmooth(true);
                                    pildiVaade.setCache(true);
                                    pildiVaade.setLayoutX(390);
                                    pildiVaade.setLayoutY(400);
                                    juur.getChildren().add(pildiVaade);

                                    //MÄNGU SEISU TEKST
                                    Text seis = new Text();
                                    seis.setText("Mäng on veel pooleli.");
                                    seis.setFill(Color.MIDNIGHTBLUE);
                                    seis.setLayoutX(100);
                                    seis.setLayoutY(460);
                                    seis.setTextAlignment(TextAlignment.CENTER);
                                    seis.setFont(new Font(15));
                                    seis.setWrappingWidth(300);
                                    juur.getChildren().add(seis);

                                    //TULEMUSTE NUPP
                                    Button tulemus = new Button();
                                    tulemus.setText("Tulemused");
                                    tulemus.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        //TULEMUSE NUPULE VAJUTAMINE AVAB TULEMUSTE TABELI
                                        @Override
                                        public void handle(MouseEvent event) {
                                            //TULEMUSTETABELI TINGIMUSED
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

                                            //LOEME FAILIST ANDMED, PANEME TABELISSE
                                            File fail = new File("tulemused.txt");
                                            if (fail.exists()) {
                                                try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fail), "UTF-8"))) {
                                                    String rida;
                                                    int reaindeks = 0;
                                                    while ((rida = br.readLine()) != null) {
                                                        String[] tükid = rida.split(";");
                                                        tulemuseTabel.add(new Text(tükid[0]), 0, reaindeks);
                                                        tulemuseTabel.add(new Text(tükid[1]), 1, reaindeks);
                                                        tulemuseTabel.add(new Text(tükid[2]), 2, reaindeks);
                                                        tulemuseTabel.add(new Text(tükid[3]), 3, reaindeks);
                                                        reaindeks++;
                                                    }
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            else {
                                                tulemuseTabel.add(new Text("Nimi"), 0, 0);
                                                tulemuseTabel.add(new Text("Võidud"), 1, 0);
                                                tulemuseTabel.add(new Text("Viigid"), 2, 0);
                                                tulemuseTabel.add(new Text("Kaotused"), 3, 0);
                                            }

                                            sc.setContent(tulemuseTabel);
                                            Scene tulemuseStseen = new Scene(sc, 300, 300);
                                            tulemused.setScene(tulemuseStseen);
                                            tulemused.setResizable(false);
                                            tulemused.show();
                                        }
                                    });
                                    tulemus.setLayoutX(115);
                                    tulemus.setLayoutY(415);
                                    juur.getChildren().add(tulemus);

                                    //UUE MÄNGU NUPP
                                    Button uus_mäng = new Button();
                                    uus_mäng.setText("Uus mäng");
                                    uus_mäng.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event) {
                                            //UUE MÄNGU NUPULE VAJUTAMINE LOOB UUE MÄNGUAKNA, NULLIB ERINEVAD LISTID, MUUDAB VAJALIKE MUUTUJATE VÄÄRTUST
                                            for (int i = 0; i < 9; i++) {
                                                ruudu_tekstid.get(i).setText("");
                                                käigud.set(i, "");
                                            }
                                            tulemusVärskendatud = false;
                                            tulemuseSõne = "";
                                            tervitusAken(peaLava);
                                            seis.setText("Mäng on veel pooleli.");
                                            pildiVaade.setImage(tühiPilt);

                                            if (alustaja.equals("arvuti")) {
                                                int arvuti_number = laud.teeKaik();
                                                käigud.set(arvuti_number, "O");
                                                laud.setKäigud(käigud);
                                                ruudu_tekstid.get(arvuti_number).setText("O");
                                                ruudu_tekstid.get(arvuti_number).setFill(Color.CORNFLOWERBLUE);
                                            }
                                        }
                                    });
                                    uus_mäng.setLayoutX(315);
                                    uus_mäng.setLayoutY(415);
                                    juur.getChildren().add(uus_mäng);

                                    //MÄNGUAKNA RUUDUSTIK
                                    GridPane gp = new GridPane();
                                    for (int i = 0; i < 3; i++) {
                                        for (int j = 0; j < 3; j++) {
                                            StackPane kuhi = new StackPane();
                                            Text ruudu_tekst = new Text("");
                                            ruudu_tekst.setFont(Font.font(null, FontWeight.BOLD, 70));
                                            ruudu_tekstid.add(ruudu_tekst);
                                            Rectangle ruut = new Rectangle(100, 100);
                                            ruut.setFill(Color.ALICEBLUE);
                                            ruut.setStroke(Color.MIDNIGHTBLUE);
                                            ruut.setStrokeWidth(2);
                                            ruut.setArcWidth(20);
                                            ruut.setArcHeight(20);
                                            kuhi.getChildren().addAll(ruut, ruudu_tekst);
                                            gp.add(kuhi, j, i);
                                        }
                                    }
                                    gp.setLayoutX(100);
                                    gp.setLayoutY(100);
                                    juur.getChildren().add(gp);

                                    //MÄNGUAKNA TEGEMISE LÕPETAMINE
                                    Scene mänguStseen = new Scene(juur, 515, 520, Color.ALICEBLUE);
                                    mäng.setScene(mänguStseen);
                                    mäng.show();

                                    //----------------------------------------------------------------------------------

                                    //MÄNGUAKNA SUURUSE MUUTUMISE JÄLGIMINE
                                    mänguStseen.widthProperty().addListener(new ChangeListener<Number>() {
                                        @Override
                                        public void changed(ObservableValue<? extends Number> observableValue, Number vanaStseeniLaius, Number uusStseeniLaius) {
                                            double uuslaius = uusStseeniLaius.intValue();
                                            double vanalaius = vanaStseeniLaius.intValue();
                                            double laiuse_muutus = vanalaius / uuslaius;
                                            StackPane sp = new StackPane();
                                            Rectangle r = new Rectangle();
                                            for (Node N : gp.getChildren()) {
                                                if (N.getClass() != sp.getClass()) {
                                                    continue;
                                                }
                                                for (Node s : ((StackPane) N).getChildren()) {
                                                    if (s.getClass() == r.getClass()) {
                                                        double vana = ((Rectangle) s).getWidth();
                                                        ((Rectangle) s).setWidth(vana / laiuse_muutus);
                                                    }
                                                }
                                            }
                                            //NIIMOODI LIIGUKS KA GRIDPANE, AGA RUUTUDE INDEKSID LÄHEKSID SASSI
                                            //gp.setLayoutX((uuslaius - gp.getWidth()) / 2);

                                            /*tulemus.setLayoutX(((uuslaius - gp.getWidth()) / 2) + ((gp.getWidth() / 3) - tulemus.getWidth()) / 2);
                                            uus_mäng.setLayoutX(((uuslaius - gp.getWidth()) / 2) + 2 * (gp.getWidth() / 3) + ((gp.getWidth() / 3) - uus_mäng.getWidth()) / 2);
                                            tekst.setLayoutX((uuslaius - gp.getWidth()) / 2);
                                            seis.setLayoutX((uuslaius - gp.getWidth()) / 2);
                                            tekst.setWrappingWidth(gp.getWidth());
                                            seis.setWrappingWidth(gp.getWidth());
                                            pildiVaade.setLayoutX(((uuslaius - gp.getWidth()) / 2) + gp.getWidth() - 10);*/

                                            tulemus.setLayoutX(100 + ((gp.getWidth() / 3) - tulemus.getWidth()) / 2);
                                            uus_mäng.setLayoutX(100 + 2 * (gp.getWidth() / 3) + ((gp.getWidth() / 3) - uus_mäng.getWidth()) / 2);
                                            tekst.setWrappingWidth(gp.getWidth());
                                            seis.setWrappingWidth(gp.getWidth());
                                            pildiVaade.setLayoutX(100 + gp.getWidth() - 10);
                                        }
                                    });

                                    mänguStseen.heightProperty().addListener(new ChangeListener<Number>() {
                                        @Override
                                        public void changed(ObservableValue<? extends Number> observable, Number vanaStseeniKõrgus, Number uusStseeniKõrgus) {
                                            double uuskõrgus = uusStseeniKõrgus.intValue();
                                            double vanakõrgus = vanaStseeniKõrgus.intValue();
                                            double kõrguse_muutus = vanakõrgus / uuskõrgus;
                                            StackPane sp = new StackPane();
                                            Rectangle r = new Rectangle();
                                            for (Node N : gp.getChildren()) {
                                                if (N.getClass() != sp.getClass()) {
                                                    continue;
                                                }
                                                for (Node s : ((StackPane) N).getChildren()) {
                                                    if (s.getClass() == r.getClass()) {
                                                        double vana = ((Rectangle) s).getHeight();
                                                        ((Rectangle) s).setHeight(vana / kõrguse_muutus);
                                                    }
                                                }

                                            }
                                            //gp.setLayoutY((uuskõrgus - gp.getHeight()) / 2);
                                            //gp liigub kui 100 asendada ülaloleva avaldisega
                                            tekst.setLayoutY(100 - 50);
                                            tulemus.setLayoutY(100 + gp.getHeight() + 10);
                                            uus_mäng.setLayoutY(100 + gp.getHeight() + 10);
                                            seis.setLayoutY(100 + gp.getHeight() + 15 + tulemus.getHeight() + 15);
                                            pildiVaade.setLayoutY(100 + gp.getHeight());
                                        }
                                    });

                                    //---------------------------------------------------------------------------------------------

                                    //ALGAB MÄNGIMINE
                                    if (alustaja.equals("arvuti")) {
                                        int arvuti_number = laud.teeKaik();
                                        käigud.set(arvuti_number, "O");
                                        laud.setKäigud(käigud);
                                        ruudu_tekstid.get(arvuti_number).setText("O");
                                        ruudu_tekstid.get(arvuti_number).setFill(Color.CORNFLOWERBLUE);
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
                                                        if (rida == 0) {
                                                            käik = veerg;
                                                        } else if (rida == 1) {
                                                            käik = veerg + 3;
                                                        } else {
                                                            käik = veerg + 6;
                                                        }
                                                    }
                                                }
                                            }
                                            try {
                                                //KASTI, KUHU ON JUBA VAREM KÄIDUD, EI SAA ENAM KÄIA. TEKIB ERIND!
                                                if ((käigud.get(käik).equals("X") || käigud.get(käik).equals("O")) && tulemuseSõne.equals("")) {
                                                    throw new ValeKastiErind("Sinna kasti ei saa käia.");
                                                }


                                                if (!(käigud.get(käik).equals("X") || käigud.get(käik).equals("O")) && tulemuseSõne.equals("")) {
                                                    seis.setText("Mäng on veel pooleli.");
                                                    käigud.set(käik, "X");
                                                    laud.setKäigud(käigud);
                                                    ruudu_tekstid.get(käik).setText("X");
                                                    ruudu_tekstid.get(käik).setFill(Color.TOMATO);
                                                    if (laud.kontroll("X")) {
                                                        tulemuseSõne = "võit";
                                                        seis.setText("Tubli! Sa võitsid!");
                                                        Image võit = new Image("file:happy.png");
                                                        pildiVaade.setImage(võit);
                                                        värskendaFailiAndmeid();
                                                        tulemusVärskendatud = true;
                                                    } else if (!käigud.contains("")) {
                                                        tulemuseSõne = "viik";
                                                        seis.setText("Harjuta veel. Jäite arvutiga viiki.");
                                                        Image viik = new Image("file:pokerface.png");
                                                        pildiVaade.setImage(viik);
                                                        värskendaFailiAndmeid();
                                                        tulemusVärskendatud = true;
                                                    } else if (tulemuseSõne.equals("")) {
                                                        try {
                                                            int arvuti_number = laud.teeKaik();
                                                            käigud.set(arvuti_number, "O");
                                                            laud.setKäigud(käigud);
                                                            sleep(10);
                                                            ruudu_tekstid.get(arvuti_number).setText("O");
                                                            ruudu_tekstid.get(arvuti_number).setFill(Color.CORNFLOWERBLUE);
                                                            if (laud.kontroll("O")) {
                                                                tulemuseSõne = "kaotus";
                                                                seis.setText("Sa kaotasid! Muuda oma strateegiat.");
                                                                Image kaotus = new Image("file:sad.png");
                                                                pildiVaade.setImage(kaotus);
                                                                värskendaFailiAndmeid();
                                                                tulemusVärskendatud = true;
                                                            } else if (!käigud.contains("")) {
                                                                tulemuseSõne = "viik";
                                                                seis.setText("Harjuta veel. Jäite arvutiga viiki.");
                                                                Image viik = new Image("file:pokerface.png");
                                                                pildiVaade.setImage(viik);
                                                                värskendaFailiAndmeid();
                                                                tulemusVärskendatud = true;
                                                            }
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                            } catch (ValeKastiErind e) {
                                                seis.setText(e.getMessage());
                                            }
                                        }
                                    });
                                }
                            } catch (TühjaSõneErind e) {
                                Text tühjaSõneTekst = new Text(e.getMessage());
                                tühjaSõneTekst.setLayoutX(175);
                                tühjaSõneTekst.setLayoutY(190);
                                tühjaSõneTekst.setFont(Font.font(null, FontWeight.BOLD, 12));
                                juur.getChildren().add(tühjaSõneTekst);
                                tervitusAken(peaLava);
                            }
                        }}}});
            return juur;
        }
    }

    @Override
    public void start(Stage peaLava) throws Exception {
        Scene peaStseen = new Scene(tervitusAken(peaLava), 500, 195, Color.LIGHTYELLOW);
        peaLava.setTitle("Trips-Traps-Trull");
        peaLava.setScene(peaStseen);
        peaLava.setResizable(false);
        peaLava.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

