package me.hossain.ebrahim;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;

public class SliderInteraction {
    WebDriver driver;

    @BeforeSuite
    public void startChromeBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void openURL() {
        // Open the website
        driver.get("https://www.tutorialspoint.com/selenium/practice/slider.php");

        // Initialize WebDriverWait with a timeout of 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Wait until the slider is present on the page
            WebElement slider = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("ageInputId")));

            // Get slider properties
            String minValue = slider.getAttribute("min");
            String maxValue = slider.getAttribute("max");
            String currentValue = slider.getAttribute("value");
            String stepValue = slider.getAttribute("step"); // Get the step attribute

            // Provide default values if attributes are missing or empty
            int min = minValue != null && !minValue.isEmpty() ? Integer.parseInt(minValue) : 0;
            int max = maxValue != null && !maxValue.isEmpty() ? Integer.parseInt(maxValue) : 100;
            int current = currentValue != null && !currentValue.isEmpty() ? Integer.parseInt(currentValue) : min;
            int step = stepValue != null && !stepValue.isEmpty() ? Integer.parseInt(stepValue) : 1;

            // Desired value to move the slider to
            int targetValue = 30;

            // Log initial values
            System.out.println("Initial Slider Value: " + current);
            System.out.println("Min Value: " + min);
            System.out.println("Max Value: " + max);
            System.out.println("Target Value: " + targetValue);
            System.out.println("Step Value: " + step);

            // Calculate slider width
            int sliderWidth = slider.getSize().getWidth();
            System.out.println("Slider Width (pixels): " + sliderWidth);

            // Calculate the exact pixel offset needed to move the slider to the target value
            double pixelPerUnit = (double) sliderWidth / (max - min);
            int xOffset = (int) ((targetValue - current) * pixelPerUnit);
            System.out.println("Calculated xOffset: " + xOffset);

            // Initialize Actions class
            Actions action = new Actions(driver);

            // Move the slider in smaller steps to avoid overshooting
            int stepSize = 5; // Adjust this value based on the slider's sensitivity
            int steps = xOffset / stepSize;
            for (int i = 0; i < steps; i++) {
                action.dragAndDropBy(slider, stepSize, 0).perform();
                Thread.sleep(100); // Small delay to allow the slider to update
            }

            // Perform the final adjustment
            int remainingOffset = xOffset % stepSize;
            if (remainingOffset != 0) {
                action.dragAndDropBy(slider, remainingOffset, 0).perform();
            }

            // Wait for the slider to update
            Thread.sleep(500);

            // Get updated slider value
            current = Integer.parseInt(slider.getAttribute("value"));
            System.out.println("Updated Slider Value: " + current);

            // Output final value
            System.out.println("Final Slider Value: " + slider.getAttribute("value"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public void closeChromeBrowser() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}