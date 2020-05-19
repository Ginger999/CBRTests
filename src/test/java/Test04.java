import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

public class Test04 extends TestBase {
    @Test
    public void testPopupItemCount() {
        // Select menu path
        List<String> menuPath;
        //menuPath = utils.getSmartphones2019MenuPath();
        menuPath = utils.getSmartphonesLargeBattryMenuPath();

        // get count of products from menu item label
        int productCountInMenu = utils.getProductCountInMenu(menuPath);
        // open menu path
        utils.openLeftMenu(menuPath);

        int productCountOnPages = 0;
        WebElement nextPageButton;

        // scan pages and summarize the number of phones
        List<WebElement> phones;
        driver.manage().timeouts().implicitlyWait(150, TimeUnit.MILLISECONDS);
        do {
            phones = utils.getProductBlocks();
            productCountOnPages = productCountOnPages + phones.size();
            nextPageButton = utils.getActiveNextPageButton();
            utils.paginationNextPageClick(nextPageButton);
        } while (nextPageButton != null);

        // check asserts for number of smartphones
        Assert.assertEquals("Количество товаров в меню и на страницах не совпадает:", productCountInMenu, productCountOnPages);
    }
}