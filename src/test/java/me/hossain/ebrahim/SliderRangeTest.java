package me.hossain.ebrahim;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class SliderRangeTest {
    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
        driver.manage().window().maximize();
        driver.get("https://qaplayground.dev/apps/range-slider/");
    }

    @Test
    public void testRangeSlider() {
        // Locate the slider element
        WebElement slider = driver.findElement(By.cssSelector("input[type='range']"));

        // Get the initial value of the slider
        String initialValue = slider.getAttribute("value");
        System.out.println("Initial Slider Value: " + initialValue);

        // Set the slider value to 50 using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = 50; arguments[0].dispatchEvent(new Event('input'));", slider);

        // Get the new value of the slider
        String newValue = slider.getAttribute("value");
        System.out.println("New Slider Value: " + newValue);
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
