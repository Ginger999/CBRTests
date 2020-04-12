/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author Ginger
 */
public class TestBase {

    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;
    
    public static int timeI;
    public static int timeW;

    public static String baseURL;
   

    public TestBase() {
    }

    public static boolean isElementPresent(WebDriver driver, By locator) {
        return driver.findElements(locator).size() > 0;
    }

    public static boolean isElementPresent2(WebDriver driver, By locator) {
        try {
            driver.manage().timeouts().implicitlyWait(timeI, TimeUnit.SECONDS);
            return driver.findElements(locator).size() > 0;
        } finally {
            driver.manage().timeouts().implicitlyWait(timeI, TimeUnit.SECONDS);
        }
    }

    @BeforeClass
    public static void setUpClass() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        baseURL = "https://dns-shop.ru";
        timeI = 45;
        timeW = 45;
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void start() {
        if (tlDriver.get() != null) {
            driver = tlDriver.get();
            wait = new WebDriverWait(driver, 300);
            actions = new Actions(driver);
            return;
        }

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("unexpectedAlertBehaviour", "dismiss");
        driver = new ChromeDriver(caps);
        //driver = new ChromeDriver();

        tlDriver.set(driver);
        driver.manage().timeouts().implicitlyWait(timeI, TimeUnit.MILLISECONDS);
        wait = new WebDriverWait(driver, timeW);
        actions = new Actions(driver);
        System.out.println(((HasCapabilities) driver).getCapabilities());

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    //driver.quit();
                    //driver = null;
                }));
    }

    @After
    public void stop() {
        //driver.quit();
        //driver = null;
    }
}
