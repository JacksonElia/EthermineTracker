package com.traptricker;

import org.openqa.selenium.chrome.ChromeDriver;

public class Main {


    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://www.google.com");

    }
}
