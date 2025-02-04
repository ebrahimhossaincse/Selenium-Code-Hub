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

public class ProgressionButton {
    public static WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void ProgressionButtonTest() {
        driver.get("http://uitestingplayground.com/progressbar");
        WebElement startButton = driver.findElement(By.id("startButton"));
        WebElement stopButton = driver.findElement(By.id("stopButton"));
        WebElement progressBar = driver.findElement(By.id("progressBar"));

        startButton.click();
        while (true) {
            int percentage = Integer.parseInt(progressBar.getText().replace("%", ""));

            if(percentage>=65) {
                ((JavascriptExecutor)driver).
                        executeScript("arguments[0].click()", stopButton);
                break;
            }
        }
    }
}
