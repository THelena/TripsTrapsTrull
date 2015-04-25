import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
    static int a = 0;

    int tagastaRuut (double x, double y) {
        double külg = ruudud.get(0).getWidth();
        System.out.println(külg);
        System.out.println(ruudud.get(2).getX());

        for (int i = 0; i < ruudud.size(); i++) {
            //double x_koordinaat = ruudud.get(i).getX();
            //double y_koordinaat = ruudud.get(i).getY();
            //System.out.println(y_koordinaat);
            //System.out.println(x_koordinaat);
            //if (x >= x_koordinaat && x <= x_koordinaat + külg && y >= y_koordinaat && y <= y_koordinaat + külg) {
            //    return i;
            // }
        }
        return 10;
    }
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
                    Text tekst = new Text("Oma käigu tegemiseks vajuta soovitud ruutu.");
                    tekst.setTextAlignment(TextAlignment.CENTER);
                    tekst.setFont(new Font(20));
                    tekst.setLayoutX(100);
                    tekst.setLayoutY(50);
                    tekst.setWrappingWidth(300);
                    juur.getChildren().add(tekst);

                    Button tulemus = new Button();
                    tulemus.setText("Tulemused");
                    tulemus.setLayoutX(100);
                    tulemus.setLayoutY(450);
                    juur.getChildren().add(tulemus);

                    //Ruudustik
                    GridPane gp = new GridPane();
                    for (int i = 0; i < 9; i++) {
                        StackPane kuhi = new StackPane();
                        Text ruudu_tekst = new Text(" ");
                        ruudu_tekst.setFont(new Font(40));
                        ruudu_tekstid.add(ruudu_tekst);
                        Rectangle ruut = new Rectangle(100, 100);
                        ruut.setFill(Color.WHITE);
                        ruut.setStroke(Color.BLACK);
                        ruut.setArcWidth(20);
                        ruut.setArcHeight(20);
                        ruut.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent event) {
                                System.out.println(event.getX());
                                System.out.println(event.getY());
                                System.out.println(tagastaRuut(event.getX(), event.getY()));
                            }
                        });
                        ruudud.add(ruut);
                        kuhi.getChildren().addAll(ruut,ruudu_tekst);
                        if (i > 2 && i < 6) {
                            gp.add(kuhi, i % 3, 1);
                        }
                        else if (i < 3) {
                            gp.add(kuhi, i, 0);
                        }
                        else {
                            gp.add(kuhi, i % 3 ,2);
                        }
                    }


                    gp.setLayoutX(100);
                    gp.setLayoutY(100);
                    juur.getChildren().add(gp);



                    //--------------------------------------------------------------
                    Scene mänguStseen = new Scene(juur, 500, 500);
                    mäng.setScene(mänguStseen);
                    mäng.show();
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
