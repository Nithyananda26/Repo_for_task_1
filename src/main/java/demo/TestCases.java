package demo;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;
// import org.testng.annotations.Test;

public class TestCases {
    ChromeDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");
        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");
        driver = new ChromeDriver(options);

        // Set browser to maximize and wait
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    public void testCase01() throws InterruptedException {
        System.out.println("Start Test case: testCase01");
        driver.get("https://leetcode.com/");

        if (driver.getCurrentUrl().contains("leetcode")) {
            System.out.println("URL verication done TestCase-1 pass");
        }
        Thread.sleep(5000);
    }

    public void testCase02() throws InterruptedException {
        System.out.println("Start Test case: testCase02");
        driver.get("https://leetcode.com/");

        WebElement questionsLink = driver.findElement(By.xpath("//p[text()='View Questions ']"));
        questionsLink.click();

        if (driver.getCurrentUrl().contains("problemset")) {
            System.out.println("verifed problem set page is success");
        }

        Thread.sleep(5000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 500)");

        List<WebElement> rows = driver.findElements(By.xpath("//div[@role='row']"));

        int questionCount = 0;

        for (int i = 2; i < rows.size(); i++) {
            if (questionCount == 5) {
                break;
            }

            WebElement row = rows.get(i);
            List<WebElement> cells = row.findElements(By.xpath(".//div[@role='cell']"));

            for (int j = 1; j < cells.size(); j++) {
                System.out.println(cells.get(j).getText());
            }
            questionCount++;
            Thread.sleep(2000);
        }
    }

    public void testCase03() throws InterruptedException {
        System.out.println("Start Test case: testCase03");
        driver.get("https://leetcode.com/");
        WebElement questionsLink = driver.findElement(By.xpath("//p[text()='View Questions ']"));
        questionsLink.click();

        WebElement firstProblem = driver.findElement(By.xpath("//a[text()='Two Sum']"));
        firstProblem.click();

        if (driver.getCurrentUrl().contains("two-sum")) {
            System.out.println("URL verification is done for firstProblem ");
        }
    }

    public void testCase04() throws InterruptedException {
        System.out.println("Start Test case: testCase04");
        driver.get("https://leetcode.com/");
        WebElement questionsLink = driver.findElement(By.xpath("//p[text()='View Questions ']"));
        questionsLink.click();

        WebElement firstProblem = driver.findElement(By.xpath("//a[text()='Two Sum']"));
        firstProblem.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement submissionButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-layout-path= '/ts0/tb3']")));
        submissionButton.click();

        WebElement registerButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[text()='Register or Sign In']")));

       
        String message = registerButton.getText();
        if (message.contains("Register or Sign In")) {
            System.out.println("Verify that \"Register or Sign In\" is displaying");
        }
    }
}
