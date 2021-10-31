package com.traptricker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;

public class SeleniumScrapper {

    public static WebElement getEthermineTable(String minerAddress) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();

        String ethermineURL = String.format("https://ethermine.org/miners/%s/dashboard", minerAddress);
        driver.get(ethermineURL);
        // Waits to make sure the web page has loaded
        Thread.sleep(5000);

        // Turns on auto refreshing for the page
        driver.findElement(By.xpath("//div[@class='slider round']")).click();

        List<WebElement> searchList = driver.findElements(By.xpath("//div[@class='active table-container']//tbody"));
        // Confirms that there are active miners, then gets each of them (isEmpty doesn't work)
        if (searchList.get(0).getText().split(" ").length > 1) {
            return searchList.get(0);
        }
        return null;
    }

    public static List<Map<String, Map<String, Float>>> getEthermineData(WebElement ethermineTable) {
        // Turns the table into an array
        String[] search = ethermineTable.getText().split("\n");
        List<Map<String, Map<String, Float>>> minerList = new ArrayList<>();
        for (String miner : search) {
            // Turns each row in the table into an array
            String[] minerData = miner.split(" ");
            // Turns each row in the table into a Hashmap (Basically a Dictionary)
            Map<String, Map<String, Float>> minerDataDict = new HashMap<>();
            Map<String, Float> minerHashrates = new HashMap<>();
            minerHashrates.put("Reported Hashrate", Float.parseFloat(minerData[1]));
            minerHashrates.put("Current Hashrate", Float.parseFloat(minerData[2]));
            minerDataDict.put(minerData[0], minerHashrates);
            minerList.add(minerDataDict);
        }
        return minerList;
    }
}
