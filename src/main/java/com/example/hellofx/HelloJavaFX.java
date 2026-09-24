package com.example.hellofx;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class HelloJavaFX extends Application {
    @Override
    public void start(Stage stage) {
        Label message = new Label("Welcome Joseph Chibale!");
        Button button = new Button("Start");
        Button button2 = new Button("Reset");
        button2.setOnAction(event ->
                message.setText("Great! You clicked the button.")
        );
        button2.setOnAction(event ->
                        message.setText("Welcome Joseph Chibale.")
        );
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(message, button);
        Scene scene = new Scene(layout, 500, 300);
        stage.setTitle("My First JavaFX Application - 202511697");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}