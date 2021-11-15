package com.traptricker;

import org.openqa.selenium.WebElement;

import java.util.Map;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting program...");
        String minerAddress = "0fB3583c11320BB9c7F512e06ce9c3A9218568C9";
        SeleniumScrapper seleniumScrapper = new SeleniumScrapper(minerAddress);
        while (true) {
            WebElement ethermineTable = seleniumScrapper.getEthermineTable();
            if (ethermineTable != null) {
                Map<String, Map<String, String>> minerData = seleniumScrapper.getEthermineData(ethermineTable);
                CSVStorer.storeCSVData("ethermine_data.csv", minerData);
            } else {
                System.out.println("No workers found");
            }
            // Sleeps for 10 minutes
            Thread.sleep(600000);
        }
    }
}
