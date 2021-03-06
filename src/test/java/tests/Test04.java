package tests;

import org.openqa.selenium.WebElement;

import io.qameta.allure.Description;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Test04 extends TestBase {
    @Description("Сравнивет количество телефонов, указанных в пункте меню, с суммарным количеством телефонов на страницах.")
    @Test
    public void testPopupItemCount() {
        // select menu path
        List<String> menuPath = app.menuLeft.getPathOfSmartphonesLargeBattry(); // getPathOfSmartphones2019();

        int productCountInMenu = app.menuLeft.getItemProductCount(menuPath); // get count of products from menu item
                                                                             // label
        app.menuLeft.open(menuPath);

        int productCountOnPages = 0;
        WebElement nextPageButton;

        // scan pages and summarize the number of phones
        List<WebElement> phones;
        driver.manage().timeouts().implicitlyWait(150, TimeUnit.MILLISECONDS);
        do {
            phones = app.productList.getProductBlocks();
            productCountOnPages = productCountOnPages + phones.size();
            nextPageButton = app.pagination.getEnableNextPageButton();
            app.pagination.clickNextPage(nextPageButton);
        } while (nextPageButton != null);

        // check asserts for number of smartphones
        Assert.assertEquals("Количество товаров в меню и на страницах не совпадает:", productCountInMenu,
                productCountOnPages);
    }
}