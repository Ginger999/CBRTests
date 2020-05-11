import org.openqa.selenium.WebDriver;


public class Page {

	protected WebDriver driver;
    public Utils utils;
    private String defaultCity;
    private String baseUrl;
    private String baseUrlTitle;

    public Page(WebDriver driver) {
        this.driver = driver;
        //
        defaultCity = "Томск";
        baseUrl = "https://dns-shop.ru";
        baseUrlTitle = "DNS – интернет магазин цифровой и бытовой техники по доступным ценам.";
        //

        utils = new Utils(driver);
        //
        utils.openPage(baseUrl, baseUrlTitle);
        utils.chooseCity(defaultCity);
    }
}