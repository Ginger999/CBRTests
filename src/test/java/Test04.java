import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Description;

public class Test04 extends TestBase {
    @Description("Сравнивет количество телефонов, указанных в пункте меню с суммарным количеством телефонов на страницах.")
    @Test
    public void testPopupItemCount() {
        // Select menu path
        List<String> menuPath;
        //menuPath = utils.getMenuPathOfSmartphones2019();
        menuPath = utils.getMenuPathOfSmartphonesLargeBattry();

        // get count of products from menu item label
        int productCountInMenu = utils.getMenuItemProductCount(menuPath);
        // open menu path
        utils.openLeftMenu(menuPath);

        int productCountOnPages = 0;
        WebElement nextPageButton;

        // scan pages and summarize the number of phones
        List<WebElement> phones;
        driver.manage().timeouts().implicitlyWait(150, TimeUnit.MILLISECONDS);
        do {
            phones = utils.getListPageProductBlocks();
            productCountOnPages = productCountOnPages + phones.size();
            nextPageButton = utils.getEnableNextPageButton();
            utils.clickNextPage(nextPageButton);
        } while (nextPageButton != null);

        // check asserts for number of smartphones
        Assert.assertEquals("Количество товаров в меню и на страницах не совпадает:", productCountInMenu, productCountOnPages);
    }
}