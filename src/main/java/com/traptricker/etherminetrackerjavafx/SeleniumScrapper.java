package com.traptricker.etherminetrackerjavafx;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeleniumScrapper {

    public static ChromeDriver driver;

    public SeleniumScrapper() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // Launches the driver in headless mode
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors","--disable-extensions","--no-sandbox","--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    // Returns a bool based on if it found the website correctly
    public boolean setUpWebdriver(String minerAddress) {
        String ethermineURL = String.format("https://ethermine.org/miners/%s/dashboard", minerAddress);
        driver.get(ethermineURL);
        try {
            // Explicitly waits until it sees the slider
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='slider round']")));
            // Turns on auto refreshing for the page
            driver.findElement(By.xpath("//div[@class='slider round']")).click();
            return true;
        } catch (TimeoutException tex) {
            System.out.println("Couldn't find slider, miner address was probably bad or ethermine is down.");
            driver.quit();
            return false;
        } catch (ElementClickInterceptedException eciex) {
            System.out.println("Slider was intercepted, miner address was probably bad.");
            driver.quit();
            return false;
        }
    }

    // Gets the element for the table containing active miners
    public WebElement getEthermineTable() {
        List<WebElement> searchList = driver.findElements(By.xpath("//div[@class='active table-container']//tbody"));
        // Confirms that there are active miners, then gets each of them (isEmpty doesn't work)
        if (searchList.get(0).getText().split(" ").length > 1) {
            return searchList.get(0);
        }
        return null;
    }

    // Gets the values from the ethermine table
    public Map<String, Map<String, String>> getEthermineData(WebElement ethermineTable) {
        // Turns the table into an array
        String[] search = ethermineTable.getText().split("\n");
        Map<String, Map<String, String>> minerDataDict = new HashMap<>();
        for (String miner : search) {
            // Turns each row in the table into an array
            String[] minerData = miner.split(" ");
            // Turns each row in the table into a Hashmap (Basically a Dictionary)
            Map<String, String> minerHashrates = new HashMap<>();
            minerHashrates.put("Reported Hashrate", minerData[1]);
            minerHashrates.put("Current Hashrate", minerData[2]);
            minerDataDict.put(minerData[0], minerHashrates);
        }
        return minerDataDict;
    }
}
