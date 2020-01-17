package sample;

import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{



        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Automated objects");
        Controller controller = new Controller();


        VBox top = new VBox();
        HBox inputs = new HBox();
        HBox Rolls = new HBox();
        HBox Damage = new HBox();
        HBox Total = new HBox();
        HBox Errors = new HBox();
        javafx.scene.control.TextField number = new javafx.scene.control.TextField("quantity");
        number.setMinWidth(50);
        javafx.scene.control.TextField AC = new javafx.scene.control.TextField("target AC");
        AC.setMinWidth(25);
        /*ObservableList<String> options =
                FXCollections.observableArrayList(
                        "Tiny","Small", "Medium", "Large", "Huge"
                );*/

        ComboBox size = new ComboBox();
        size.getItems().add("Tiny");
        size.getItems().add("Small");
        size.getItems().add("Medium");
        size.getItems().add("Large");
        size.getItems().add("Huge");

        Button calculate = new Button("Calculate");
        calculate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                int numAttacks = 0;
                int targetAC = 0;
                String objectSize = "NOTHING";
                int[][] results;
                Errors.getChildren().clear();
                try{
                    numAttacks = Integer.valueOf(number.getCharacters().toString());
                    targetAC = Integer.valueOf(AC.getCharacters().toString());
                    objectSize = (String) size.getValue();

                } catch (Exception e){
                    Errors.getChildren().add(new Label("Put the right stuff in next time ya dingus"));
                }
                results = controller.calculateDamage(numAttacks,targetAC,objectSize);
                Rolls.getChildren().clear();
                for (int attackRoll:
                     results[0]) {
                    Rolls.getChildren().add(new Label(String.valueOf(attackRoll)+"  "));

                }

                Damage.getChildren().clear();
                for (int attackNum = 0; attackNum < numAttacks-1; attackNum++) {
                    Damage.getChildren().add(new Label(String.valueOf(results[1][attackNum])+"   "));

                }

                Total.getChildren().clear();
                Total.getChildren().add(new Label("Total damage: "+String.valueOf(results[1][numAttacks])));


            }
        });

        inputs.getChildren().addAll(number,AC,size,calculate);
        top.getChildren().addAll(inputs, Rolls, Damage, Total, Errors);

        BorderPane pane = new BorderPane();
        pane.setTop(top);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(500);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
