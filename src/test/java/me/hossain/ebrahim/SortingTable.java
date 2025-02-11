package me.hossain.ebrahim;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingTable {
    WebDriver driver;

    @BeforeSuite
    public void startChromeBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.w3schools.com/howto/howto_js_sort_table.asp");
    }

    @Test
    public void sorting() {
        // Click on Sort button and fetch the List from the HTML table col1 and store in an ArrayList
        driver.findElement(By.xpath("//button[contains(text(),'Sort')]")).click();
        List<WebElement> tdList = driver.findElements(By.xpath("//table[@id='myTable']/tbody/tr/td[1]"));

        // Extract text from the table cells and store in a list
        List<String> originalList = new ArrayList<>();
        for (WebElement td : tdList) {
            originalList.add(td.getText());
        }

        // Print the original list
        System.out.println("Original List:");
        for (String item : originalList) {
            System.out.println(item);
        }

        // Create a copy of the original list and sort it
        List<String> sortedList = new ArrayList<>(originalList);
        Collections.sort(sortedList);

        // Print the sorted list
        System.out.println("\nSorted List:");
        for (String item : sortedList) {
            System.out.println(item);
        }

        // Verify if the original list is sorted
        if (originalList.equals(sortedList)) {
            System.out.println("\nThe list is correctly sorted.");
        } else {
            System.out.println("\nThe list is not correctly sorted.");
        }
    }

    @AfterSuite
    public void closeChromeBrowser() {
        driver.close();
    }
}
