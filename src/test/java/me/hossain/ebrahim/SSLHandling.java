package me.hossain.ebrahim;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class SSLHandling {
    public static WebDriver driver;

    public static void setupDriverWithOutHandlingSSL() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
        driver.manage().window().maximize();
    }

    public static void setupDriverWithHandlingSSL() {
        //Create instance of ChromeOptions Class
        ChromeOptions handlingSSL = new ChromeOptions();

        //Using the accept insecure cert method with true as parameter to accept the untrusted certificate
        handlingSSL.setAcceptInsecureCerts(true);

        //Creating instance of Chrome driver by passing reference of ChromeOptions object
        driver = new ChromeDriver(handlingSSL);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test(priority = 0)
    public void withoutSSL() throws InterruptedException {
        setupDriverWithOutHandlingSSL();
        //Launching the URL
        driver.get("https://expired.badssl.com/");
        Thread.sleep(2000);
        driver.quit();
    }

    @Test(priority = 1)
    public void withSSL() throws InterruptedException {
        setupDriverWithHandlingSSL();
        //Launching the URL
        driver.get("https://expired.badssl.com/");
        Thread.sleep(2000);
        System.out.println("The page title is : " +driver.getTitle());
        driver.quit();
    }
}
