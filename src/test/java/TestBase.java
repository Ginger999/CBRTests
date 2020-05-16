import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {
    public static final int TIME_WAIT = 100;
    public static final int TIME_I_WAIT_DEFAULT = 100;
    public static final int TIME_I_WAIT_MAXIMUM = 5000;


    public static ThreadLocal < Application > tlApp = new ThreadLocal < > ();
    public Application app;
    public WebDriver driver;
    public WebDriverWait wait;
    public static Utils utils;

    @Before
    public void start() {
        // if (tlApp.get() != null) {
        //     app = tlApp.get();
        //     return;
        // }
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println(((HasCapabilities) driver).getCapabilities());

        wait = new WebDriverWait(driver, TIME_WAIT);
        app = new Application(driver, wait);
        utils = new Utils(driver, wait);
        tlApp.set(app);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (app != null) {
                app.quit(driver);
                app = null;
            }
        }));

    }
    @After
    public void stop() {
        app.quit(driver);
        app = null;
    }
}