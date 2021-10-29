package com.traptricker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class SeleniumScrapper {

    public List<String> getEthermineHTML(String minerAddress) throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();

        String ethermineURL = String.format("https://ethermine.org/miners/%s/dashboard", minerAddress);
        driver.get(ethermineURL);
        // Waits to make sure the web page has loaded
        Thread.sleep(10000);

        // Turns on auto refreshing for the page
        driver.findElement(By.xpath("//div[@class='slider round']")).click();
        List<WebElement> searchList = driver.findElements(By.xpath("//div[@class='active table-container']//tbody"));

        // Confirms that there are active miners, then gets each of them
        if (!searchList.isEmpty()) {
            String search = searchList.get(0).getText();
            return List.of(search.split("\n"));
        }
        return null;
    }
}
