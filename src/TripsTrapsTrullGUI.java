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
                    mäng.setMinWidth(475);
                    mäng.setMinHeight(475);
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
                    Text tekst = new Text("Oma käigu tegemiseks vajuta soovitud ruutu. Edukat mängimist, " + inimese_nimi + "!");
                    tekst.setTextAlignment(TextAlignment.CENTER);
                    tekst.setFont(new Font(15));
                    tekst.setLayoutX(100);
                    tekst.setLayoutY(50);
                    tekst.setWrappingWidth(300);
                    juur.getChildren().add(tekst);

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
                            //FAILI KIRJUTAMINE
                            if (!tulemuseSõne.equals("")) {
                                System.out.println("ja");
                                File fail = new File("tulemused.txt");
                                try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fail), "UTF-8"))) {
                                    if (fail.exists()) {
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
                                                String nimi = tükid[0];
                                                int võidud = Integer.parseInt(tükid[1]);
                                                int viigid = Integer.parseInt(tükid[2]);
                                                int kaotused = Integer.parseInt(tükid[3]);
                                                if (inimese_nimi.equals(nimi)) {
                                                    switch (tulemuseSõne) {
                                                        case "võit":
                                                            võidud++;
                                                            break;
                                                        case "kaotus":
                                                            kaotused++;
                                                            break;
                                                        default:
                                                            viigid++;
                                                            break;
                                                    }
                                                }
                                                andmed.add(nimi);
                                                andmed.add(String.valueOf(võidud));
                                                andmed.add(String.valueOf(viigid));
                                                andmed.add(String.valueOf(kaotused));
                                            }
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                if (andmed.size() == 0) {
                                    andmed.add("Nimi");
                                    andmed.add("Võidud");
                                    andmed.add("Viigid");
                                    andmed.add("Kaotused");
                                }
                                File fail1 = new File("tulemused.txt");
                                try {
                                    if (!fail1.exists()) {
                                        fail1.createNewFile();
                                    }
                                }
                                catch (IOException e) {
                                    e.printStackTrace();
                                }
                                try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fail1), "UTF-8"))) {
                                    int i = 0;
                                    while (i < andmed.size()) {
                                        bw.write(andmed.get(i) + ";" + andmed.get(i + 1) +
                                                ";" + andmed.get(i + 2) + ";" + andmed.get(i + 3));
                                        bw.write(System.getProperty("line.separator"));
                                        i += 4;
                                    }
                                    if (!andmed.contains(inimese_nimi) && !tulemuseSõne.equals("")) {
                                        int võidud = 0;
                                        int viigid = 0;
                                        int kaotused = 0;
                                        switch (tulemuseSõne) {
                                            case "võit":
                                                võidud++;
                                                break;
                                            case "kaotus":
                                                kaotused++;
                                                break;
                                            default:
                                                viigid++;
                                                break;
                                        }
                                        bw.write(inimese_nimi + ";" + võidud + ";" + viigid + ";" + kaotused);
                                        bw.write(System.getProperty("line.separator"));
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            //FAILIST LUGEMINE
                            File fail2 = new File("tulemused.txt");
                            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fail2), "UTF-8"))) {
                                String rida;
                                int i = 0;
                                while ((rida = br.readLine()) != null) {
                                    String[] tükid = rida.split(";");
                                    tulemuseTabel.add(new Text(tükid[0]), 0, i);
                                    tulemuseTabel.add(new Text(tükid[1]), 1, i);
                                    tulemuseTabel.add(new Text(tükid[2]), 2, i);
                                    tulemuseTabel.add(new Text(tükid[3]), 3, i);
                                    i++;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //------------------------------------------
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
                            tulemuseSõne = "";
                            tervitusAken(peaLava);
                            //mäng.close();
                            //peaLava.show();


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
                    for (int i = 0; i < 3; i++) {
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
                    gp.add(uus_mäng,2,8);
                    gp.add(tulemus,0,8);
                    gp.setLayoutX(100);
                    gp.setLayoutY(100);
                    juur.getChildren().add(gp);

                    //--------------------------------------------------------------
                    Scene mänguStseen = new Scene(juur, 500, 500);
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
                                if (käigud.get(käik).equals("X") || käigud.get(käik).equals("O") && tulemuseSõne.equals("")) {
                                    throw new ValeKastiErind("Sinna kasti ei saa käia.");
                                }
                                if (!(käigud.get(käik).equals("X") || käigud.get(käik).equals("O")) && tulemuseSõne.equals("")) {
                                    käigud.set(käik, "X");
                                    laud.setKäigud(käigud);
                                    ruudu_tekstid.get(käik).setText("X");
                                    if (laud.kontroll("X")) {
                                        System.out.println("võit");
                                        tulemuseSõne = "võit";
                                    } else if (!käigud.contains("")) {
                                        System.out.println("viik");
                                        tulemuseSõne = "viik";
                                    } else if (tulemuseSõne.equals("")) {
                                        try {
                                            int arvuti_number = laud.teeKaik();
                                            käigud.set(arvuti_number, "O");
                                            laud.setKäigud(käigud);
                                            sleep(10);
                                            ruudu_tekstid.get(arvuti_number).setText("O");
                                            if (laud.kontroll("O")) {
                                                System.out.println("kaotus");
                                                tulemuseSõne = "kaotus";
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
