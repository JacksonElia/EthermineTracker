package com.traptricker;

import org.openqa.selenium.WebElement;

public class Main {

    public static void main(String[] args) throws Exception {

        String minerAddress = "0fB3583c11320BB9c7F512e06ce9c3A9218568C9";
        WebElement ethermineTable = SeleniumScrapper.getEthermineTable(minerAddress);
        if (ethermineTable != null) {
            System.out.println(SeleniumScrapper.getEthermineData(ethermineTable));
            CSVStorer.csvWriter("ethermine_data.csv");
        } else {
            System.out.println("No workers found");
        }
    }
}
