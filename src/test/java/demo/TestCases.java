package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
// import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.Assert;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */
    String googlesheetURL = "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform";

    public static String epochitme() {
        long epochTime = Instant.now().getEpochSecond();
        return String.valueOf(epochTime);
    }

    public static String reqDate() {
        LocalDate dateMinus7 = LocalDate.now().minusDays(7);
        String formattedDate = dateMinus7.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return formattedDate;
    }

    @Test(enabled = true)
    public void testCase01() throws InterruptedException {
        // Step-1. Navigate to this google form.
        driver.get(googlesheetURL);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Step-2.Text box_1
        WebElement nameTextField = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[@type='text' and contains(@class, 'whsOnd') and @required]")));

        nameTextField.sendKeys("Crio Learner");
        String epochivalue = epochitme();

        // Step-3.Text box_2
        WebElement Textfield_2 = driver.findElement(By.xpath("//textarea[contains(@class, 'KHxj8b')]"));
        Textfield_2.sendKeys("\"I want to be the best QA Engineer!" + epochivalue + "\"");

        // Step-4.radio button
        List<WebElement> radiobuttons = driver.findElements(By.className("nWQGrd"));
        radiobuttons.get(1).click();

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 800)");

        // Step-5.check-box
        List<String> toSelect = Arrays.asList("Java", "Selenium", "TestNG");
        List<WebElement> checkboxes = driver.findElements(By.xpath("//div[@role='checkbox']"));

        for (WebElement checkbox : checkboxes) {
            String label = checkbox.getAttribute("aria-label");
            String checked = checkbox.getAttribute("aria-checked");

            if (toSelect.contains(label) && !"true".equals(checked)) {
                checkbox.click();
            }
        }
        // Step-6.dropdown
        WebElement dropdownbutton = driver.findElement(By.xpath("//span[text()= 'Choose']"));
        dropdownbutton.click();
        WebElement select_Mr_option = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@role='option' and @data-value='Mr']")));
        select_Mr_option.click();

        // Step-7.Date field
        WebElement Datefield = driver.findElement(By.xpath("//input[@type='date']"));
        Datefield.sendKeys(reqDate());

        // Step-8.Time field
        WebElement Time_Hour = driver.findElement(By.xpath("//input[@aria-label='Hour']"));
        WebElement Time_Minute = driver.findElement(By.xpath("//input[@aria-label='Minute']"));

        Time_Hour.sendKeys("07");
        Time_Minute.sendKeys("30");

        // step-9 Submit the form
        WebElement submitButton = driver.findElement(By.xpath("//span[text()= 'Submit']"));
        submitButton.click();

        WebElement successmessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(),'your response')]")));
        // step-10 verify the success message
        String messageText = successmessage.getText();
        Assert.assertTrue(messageText.contains("Thanks for your response"), "Fail tp get success");
    }

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}