package me.hossain.ebrahim;

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

public class NestedIframeAutomation {
    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @Test
    public  void nestedIframe() {
        try {
            // Open the target URL
            driver.get("https://qaplayground.dev/apps/iframe/");

            // Wait for the outer iframe to be visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(25));
            WebElement outerIframe = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("frame1")));

            // Switch to the outer iframe
            driver.switchTo().frame(outerIframe);

            // Wait for the inner iframe to be visible within the outer iframe
            WebElement innerIframe = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("frame2")));

            // Switch to the inner iframe
            driver.switchTo().frame(innerIframe);

            // Now you can interact with elements inside the inner iframe
            // For example, let's click on a button inside the inner iframe
            WebElement clickm_me_button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Click Me')]")));
            clickm_me_button.click();

            Thread.sleep(3000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
