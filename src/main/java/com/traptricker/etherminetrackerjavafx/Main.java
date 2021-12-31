package com.traptricker.etherminetrackerjavafx;

import com.opencsv.exceptions.CsvException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends javafx.application.Application {

    public static void main(String[] args) throws IOException, InterruptedException, CsvException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        Scene scene = new Scene(root);

        Image icon = new Image("file:src/main/extras/logo.png");
        stage.getIcons().add(icon);
        stage.setTitle("Ethermine Tracker");
        // Gets the css string and adds it to the scene
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

}
