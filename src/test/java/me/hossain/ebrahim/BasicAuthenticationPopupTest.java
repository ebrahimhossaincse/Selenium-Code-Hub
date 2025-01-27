package me.hossain.ebrahim;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;

public class BasicAuthenticationPopupTest {
    WebDriver driver;

    @BeforeSuite
    public void startChromeBrowser() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void openURL() throws InterruptedException {
        // Open the website
        driver.get("https://the-internet.herokuapp.com/basic_auth");

        ((HasAuthentication) driver)
                .register(() -> new UsernameAndPassword("admin", "admin"));
        driver.get("https://the-internet.herokuapp.com/basic_auth");
        WebElement text = driver.findElement(By.xpath("//p"));
        Assert.assertEquals(text.getText(), "Congratulations! You must have the proper credentials.");

    }

    @AfterSuite
    public void closeChromeBrowser() {
        driver.close();
    }
}
