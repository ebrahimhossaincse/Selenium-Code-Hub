package me.hossain.ebrahim;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;

public class ShadowDOM {
    public static WebDriver driver;

    @BeforeClass
    public void setup(){
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
        driver.manage().window().maximize();
        driver.get("https://uitestingplayground.com/shadowdom");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

    @Test
    public void ShadowDOMTest() throws IOException, UnsupportedFlavorException {
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
        SearchContext shadowDOMContext = driver.findElement(By.tagName("guid-generator")).getShadowRoot();
        shadowDOMContext.findElement(By.cssSelector(".button-generate")).click();
        WebElement element = shadowDOMContext.findElement(By.cssSelector(".edit-field"));

        String inputTextString = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].value;", element);
        shadowDOMContext.findElement(By.cssSelector("#buttonCopy")).click();

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Object clipData = clipboard.getData(DataFlavor.stringFlavor);
        if(clipData.equals(inputTextString))
        {
            System.out.println("Test is passed..");
        }
        else{
            System.out.println("Test is failed..");
        }
    }
}
