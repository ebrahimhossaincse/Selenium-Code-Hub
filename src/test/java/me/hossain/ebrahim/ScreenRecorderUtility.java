package me.hossain.ebrahim;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.monte.media.Format;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.time.Duration;

import static org.monte.media.FormatKeys.MediaTypeKey;
import static org.monte.media.FormatKeys.MIME_QUICKTIME;
import static org.monte.media.FormatKeys.MimeTypeKey;
import static org.monte.media.FormatKeys.MediaType;
import static org.monte.media.VideoFormatKeys.*;

public class ScreenRecorderUtility {

    private WebDriver driver;
    private ScreenRecorder screenRecorder;

    @BeforeSuite
    public void setUp() throws Exception {
        // Initialize WebDriver (e.g., ChromeDriver)
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
        driver.manage().window().maximize();
        driver.get("https://qaplayground.dev/apps/sortable-list/");

        // Start screen recording
        screenRecorder = startRecording("test-recording");
    }

    @Test
    public void testScreenRecording() throws Exception {
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

    @AfterSuite
    public void tearDown() throws Exception {
        // Quit WebDriver
        if (driver != null) {
            driver.quit();
        }
        // Stop screen recording if it's still running
        if (screenRecorder != null && screenRecorder.getState() == ScreenRecorder.State.RECORDING) {
            stopRecording(screenRecorder);
        }
    }

    public static ScreenRecorder startRecording(String fileName) throws Exception {
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();

        // Define output directory
        File outputDir = new File("./test-recordings");
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // Configure ScreenRecorder
        ScreenRecorder screenRecorder = new ScreenRecorder(gc,
                gc.getBounds(),
                new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_QUICKTIME), // Use QuickTime format
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_QUICKTIME_JPEG,
                        CompressorNameKey, ENCODING_QUICKTIME_JPEG,
                        DepthKey, 24, FrameRateKey, Rational.valueOf(15),
                        QualityKey, 1.0f,
                        KeyFrameIntervalKey, 15 * 60),
                new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black",
                        FrameRateKey, Rational.valueOf(30)),
                null, outputDir);

        screenRecorder.start();
        return screenRecorder;
    }

    public static void stopRecording(ScreenRecorder screenRecorder) throws Exception {
        if (screenRecorder != null && screenRecorder.getState() == ScreenRecorder.State.RECORDING) {
            screenRecorder.stop();
        }
    }
}
