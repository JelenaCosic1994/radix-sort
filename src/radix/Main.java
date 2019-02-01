package radix;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage){
        primaryStage.setTitle("Radix Sort");
        Group root = new Group();

        Label lbl = new Label("Unesite dekadne brojeve(pozitivne ili negativne)\n za sortiranje odvojene zarezom:");

        TextField textField = new TextField();
        textField.setMinWidth(150);
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("-?\\d+(,-?\\d+)+")) {
                    textField.setText(newValue.replaceAll("[^-\\d,]", ""));
                }
            }
        });

        Button btnSort =  new Button("Sortiraj");
        btnSort.setAlignment(Pos.CENTER);
        btnSort.setOnAction(e ->{
            if(!textField.getText().equals("")) {
                int[] numbers = Arrays.stream(textField.getText().split(",")).mapToInt(Integer::parseInt).toArray();

                if (numbers.length != 0) {
                    ArrayList<Integer> nums = new ArrayList<>();
                    for (int i : numbers)
                        nums.add(i);

                    Radix radix = new Radix(nums);
                    radix.sort();
                    root.getChildren().clear();
                    root.getChildren().addAll(radix);

                    primaryStage.setScene(new Scene(root, 560, 450, Color.BLUEVIOLET));
                    primaryStage.show();
                }
            }

        });

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10, 50, 50, 50));
        vbox.setSpacing(10);
        vbox.getChildren().addAll(lbl, textField, btnSort);

        Scene sceneHome = new Scene(vbox, 400, 150);
        primaryStage.setScene(sceneHome);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
