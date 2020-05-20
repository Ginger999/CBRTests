import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Application {
    public ProductPage productPage;

    public Application(WebDriver driver, WebDriverWait wait) {
        productPage = new ProductPage(driver, wait);
    }

    public void quit(WebDriver driver) {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
