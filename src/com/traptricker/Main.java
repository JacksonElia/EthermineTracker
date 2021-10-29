package com.traptricker;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        String minerAddress = "0fB3583c11320BB9c7F512e06ce9c3A9218568C9";
        SeleniumScrapper seleniumScrapper = new SeleniumScrapper();
        seleniumScrapper.getEthermineHTML(minerAddress);

    }
}
