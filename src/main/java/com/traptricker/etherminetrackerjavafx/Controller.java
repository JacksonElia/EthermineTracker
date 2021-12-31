package com.traptricker.etherminetrackerjavafx;

import com.opencsv.exceptions.CsvException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Label currentHashrateLabel;
    @FXML
    Label reportedHashrateLabel;
    @FXML
    TextField mineraddressTextField;
    @FXML
    Hyperlink ethermineLink;
    @FXML
    Label errorLabel;
    @FXML
    Button trackButton;
    @FXML
    Button resetButton;

    // Is called when everything is loaded, like viewDidLoad
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTextField();
        try {
            if (!Objects.equals(CSVStorer.getStoredValues("src/main/extras/ethermine_data.csv")[0], "")) {
                currentHashrateLabel.setText(CSVStorer.getStoredValues("src/main/extras/ethermine_data.csv")[0]);
                reportedHashrateLabel.setText(CSVStorer.getStoredValues("src/main/extras/ethermine_data.csv")[1]);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void trackPressed(ActionEvent e) {
        // TODO: Add in invisible button on top of track button that takes it place and stops tracking
        trackButton.setDisable(true);
        errorLabel.setText("Miner Address was bad");
        Thread thread = new Thread(this::trackEthermine);
        thread.start();
    }

    @FXML
    private void resetPressed(ActionEvent e) {
        try {
            CSVStorer.clearCSVFile("src/main/extras/ethermine_data.csv");
            currentHashrateLabel.setText("No Data");
            reportedHashrateLabel.setText("No Data");

        } catch (IOException | CsvException ex) {
            ex.printStackTrace();
        }
    }

    // Clicks the track button when the user presses enter in the text field
    @FXML
    private void textFieldReturn(ActionEvent e) {
        trackButton.fire();
    }

    private void trackEthermine() {
        // TODO: Launch Selenium in windowless mode
        // String minerAddress = "0fB3583c11320BB9c7F512e06ce9c3A9218568C9";
        SeleniumScrapper seleniumScrapper = new SeleniumScrapper();
        String minerAddress = mineraddressTextField.getText();
        // Checks to make sure selenium was set up correctly
        if (!seleniumScrapper.setUpWebdriver(minerAddress)) {
            //Switches to the GUI thread
            Platform.runLater(() -> {
                trackButton.setDisable(false);
            });
            Platform.runLater(() -> {
                errorLabel.setText("Miner Address was bad");
            });
            return;
        }

        try {
            while (true) {
                WebElement ethermineTable = seleniumScrapper.getEthermineTable();
                if (ethermineTable != null) {
                    Map<String, Map<String, String>> minerData = seleniumScrapper.getEthermineData(ethermineTable);
                    CSVStorer.storeCSVData("src/main/extras/ethermine_data.csv", minerData);
                    String csvCurrentValues = CSVStorer.getStoredValues("src/main/extras/ethermine_data.csv")[0];
                    String csvReportedValues = CSVStorer.getStoredValues("src/main/extras/ethermine_data.csv")[1];
                    Platform.runLater(() -> {
                        currentHashrateLabel.setText(csvCurrentValues);
                        reportedHashrateLabel.setText(csvReportedValues);
                    });
                } else {
                    Platform.runLater(() -> {
                        errorLabel.setText("No workers found");
                    });
                }
                // Sleeps for 10 minutes
                Thread.sleep(6000);
            }
        } catch (InterruptedException e) {
            System.out.println("Sleep was interrupted");
        } catch (IOException | CsvException e) {
            System.out.println("Csv reader/writer had a problem");
        }
        // Never going to be reached
        // TODO: add stop button
    }

    // Stops the user from entering spaces in the text field
    private void setupTextField() {
        mineraddressTextField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().equals(" ")) {
                change.setText("");
            }
            return change;
        }));
    }

}