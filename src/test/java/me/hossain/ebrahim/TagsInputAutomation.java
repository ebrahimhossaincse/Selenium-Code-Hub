package me.hossain.ebrahim;

import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class TagsInputAutomation {
    private static WebDriver driver;
    private Faker faker;

    @BeforeClass
    public static void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
        driver.manage().window().maximize();
        driver.get("https://qaplayground.dev/apps/tags-input-box/");
    }

    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testTagAdditionAndRemoval() throws InterruptedException {
        int numberOfTags = 10;

        // Initialize Faker for generating random text
        faker = new Faker();

        // Verify initial number of tags
        List<WebElement> tags = driver.findElements(By.xpath("//div[@class='content']//ul/li"));
        System.out.println("Total No. of Tags: " + tags.size());

        WebElement removeAllButton = driver.findElement(By.xpath("//button[text()='Remove All']"));
        removeAllButton.click();

        // Verify number of tags after removal
        List<WebElement> tagsAfterRemoval = driver.findElements(By.xpath("//div[@class='content']//ul/li"));
        System.out.println("Number of tags after 'Remove All' action: " + tagsAfterRemoval.size());

        // Add new tags
        WebElement textField = driver.findElement(By.xpath("//input[@type='text']"));
        for (int i = 1; i <= numberOfTags; i++) {
            String randomTag = faker.lorem().characters(10, true);
            System.out.println("Adding tag: " + randomTag);
            textField.sendKeys(randomTag);
            textField.sendKeys(Keys.ENTER);
        }
        Thread.sleep(3000);

        // Explicitly wait until the tags are added to the DOM
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[@class='content']//ul/li"), 0));

        // Assert the number of tags after adding new ones
        List<WebElement> tagsAfterAdding = driver.findElements(By.xpath("//div[@class='content']//ul/li"));
        System.out.println("Number of tags after 'Add' : " + tagsAfterAdding.size());

        // Remove all tags again
        removeAllButton.click();

        Thread.sleep(3000);

    }
}
