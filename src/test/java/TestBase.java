import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {
    public static final int TIME_WAIT = 100;
    public static final int TIME_I_WAIT_DEFAULT = 100;
    public static final int TIME_I_WAIT_MAXIMUM = 5000;

    public static ThreadLocal<Application> tlApp = new ThreadLocal<>();
    public Application app;
    public WebDriver driver;
    public WebDriverWait wait;
    public static Utils utils;

    @BeforeClass
    public static void setUpClass() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        } else if (os.contains("nix") || os.contains("nux")) {
            System.setProperty("webdriver.chrome.driver", "chromedriver");
        } else {
            throw new IllegalArgumentException("Operating System: " + os + ", there is no webdriver for it!");
        }
    }

    @Before
    public void start() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        System.out.println(((HasCapabilities) driver).getCapabilities());

        wait = new WebDriverWait(driver, TIME_WAIT);
        utils = new Utils(driver, wait);
        app = new Application(driver, wait, utils);
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