package com.traptricker;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class SeleniumScrapper {

    public void getEthermineHTML(String minerAddress) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();

        String ethermineURL = String.format("https://ethermine.org/miners/%s/dashboard", minerAddress);
        driver.get(ethermineURL);
        // Waits to make sure the web page has loaded
        Thread.sleep(10000);
        String search = driver.findElement(By.xpath("//div[@class='active table-container']//tbody")).getText();

        System.out.println(search);

        // Turns on auto refreshing for the page
        driver.findElement(By.xpath("//div[@class='slider round']")).click();
    }
}
