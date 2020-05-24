import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class PopupCity extends Page {

    public PopupCity(WebDriver driver, WebDriverWait wait, Utils utils) {
        super(driver, wait, utils);
        PageFactory.initElements(driver, this);
    }

    @Step("Choose city")
    public PopupCity chooseCity(String city) {
        List<WebElement> cityPopups = driver.findElements(By.className("confirm-city-mobile"));
        if (cityPopups.size() > 0) {
            WebElement cityPopup = cityPopups.get(0);
            if (utils.isSubElementPresent(null, By.cssSelector("a.w-choose-city-widget.pseudo-link.pull-right"))) {
                wait.until(elementToBeClickable(By.cssSelector("a.w-choose-city-widget.pseudo-link.pull-right")))
                        .click();
                // input a city name
                wait.until(presenceOfElementLocated(By.cssSelector("input.form-control")))
                        .sendKeys(Keys.HOME + city + Keys.ENTER);
            }
            // wait: city popup will disappear
            wait.until(stalenessOf(cityPopup));
        }
        // wait: city name
        wait.until(attributeToBe(By.cssSelector("div.city-select.w-choose-city-widget"), "textContent", city));
        // wait: left menu items
        wait.until(numberOfElementsToBeMoreThan(By.cssSelector("div.menu-desktop__root-info"), 2));
        return this;
    }
}
