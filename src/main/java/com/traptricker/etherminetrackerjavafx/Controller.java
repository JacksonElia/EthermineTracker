package com.traptricker.etherminetrackerjavafx;

import com.opencsv.exceptions.CsvException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
        // Makes sure the csv file exists, then checks if it can use its data for the labels
        try {
            DataStorer.checkFileExists("src/main/extras/ethermine_data.csv");
            if (!Objects.equals(DataStorer.getStoredValues("src/main/extras/ethermine_data.csv")[0], "")) {
                currentHashrateLabel.setText(DataStorer.getStoredValues("src/main/extras/ethermine_data.csv")[0]);
                reportedHashrateLabel.setText(DataStorer.getStoredValues("src/main/extras/ethermine_data.csv")[1]);
            }
            DataStorer.checkFileExists("src/main/extras/miner_address.txt");
            if ((!Objects.equals(DataStorer.getMinerAddress("src/main/extras/miner_address.txt"), ""))) {
                mineraddressTextField.setText(DataStorer.getMinerAddress("src/main/extras/miner_address.txt"));
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void trackPressed(ActionEvent e) {
        if (!Objects.equals(mineraddressTextField.getText(), "")) {
            // TODO: Add in invisible button on top of track button that takes it place and stops tracking
            trackButton.setDisable(true);
            DataStorer.checkFileExists("src/main/extras/miner_address.txt");
            DataStorer.storeMinerAddress("src/main/extras/miner_address.txt", mineraddressTextField.getText());
            Thread thread = new Thread(this::trackButtonThread);
            thread.start();
        } else {
            errorLabel.setText("Enter a miner address");
        }
    }

    @FXML
    private void resetPressed(ActionEvent e) {
        try {
            DataStorer.clearCSVFile("src/main/extras/ethermine_data.csv");
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

    @FXML
    private void hyperlinkClicked(ActionEvent e) {
        if (!Objects.equals(mineraddressTextField.getText(), "")) {
            String ethermineURL = String.format("https://ethermine.org/miners/%s/dashboard", mineraddressTextField.getText());
            try {
                Desktop.getDesktop().browse(new URI(ethermineURL));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        } else {
            errorLabel.setText("Enter a miner address");
        }
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

    private void trackButtonThread() {
        SeleniumScrapper seleniumScrapper = new SeleniumScrapper();
        String minerAddress = mineraddressTextField.getText();
        // Checks to make sure selenium was set up correctly
        if (!seleniumScrapper.setUpWebdriver(minerAddress)) {
            // Switches to the GUI thread
            Platform.runLater(() -> {
                trackButton.setDisable(false);
                errorLabel.setText("Miner Address was bad");
            });
            return;
        }

        try {
            while (true) {
                WebElement ethermineTable = seleniumScrapper.getEthermineTable();
                if (ethermineTable != null) {
                    Map<String, Map<String, String>> minerData = seleniumScrapper.getEthermineData(ethermineTable);
                    DataStorer.storeCSVData("src/main/extras/ethermine_data.csv", minerData);
                    String csvCurrentValues = DataStorer.getStoredValues("src/main/extras/ethermine_data.csv")[0];
                    String csvReportedValues = DataStorer.getStoredValues("src/main/extras/ethermine_data.csv")[1];
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
                Thread.sleep(600000);
            }
        } catch (InterruptedException e) {
            System.out.println("Sleep was interrupted");
        } catch (IOException | CsvException e) {
            System.out.println("Csv reader/writer had a problem");
        }
        // Never going to be reached
        // TODO: add stop button
    }

}