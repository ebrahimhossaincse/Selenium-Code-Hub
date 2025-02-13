package me.hossain.ebrahim;

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

public class MicrosoftLogin {
    public static WebDriver driver;

    @BeforeClass
    public void setup() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
        driver.manage().window().maximize();
        driver.get("https://account.microsoft.com/account/manage-my-account");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void login() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement sign_in_to_my_account = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(text(),'Sign in')]")));
        sign_in_to_my_account.click();

        WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='loginfmt' or @type='email']")));
        email.sendKeys("youremail@gmail.com");

        WebElement next_button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='idSIButton9' or @type='submit']")));
        next_button.click();

        WebElement password = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@name='passwd' or @type='password']")));
        password.sendKeys("yourpassword");

        WebElement sign_in_button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='idSIButton9' or @type='submit']")));
        sign_in_button.click();

        Thread.sleep(5000);
    }
}