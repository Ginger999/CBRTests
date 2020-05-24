package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Page {
    public WebDriver driver;
    public WebDriverWait wait;

    public Utils utils;

    public Page(WebDriver driver, WebDriverWait wait, Utils utils) {
        this.driver = driver;
        this.wait = wait;
        this.utils = utils;
    }
}