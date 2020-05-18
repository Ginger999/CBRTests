import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Application {
    public Smartfony2019Page smartfony2019Page;

    public Application(WebDriver driver, WebDriverWait wait) {
        smartfony2019Page = new Smartfony2019Page(driver, wait);
    }

    public void smartfony2019PageOpen() {
        smartfony2019Page.open();
    }

    public void quit(WebDriver driver) {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
