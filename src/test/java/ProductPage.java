import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.qameta.allure.Step;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ProductPage extends Page {

    public ProductPage(WebDriver driver, WebDriverWait wait, Utils utils) {
        super(driver, wait, utils);
        PageFactory.initElements(driver, this);
    }

    /* Returns the list of feature blocks */
    public List<WebElement> getComparisonFeaturesBlocks() {
        return utils.getAllElements(By.cssSelector("div.group-table__option-wrapper"));
    }

    /* Returns the list of feature values within one feature block */
    public List<WebElement> getFeatureBlockValues(WebElement featureBlock) {
        return featureBlock.findElements(By.cssSelector("div.group-table__data>p"));
    }

    /* Returns the list of feature blocks */
    public String getCurrentPrice() {
        return getPriceBlock().getAttribute("data-price-value").replaceAll(" ", "");
    }

    /* Selects Guarantee from the list */
    @Step("Select guarantee")
    public void selectGuarantee(String guaranteePeriod) {
        WebElement select = wait.until(visibilityOfElementLocated(By.cssSelector("select.form-control.select")));
        Select guaranteeList = new Select(select);
        guaranteeList.selectByVisibleText(guaranteePeriod);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    /* Returns the name of the feature */
    public String getFeatureTitle(WebElement featureBlock) {
        return featureBlock.findElement(By.cssSelector("span.group-table__option-name")).getAttribute("textContent");
    }

    /*
     * Returns the product price contained in the product block on the product page
     */
    private WebElement getPriceBlock() {
        return wait.until(visibilityOfElementLocated(By.cssSelector("span.current-price-value[data-role*='current']")));
    }

    /* Returns product total price: product price plus guaratee value */
    public String getTotalPrice() {
        return getPriceBlock().getAttribute("textContent").replaceAll(" ", "");
    }

    /* Slider */
    public ProductPage showDifferentSettings() {
        wait.until(elementToBeClickable(By.cssSelector("span.base-ui-toggle__icon"))).click();
        return this;
    }
}
