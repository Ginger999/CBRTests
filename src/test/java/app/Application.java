package app;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

public class Application {
    public MainPage mainPage;
    public ProductPage productPage;
    public ProductList productList;
    public FilterLeft filterLeft;
    public MenuLeft menuLeft;
    public PopupCity popupCity;
    public Pagination pagination;
    private String defaultCity = "Томск";
    private String baseUrl = "https://dns-shop.ru";
    private String baseUrlTitle = "DNS – интернет магазин цифровой и бытовой техники по доступным ценам.";

    public Application(WebDriver driver, WebDriverWait wait, Utils utils) {
        mainPage = new MainPage(driver, wait, utils);
        productPage = new ProductPage(driver, wait, utils);
        productList = new ProductList(driver, wait, utils);
        filterLeft = new FilterLeft(driver, wait, utils);
        menuLeft = new MenuLeft(driver, wait, utils);
        popupCity = new PopupCity(driver, wait, utils);
        pagination = new Pagination(driver, wait, utils);
        mainPage.open(baseUrl, baseUrlTitle);
        popupCity.chooseCity(defaultCity);
    }

    public void quit(WebDriver driver) {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
