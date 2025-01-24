package me.hossain.ebrahim;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.time.Duration;

public class DatePicker {
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
        driver.get("https://www.tutorialspoint.com/selenium/practice/date-picker.php");
        // Initialize WebDriverWait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Call the function with desired date and time
        selectDateTime(driver, wait, "15", "February", "2025", "10", "30", "AM");
    }

    @AfterSuite
    public void closeChromeBrowser() {
        driver.close();
    }


    // Function to convert month name to index
    public static int monthNameToIndex(String monthName) {
        switch (monthName) {
            case "January": return 0;
            case "February": return 1;
            case "March": return 2;
            case "April": return 3;
            case "May": return 4;
            case "June": return 5;
            case "July": return 6;
            case "August": return 7;
            case "September": return 8;
            case "October": return 9;
            case "November": return 10;
            case "December": return 11;
            default: return -1;
        }
    }

    public static void selectDateTime(WebDriver driver, WebDriverWait wait, String desiredDay, String desiredMonth,
                                      String desiredYear, String desiredHour, String desiredMinute, String amPm) {
        try {
            // Locate and click on the date picker input
            WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("datetimepicker1")));
            dateInput.click();

            // Select the desired year
            while (true) {
                WebElement yearInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".numInput.cur-year")));
                String displayedYear = yearInput.getAttribute("value");

                if (displayedYear.equals(desiredYear)) {
                    break;
                }

                if (Integer.parseInt(displayedYear) < Integer.parseInt(desiredYear)) {
                    WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".flatpickr-next-month")));
                    nextButton.click();
                } else {
                    WebElement prevButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".flatpickr-prev-month")));
                    prevButton.click();
                }
            }

            // Select the desired month
            while (true) {
                WebElement monthDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".flatpickr-monthDropdown-months")));
                int displayedMonth = Integer.parseInt(monthDropdown.getAttribute("value"));
                int desiredMonthIndex = monthNameToIndex(desiredMonth);

                if (displayedMonth == desiredMonthIndex) {
                    break;
                }

                if (displayedMonth < desiredMonthIndex) {
                    WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".flatpickr-next-month")));
                    nextButton.click();
                } else {
                    WebElement prevButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".flatpickr-prev-month")));
                    prevButton.click();
                }
            }

            // Select the desired day
            WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//span[contains(@class, 'flatpickr-day') and text()='" + desiredDay + "']")));
            dayElement.click();

            // Select the time
            // Set the hour
            WebElement hourInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".flatpickr-hour")));
            hourInput.clear();
            hourInput.sendKeys(desiredHour);

            // Set the minute
            WebElement minuteInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".flatpickr-minute")));
            minuteInput.clear();
            minuteInput.sendKeys(desiredMinute);

            // Set AM/PM
            WebElement amPmButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".flatpickr-am-pm")));
            if (!amPmButton.getText().equalsIgnoreCase(amPm)) {
                amPmButton.click();
            }

            // Print the selected date and time from the input field
            String selectedDateTime = dateInput.getAttribute("value");
            System.out.println("Selected Date and Time: " + selectedDateTime);

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}

