package demo;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Level;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases {
    ChromeDriver driver;
    private WebDriverWait wait;

    public TestCases() {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().timeout(30).setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.INFO);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);

        // Connect to the chrome-window running on debugging port
        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Set browser to maximize and wait
        driver.manage().window().maximize();
    }

    public void endTest() {
        System.out.println("End Test: TestCases");
        // driver.close();
        // driver.quit();

    }

    public void testCase01() {
        try {
            System.out.println("Start Test case: testCase01");
            driver.get("https://calendar.google.com/");
            String currentURL = driver.getCurrentUrl();
            String expectedTitle = "calendar.";
            if (currentURL.contains(expectedTitle)) {
                System.out.println("The URL contains the expected URL:" + " " + expectedTitle);
            } else {
                System.out.println("The URL does not contain the expected URL" + " " + expectedTitle);
            }
        } catch (UnhandledAlertException ex) {
            Alert alert = driver.switchTo().alert();
            System.out.println("Alert text: " + alert.getText());
            alert.dismiss();
        }

        finally {
            System.out.println("End Test case: testCase01");
        }

    }

    /**
     * @throws InterruptedException
     */
    public void testCase02() throws InterruptedException {

        System.out.println("Start Test case: testCase02");
        driver.get("https://calendar.google.com/");
        WebElement monthview = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//span[@class='VfPpkd-vQzf8d' and text()='Month']")));
        String monthviewText = monthview.getText();
        System.out.println("Month view button text:" + monthviewText);
        monthview.click();
        WebElement month = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@jsname='K4r5Ff' and contains(text(), 'Month')]")));
        String monthText = month.getText();
        System.out.println("Month button text:" + monthviewText);
        month.click();
        WebElement date = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-datekey='27722']")));
        date.click();
        Thread.sleep(2000);
        WebElement task = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//button[@id='tabTask']")));
        task.click();
        Thread.sleep(2000);
        WebElement title = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Add title and time']")));
        title.clear();
        title.sendKeys("Crio INTV Task Automation");
        WebElement description = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@placeholder='Add description']")));
        description.sendKeys("Crio INTV Calendar Task Automation");
        WebElement save = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
                "//button[@class='VfPpkd-LgbsSe VfPpkd-LgbsSe-OWXEXe-k8QpJ VfPpkd-LgbsSe-OWXEXe-dgl2Hf nCP5yc AjY5Oe DuMIQc LQeN7 pEVtpe']")));
        save.click();
        System.out.println("End Test case: testCase02");

    }

    /**
     * @throws InterruptedException
     */
    public void testCase03() throws InterruptedException {
        System.out.println("Start Test case: testCase03");
        driver.get("https://calendar.google.com/");
        Thread.sleep(3000);
        try {
            Alert alert = driver.switchTo().alert();
            alert.dismiss();
        } catch (NoAlertPresentException e) {

        }
        WebElement task = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(
                        "//div[@class='vEJ0bc elYzab-cXXICe-Hjleke NlL62b Po94xd']//span[text()='Crio INTV Task Automation']")));
        task.click();
        Thread.sleep(2000);
        WebElement edit = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Edit task']")));
        edit.click();
        WebElement description = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//textarea[@placeholder='Add description']")));

        description.clear();
        description.sendKeys(
                "Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application");
        Thread.sleep(2000);
        WebElement save = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//span[@class='VfPpkd-vQzf8d' and contains(text(), 'Save')]")));
        save.click();
        Thread.sleep(2000);
        WebElement updatedTask = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath(
                        "//div[@class='vEJ0bc elYzab-cXXICe-Hjleke NlL62b Po94xd']//span[text()='Crio INTV Task Automation']")));
        updatedTask.click();
        Thread.sleep(2000);
        WebElement updatedText = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[@class='toUqff D29CYb']")));

        String actualDescription = updatedText.getText();
        actualDescription = actualDescription.trim().replace("Description:", "");

        if (actualDescription.contains(
                "Crio INTV Task Automation is a test suite designed for automating various tasks on the Google Calendar web application")) {
            System.out.println("The updated description is displayed");
        } else {
            System.out.println("The updated description is not  displayed");
        }

        System.out.println("End Test case: testCase03");

    }

    /**
     * @return
     * @throws InterruptedException
     */
    public void testCase04() throws InterruptedException {
        System.out.println("Start Test case: testCase04");
        driver.get("https://calendar.google.com/");
        Thread.sleep(3000);
        WebElement task = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath(
                        "//div[@class='vEJ0bc elYzab-cXXICe-Hjleke NlL62b Po94xd']//span[text()='Crio INTV Task Automation']")));
        task.click();
        Thread.sleep(2000);
        WebElement title = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toUqff ']//span")));
        String titleDisplayed = title.getText();
        System.out.println("The title is:" + titleDisplayed);
        String expectedTitle = "Crio INTV Task Automation";
        if (titleDisplayed.equals(expectedTitle)) {
            System.out.println("Verification of Title is successfull");
        } else {
            System.out.println("Verification of Title is not  successfull");
        }

        WebElement delete = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@aria-label='Delete task']")));

        delete.click();
        WebElement taskDeletedMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='VYTiVb' and text()='Task deleted']")));
    

        // String expectedText = "Task deleted";

        if (taskDeletedMessage.getText().trim().equalsIgnoreCase("Task deleted")) {
            System.out.println("Task deletion was successful. 'Task deleted' message is displayed.");
        } else {
            System.out.println("Task deletion was not successful or 'Task deleted' message is not displayed. Actual text: " + taskDeletedMessage.getText());
        }

        System.out.println("End Test case: testCase04");

    }

}
