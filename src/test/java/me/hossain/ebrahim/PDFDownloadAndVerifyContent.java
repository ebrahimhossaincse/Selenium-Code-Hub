package me.hossain.ebrahim;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class PDFDownloadAndVerifyContent {
    public static WebDriver driver;
    public static String downloadFilepath = "C:\\Users\\mdebr\\OneDrive\\Documents\\Automation Projects\\Selenium_Java_Solution_Hub\\file"; // Change this path to your project's file folder

    @BeforeClass
    public void setup() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadFilepath);
        prefs.put("browser.download.manager.showWhenStarting", false);
        prefs.put("plugins.plugins_disabled", new String[]{"Chrome PDF Viewer"});
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("plugins.always_open_pdf_externally", true);

        ChromeOptions opt = new ChromeOptions();
        opt.setExperimentalOption("prefs", prefs);

        driver = new ChromeDriver(opt);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(25));
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void downloadAndVerifyPDF() throws IOException {
        driver.get("https://demo.automationtesting.in/FileDownload.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement download = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@type='button']")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", download);

        String fileName = "sampleFile.pdf";
        File downloadedFile = new File(downloadFilepath + "\\" + fileName);

        // Check if the file exists and delete it if it does
        if (downloadedFile.exists()) {
            if (downloadedFile.delete()) {
                System.out.println("Existing file deleted.");
            } else {
                System.out.println("Failed to delete existing file.");
            }
        }

        download.click();
        wait.until((WebDriver wd) -> downloadedFile.exists() && downloadedFile.length() > 0);
        PDDocument document = PDDocument.load(downloadedFile);
        PDFTextStripper stripper = new PDFTextStripper();
        String pdfText = stripper.getText(document);
        String searchText = "Get Tickets";
        if (pdfText.contains(searchText)) {
            System.out.println("Text found in the PDF.");
        } else {
            System.out.println("Text not found in the PDF.");
        }
        document.close();
    }
}
