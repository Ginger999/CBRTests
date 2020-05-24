import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.qameta.allure.Step;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ProductList extends Page {

    public ProductList(final WebDriver driver, final WebDriverWait wait, Utils utils) {
        super(driver, wait, utils);
        PageFactory.initElements(driver, this);
    }

    @Step("Adds products to compare")
    public ProductList addProductsToCompare(int phoneNumberToCompare) {
        // add products to compare
        String cssProducts = "label.ui-checkbox[data-commerce-target='CATALOG_PRODUCT_COMPARE']";
        List<WebElement> products = wait
                .until(numberOfElementsToBeMoreThan(By.cssSelector(cssProducts), phoneNumberToCompare));
        // add products to compare
        for (int p = 0; p < phoneNumberToCompare; p++) {
            products.get(p).click();
            // wait number of 'Сравнить' buttons
            wait.until(numberOfElementsToBe(By.cssSelector("label.ui-checkbox.popover-wrapper"), p + 1));
            if (p == phoneNumberToCompare - 1) {
                driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
            }
            utils.findItemPerformClick(cssProducts, true, false);
        }
        // press the button 'Сравнить'
        driver.findElements(By.cssSelector("a.button-ui.button-ui_brand[href*='/compare/'")).get(0).click();

        wait.until(titleContains("Сравнение товаров"));
        wait.until(numberOfElementsToBeMoreThan(By.cssSelector("section.group-table__row"), 0));
        driver.manage().timeouts().implicitlyWait(TestBase.TIME_I_WAIT_DEFAULT, TimeUnit.MILLISECONDS);
        return this;
    }

    /* Returns the count of products contained in the menu item */
    public List<WebElement> getLinks() {
        return utils.getAllElements(By.cssSelector("a.ui-link[data-role='clamped-link']"));
    }

    /* Returns product price without a stock */
    public WebElement getPriceNonStock(WebElement productBlock) {
        return productBlock.findElement(By.cssSelector("span.product-price__previous-total"));
    }

    /* Returns product price with a stock */
    public WebElement getPriceWithStock(WebElement productBlock) {
        return productBlock.findElement(By.cssSelector("div.product-price__current"));
    }

    /* Returns product blocks on the page after search */
    public List<WebElement> getProductBlocks() {
        return utils.getAllElements(By.cssSelector("div[data-id='product']"));
    }

    /*
     * Returns the first clickable link of product on the page after search starting
     * from the root element
     */
    public WebElement getProductLink(WebElement element) {
        return wait.until(
                elementToBeClickable(element.findElement(By.cssSelector("a.ui-link[data-role='clamped-link']"))));
    }
}
