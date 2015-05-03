import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
import java.util.ArrayList;
import java.util.List;


public class TripsTrapsTrullGUI extends Application {
    static List<Text> ruudu_tekstid = new ArrayList<>();
    static List<Rectangle> ruudud = new ArrayList<>();


    Group tervitusAken(Stage peaLava) {
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
        juhis.setText("Alustamiseks sisesta oma nimi ja vajuta ENTER.");
        juhis.setTextAlignment(TextAlignment.CENTER);
        juhis.setWrappingWidth(300);
        juhis.setLayoutX(100);
        juhis.setLayoutY(80);
        juur.getChildren().add(juhis);


        TextField nimi = new TextField();
        nimi.setPromptText("Siia sisesta oma nimi.");
        nimi.setLayoutX(175);
        nimi.setLayoutY(100);
        nimi.setAlignment(Pos.CENTER);
        juur.getChildren().add(nimi);

        nimi.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    //MÄNGUPLATSI TEGEMINE
                    peaLava.close();
                    String inimese_nimi = nimi.getText();
                    final Stage mäng = new Stage();
                    mäng.initModality(Modality.APPLICATION_MODAL);
                    mäng.initOwner(peaLava);

                    //-------------------------------------------------------------
                    Group juur = new Group();
                    Text tekst = new Text("Mängu alustamiseks vali, kes teeb esimese käigu. Oma käigu tegemiseks vajuta soovitud ruutu. Edukat mängimist, " + inimese_nimi + "!");
                    tekst.setTextAlignment(TextAlignment.CENTER);
                    tekst.setFont(new Font(15));
                    tekst.setLayoutX(100);
                    tekst.setLayoutY(50);
                    tekst.setWrappingWidth(300);
                    juur.getChildren().add(tekst);

                    Button tulemus = new Button();
                    tulemus.setText("Tulemused");
                    tulemus.setLayoutX(325);
                    tulemus.setLayoutY(450);
                    juur.getChildren().add(tulemus);
                    tulemus.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            final Stage tulemused = new Stage();
                            tulemused.initModality(Modality.APPLICATION_MODAL);
                            tulemused.initOwner(mäng);
                            GridPane tulemuseTabel = new GridPane();
                            ColumnConstraints col1 = new ColumnConstraints();
                            col1.setPercentWidth(50);
                            ColumnConstraints col2 = new ColumnConstraints();
                            col2.setPercentWidth(25);
                            ColumnConstraints col3 = new ColumnConstraints();
                            col3.setPercentWidth(25);
                            tulemuseTabel.getColumnConstraints().addAll(col1, col2, col3);
                            tulemuseTabel.setHgap(10);
                            tulemuseTabel.setVgap(10);
                            tulemuseTabel.setPadding(new Insets(0, 10, 0, 10));
                            Text nimi = new Text("Nimi");
                            Text võidud = new Text("Võite kokku");
                            Text kaotused = new Text("Kaotusi kokku");


                            tulemuseTabel.add(nimi, 0, 0);
                            tulemuseTabel.add(võidud, 1, 0);
                            tulemuseTabel.add(kaotused, 2, 0);


                            Scene tulemuseStseen = new Scene(tulemuseTabel, 300, 100);
                            tulemused.setScene(tulemuseStseen);
                            tulemused.show();
                        }
                    });

                    Button uus_mäng = new Button();
                    uus_mäng.setText("Uus mäng");
                    uus_mäng.setLayoutX(100);
                    uus_mäng.setLayoutY(450);
                    uus_mäng.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
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
                            juur.getChildren().add(iv2);
                        }
                    });
                    juur.getChildren().add(uus_mäng);


                    //Ruudustik
                    GridPane gp = new GridPane();
                    for (int i = 0; i < 3; i++) {
                        for (int j = 0; j < 3; j++) {
                            StackPane kuhi = new StackPane();
                            Text ruudu_tekst = new Text(" ");
                            ruudu_tekst.setFont(new Font(40));
                            ruudu_tekstid.add(ruudu_tekst);
                            Rectangle ruut = new Rectangle(100, 100);
                            ruut.setFill(Color.WHITE);
                            ruut.setStroke(Color.BLACK);
                            ruut.setArcWidth(20);
                            ruut.setArcHeight(20);
                            ruudud.add(ruut);
                            kuhi.getChildren().addAll(ruut, ruudu_tekst);
                            gp.add(kuhi, i, j);
                        }
                    }

                    gp.setLayoutX(100);
                    gp.setLayoutY(100);
                    juur.getChildren().add(gp);
                    System.out.println(gp.getWidth());

                    //--------------------------------------------------------------
                    Scene mänguStseen = new Scene(juur, 500, 500);
                    mäng.setScene(mänguStseen);
                    mäng.show();

                    gp.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            for (Node node : gp.getChildren()) {
                                if (node instanceof StackPane){
                                    if (node.getBoundsInParent().contains(event.getSceneX()-100,event.getSceneY()-100)){
                                        int rida = gp.getRowIndex(node);
                                        int veerg = gp.getColumnIndex(node);
                                        int kast;
                                        System.out.println();
                                        if(rida == 0) {
                                            kast = veerg;
                                        }
                                        else if(rida == 1) {
                                            kast = veerg + 3;
                                        }
                                        else {
                                            kast = veerg + 6;
                                        }
                                        System.out.println(kast);
                                    }
                                }
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

        Scene peaStseen = new Scene(tervitusAken(peaLava), 500, 150);
        peaLava.setTitle("Trips-Traps-Trull");
        peaLava.setScene(peaStseen);
        peaLava.setResizable(false);
        peaLava.show();

    }

    public static void main(String[] args) {
        launch();

    }
}
