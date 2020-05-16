import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Page {
    public WebDriver driver;
    public WebDriverWait wait;

    public Utils utils;
    private String defaultCity;
    private String baseUrl;
    private String baseUrlTitle;

    public Page(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        //
        defaultCity = "Томск";
        baseUrl = "https://dns-shop.ru";
        baseUrlTitle = "DNS – интернет магазин цифровой и бытовой техники по доступным ценам.";
        //

        utils = new Utils(driver, wait);

        utils.openPage(baseUrl, baseUrlTitle);
        utils.chooseCity(defaultCity);
    }
}