package me.hossain.ebrahim;

import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class GoogleSearch {

    private static WebDriver driver;
    private Faker faker;

    @BeforeClass
    public static void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
        driver.manage().window().maximize();
        driver.get("https://www.google.com");
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void fetchLongestAndShortestResult() {
        try {

            // Enter 'Achieve Test Prep' in the search box
            WebElement searchBox = driver.findElement(By.name("q"));
            searchBox.clear();
            searchBox.sendKeys("Achieve Test");
            Thread.sleep(2000);

            // Wait for the suggestion list to be visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("wM6W7d")));

            // Find all the suggestion elements
            List<WebElement> suggestions = driver.findElements(By.className("wM6W7d"));

            if (suggestions.isEmpty()) {
                System.out.println("No suggestions found.");
            } else {
                // Print the text of each suggestion
                System.out.println("Suggestions founds: ");
                for (WebElement suggestion : suggestions) {
                    System.out.println(suggestion.getText());
                }

                // Initialize longest and shortest suggestions
                String longestSuggestion = "";
                String shortestSuggestion = null;

                for (WebElement suggestion : suggestions) {
                    String text = suggestion.getText();
                    if (text != null && !text.isEmpty()) {
                        if (shortestSuggestion == null || text.length() < shortestSuggestion.length()) {
                            shortestSuggestion = text;
                        }
                        if (text.length() > longestSuggestion.length()) {
                            longestSuggestion = text;
                        }
                    }
                }

                // Print the longest and shortest suggestions

                System.out.println("Longest suggestion: " + (longestSuggestion != null ? longestSuggestion : "None"));
                System.out.println("Shortest suggestion: " + (shortestSuggestion != null ? shortestSuggestion : "None"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}