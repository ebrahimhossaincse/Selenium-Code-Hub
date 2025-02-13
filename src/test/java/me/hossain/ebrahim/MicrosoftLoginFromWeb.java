package me.hossain.ebrahim;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class MicrosoftLoginFromWeb {
    public static WebDriver driver;

    @BeforeClass
    public void setup() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
        driver.manage().window().maximize();
        driver.get("https://www.minecraft.net/en-us/login");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void login() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement sign_in_with_microsoft = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='login-form-view']/div/div[1]/div/a")));
        sign_in_with_microsoft.click();

        WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='loginfmt' or @type='email']")));
        email.sendKeys("test@gmail.com");
        email.sendKeys(Keys.ENTER);

        WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='passwd' or @type='password']")));
        password.sendKeys("test123");
        password.sendKeys(Keys.ENTER);

        Thread.sleep(5000);
    }
}