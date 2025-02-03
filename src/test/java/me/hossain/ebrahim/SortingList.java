package me.hossain.ebrahim;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

/*
𝐄𝐱𝐩𝐞𝐜𝐭𝐞𝐝 𝐨𝐫𝐝𝐞𝐫 𝐨𝐟 𝐧𝐚𝐦𝐞𝐬 𝐢𝐧 𝐋𝐢𝐬𝐭 𝐨𝐫𝐝𝐞𝐫:
        position: 1, name: "Jeff Bezos"
        position: 2, name: "Bill Gates"
        position: 3, name: "Warren Buffett"
        position: 4, name: "Bernard Arnault"
        position: 5, name: "Carlos Slim Helu"
        position: 6, name: "Amancio Ortega"
        position: 7, name: "Larry Ellison"
        position: 8, name: "Mark Zuckerberg"
        position: 9, name: "Michael Bloomberg"
 */

public class SortingList {
    public static WebDriver driver;

    @BeforeClass
    public void setup() {

        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
        driver.manage().window().maximize();
        driver.get("https://qaplayground.dev/apps/sortable-list/");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void SortListOrder() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String[] topList = {
                "Jeff Bezos",
                "Bill Gates",
                "Warren Buffett",
                "Bernard Arnault",
                "Carlos Slim Helu",
                "Amancio Ortega",
                "Larry Ellison",
                "Mark Zuckerberg",
                "Michael Bloomberg"
        };

        Actions actions = new Actions(driver);
        int counter = 1; // Start counter at 1

        for (String name : topList) {
            WebElement draggableItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), '" + name + "')]")));
            WebElement targetPosition = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), '" + counter + "')]")));
            actions.dragAndDrop(draggableItem, targetPosition).build().perform();
            counter++; // Increment counter
        }

        driver.findElement(By.id("check")).click();

        for (String name : topList) {
            WebElement listItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), '" + name + "')]")));
            String color = listItem.getCssValue("color");
            Assert.assertEquals(color, "rgba(58, 227, 116, 1)");
        }
    }
}
