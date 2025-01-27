package me.hossain.ebrahim;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class DisabledFieldInputTest {
    WebDriver driver;

    @BeforeSuite
    public void startChromeBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void openURL() throws InterruptedException {
        try {
            driver.get("https://seleniumpractise.blogspot.com/2016/09/how-to-work-with-disable-textbox-or.html");

            // JavaScript to enable the disabled input field
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.getElementById('pass').removeAttribute('disabled')");

            // Input value into the now enabled field
            WebElement passwordField = driver.findElement(By.id("pass"));
            passwordField.sendKeys("Hello");

            // Optionally, validate the input
            String enteredText = passwordField.getAttribute("value");
            System.out.println(enteredText);

        } catch (Exception e) {
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

    }

    @AfterSuite
    public void closeChromeBrowser() {
        driver.close();
    }
}
