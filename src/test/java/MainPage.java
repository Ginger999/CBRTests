import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class MainPage extends Page {

    public MainPage(WebDriver driver, WebDriverWait wait, Utils utils) {
        super(driver, wait, utils);
        PageFactory.initElements(driver, this);
    }

    @Step("Open page")
    public void open(String url, String title) {
        driver.get(url);
        wait.until(titleIs(title));
        wait.until(numberOfElementsToBeMoreThan(By.cssSelector("div.menu-desktop__root-info"), 2));
    }
}
