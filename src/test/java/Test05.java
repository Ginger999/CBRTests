import java.util.HashSet;
import java.util.Set;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import io.qameta.allure.Description;
import io.qameta.allure.Step;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

@RunWith(DataProviderRunner.class)
public class Test05 extends TestBase {
    @Description("Сравнивает характеристики цены на телефон по акции и старой ценыы, выводит результаты в отчет.")
    @Test
    @UseDataProvider(value = "test05", location = DataProviders.class)
    public void testLisAndProductPagePrices(Filter filter) {
        utils.openMenuSmartphones2019();
        utils.setFilterValues(filter);
        utils.filterByShowButton(filter.getStockValues().get(0));

        WebElement phoneBlock = utils.getListPageProductBlocks().get(0); // get the 1st phone block in the list
        WebElement phoneLink = utils.getProductLink(phoneBlock);

        WebElement oldPrice1 = utils.getProductListPriceNonStock(phoneBlock);
        WebElement newPrice1 = utils.getProductListPriceWithStock(phoneBlock);

        Float oldPrTab1 = getPriceFloat(oldPrice1);
        Float newPrTab1 = getPriceFloat(newPrice1);

        reportLessThan("Цена по акции меньше чем предыдущая стоимость", newPrTab1, oldPrTab1);
        reportLessThan("Размер шрифта для старой цены меньше чем размер шрифта цены по акции", getFontSize(oldPrice1), getFontSize(newPrice1));
        reportLessThan("Толщина шрифта для старой цены меньше чем толщина шрифта цены по акции", getFontWeight(oldPrice1), getFontWeight(newPrice1));

        // open product link in a new tab
        String firstWindow = driver.getWindowHandle(); // remember the current tab
        Set<String>existingWindows= driver.getWindowHandles(); // remember current windows handeles
        utils.actionOpenLinlInNewTab(phoneLink, 2); // open the product in another tab and wait 2 open tabs
        String secondWindow = getWindowHandle(existingWindows); // define the new tab handle
        driver.switchTo().window(secondWindow);

        WebElement oldPrice2 = driver.findElement(By.cssSelector("div.price-block s.prev-price-total"));
        WebElement newPrice2 = driver.findElement(By.cssSelector("div.price_g span.current-price-value"));

        Float oldPrTab2 = getPriceFloat(oldPrice2);
        Float newPrTab2 = getPriceFloat(newPrice2);

        reportEquals("Старые цены в списке продуктов и на странице продукта совпадают", oldPrTab1, oldPrTab2);
        reportEquals("Цены по акции в списке продуктов и на странице продукта совпадают", newPrTab1, newPrTab2);
        reportLessThan("Цена по акции меньше чем предыдущая стоимость", newPrTab2, oldPrTab2);
        reportLessThan("Размер шрифта для старой цены меньше чем размер шрифта цены по акции", getFontSize(oldPrice2), getFontSize(newPrice2));
        reportLessThan("Толщина шрифта для старой цены меньше чем толщина шрифта цены по акции", getFontWeight(oldPrice2), getFontWeight(newPrice2));

        driver.close(); // close the current tab
        driver.switchTo().window(firstWindow); // return to original tab
    }

    private Float getNum(String s) {
        return Float.parseFloat(s.toLowerCase().replace("px", "").replaceAll(" ", ""));
    }

    private Float getPriceFloat(WebElement element) {
        return getNum(element.getAttribute("textContent"));
    }

    private Float getFontSize(WebElement element) {
        return getNum(element.getCssValue("font-size"));
    }

    private Float getFontWeight(WebElement element) {
        return getNum(element.getCssValue("font-weight"));
    }

    private String getWindowHandle(Set<String> existingWindows) {
        Set<String> newWindows = driver.getWindowHandles();
        Set<String> newWindowHandle = new HashSet<String>(newWindows);
        newWindowHandle.removeAll(existingWindows);
        return newWindowHandle.iterator().next();
    }

    @Step("ReportLessThan")
    private void reportLessThan(String message, Float value1, Float value2) {
        Assert.assertTrue(message, (value1 < value2));
    }

    @Step("ReportEquals")
    private void reportEquals(String message, Float value1, Float value2) {
        Assert.assertEquals(message, value1, value2);
    }
}