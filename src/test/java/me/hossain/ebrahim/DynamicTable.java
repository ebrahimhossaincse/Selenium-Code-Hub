package me.hossain.ebrahim;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.List;

public class DynamicTable {
    WebDriver driver;

    @BeforeSuite
    public void startChromeBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testDynamicTable() throws InterruptedException {
        String expected_name = "Hulk";
        // Open the website
        driver.get("https://qaplayground.dev/apps/dynamic-table/");
        List<WebElement> table_rows = driver.findElements(By.xpath("//tbody[@id=\"tbody\"]/tr"));
        for(int i=1; i<=table_rows.size(); i++) {
            WebElement actual_name = driver.findElement(By.xpath("//tbody[@id=\"tbody\"]/tr[" + i + "]/td/div[1]/div[2]/div[1]"));
            if(actual_name.getText().equalsIgnoreCase(expected_name)) {
                WebElement status = driver.findElement(By.xpath("//tbody[@id=\"tbody\"]/tr[" + i + "]/td[2]/span"));
                WebElement real_name = driver.findElement(By.xpath("//tbody[@id=\"tbody\"]/tr[" + i + "]/td[3]/span"));
                System.out.println("Status: " + status.getText());
                System.out.println("Real Name: " + real_name.getText());
            }
        }
    }

    @AfterSuite
    public void closeChromeBrowser() {
        driver.close();
    }
}
