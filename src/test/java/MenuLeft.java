import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class MenuLeft extends Page {

    public MenuLeft(final WebDriver driver, final WebDriverWait wait, Utils utils) {
        super(driver, wait, utils);
        PageFactory.initElements(driver, this);
    }

    /* Returns the last item in the menu items chain */
    public WebElement getItem(List<String> menuItems) {
        String cssMenuItem;

        List<WebElement> els;
        for (int i = 0; i < menuItems.size(); i++) {
            cssMenuItem = getItemCss(menuItems.get(i));
            els = wait.until(numberOfElementsToBeMoreThan(By.cssSelector(cssMenuItem), 2));
            for (WebElement element : els) {
                if (element.getAttribute("textContent").toLowerCase().contains(menuItems.get(i).toLowerCase())) {
                    utils.actions.moveToElement(element).build().perform();
                    if (i == menuItems.size() - 1) {
                        return element;
                    }
                    break;
                }
            }
        }
        return null;
    }

    /* Returns css selector of the left menu item */
    public String getItemCss(String menuItemLabel) {
        switch (menuItemLabel) {
            case "Смартфоны и гаджеты":
                return "a.ui-link.menu-desktop__root-title";
            case "Смартфоны":
                return "a.ui-link.menu-desktop__second-level";
            case "2019 года":
            case "С большим аккумулятором":
                return "a.ui-link.menu-desktop__popup-link";
            default:
                throw new IllegalArgumentException("cssSelector is not defined for this item: " + menuItemLabel);
        }
    }

    /* Returns the count of products contained in the menu item */
    public int getItemProductCount(List<String> menuItems) {
        WebElement leftMenuItem = getItem(menuItems);
        if (leftMenuItem != null) {
            String popupCount = leftMenuItem.findElement(By.cssSelector("span.menu-desktop__popup-count"))
                    .getAttribute("textContent");
            if (!(Pattern.compile("[^0-9]").matcher(popupCount).find())) {
                return Integer.parseInt(popupCount);
            } else {
                return 0;
            }
        }
        return 0;
    }

    /* Returns a specified menu path */
    public List<String> getPathOfSmartphones2019() {
        return Arrays.asList("Смартфоны и гаджеты", "Смартфоны", "2019 года");
    }

    /* Returns a specified menu path */
    public List<String> getPathOfSmartphonesLargeBattry() {
        return Arrays.asList("Смартфоны и гаджеты", "Смартфоны", "С большим аккумулятором");
    }

    @Step("Open menu item")
    public MenuLeft open(List<String> menuItems) {
        driver.manage().timeouts().implicitlyWait(TestBase.TIME_I_WAIT_MAXIMUM, TimeUnit.MILLISECONDS);
        wait.until(numberOfElementsToBeMoreThan(By.cssSelector("div.menu-desktop__root-info"), 2));
        WebElement leftMenuItem = getItem(menuItems);
        if (leftMenuItem != null) {
            leftMenuItem.click();
        }
        // wait for the element - 'Наличие'
        wait.until(presenceOfElementLocated(By.cssSelector("span.ui-collapse__link-text")));
        driver.manage().timeouts().implicitlyWait(TestBase.TIME_I_WAIT_DEFAULT, TimeUnit.MILLISECONDS);
        return this;
    }

    /* Follows a specifed menu path and open the last item */
    public MenuLeft openSmartphones2019() {
        open(getPathOfSmartphones2019());
        return this;
    }
}
