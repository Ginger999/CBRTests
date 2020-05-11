import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.HasCapabilities;

public class Application {
    public static final int TIME_WAIT = 100;
    public static final int TIME_I_WAIT_DEFAULT = 100;
    public static final int TIME_I_WAIT_MAXIMUM = 5000;

    private WebDriver driver;
    public Smartfony2019Page smartfony2019Page;

    public Application() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        System.out.println(((HasCapabilities) driver).getCapabilities());
        smartfony2019Page = new Smartfony2019Page(driver);
    }

    public void smartfony2019PageOpen() {
        smartfony2019Page.open();
    }

    public void quit() {
        if (!(driver.equals(null))) {
            driver.quit();
            driver = null;
        }
    }
}
