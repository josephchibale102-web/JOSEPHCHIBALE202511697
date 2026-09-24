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
        Label message = new Label("Welcome to Joseph Chibale!");
        Button startbutton = new Button("Start");
        Button resetbutton = new Button("Reset");
        startbutton.setOnAction(event ->
                message.setText("Great! You clicked the button.")
        );
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(message, startbutton);
        Scene scene = new Scene(layout, 500, 300);
        stage.setTitle("My First JavaFX Application - 202511697");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}