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

public class TooltipVerification {
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
        driver.get("https://www.tutorialspoint.com/selenium/practice/tool-tips.php");

        // Initialize WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // Verify the tooltip for the "Tooltip on top" button
        WebElement tooltipTopButton =  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Tooltip on top']")));
        verifyTooltip(driver, wait, tooltipTopButton, "Tooltip on top");

        // Verify the tooltip for the "Tooltip on right" button
        WebElement tooltipRightButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Tooltip on right']")));
        verifyTooltip(driver, wait, tooltipRightButton, "Tooltip on right");

        // Verify the tooltip for the "Tooltip on bottom" button
        WebElement tooltipBottomButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Tooltip on bottom']")));
        verifyTooltip(driver, wait, tooltipBottomButton, "Tooltip on bottom");

        // Verify the tooltip for the "Tooltip on left" button
        WebElement tooltipLeftButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Tooltip on left']")));
        verifyTooltip(driver, wait, tooltipLeftButton, "Tooltip on left");
    }

    private static void verifyTooltip(WebDriver driver,WebDriverWait wait, WebElement element, String expectedTooltip) throws InterruptedException {
        // Create an instance of Actions class
        Actions actions = new Actions(driver);

        // Hover over the element to trigger the tooltip
        actions.moveToElement(element).perform();

        // Wait for the tooltip to appear (adjust the sleep time if necessary)
        Thread.sleep(1000);

        // Locate the tooltip element
        WebElement tooltip = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".tooltip-inner")));

        // Get the tooltip text
        String actualTooltip = tooltip.getText();

        // Verify the tooltip text
        if (actualTooltip.equals(expectedTooltip)) {
            System.out.println("Tooltip verified successfully: " + actualTooltip);
        } else {
            System.out.println("Tooltip verification failed. Expected: " + expectedTooltip + ", but got: " + actualTooltip);
        }
    }

    @AfterSuite
    public void closeChromeBrowser() {
        driver.close();
    }

}
