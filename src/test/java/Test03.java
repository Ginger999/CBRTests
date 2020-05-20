import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class Test03 extends TestBase {
    @Description("Gets the price of the phone and the cost of the guarantee")
    @Test
    @UseDataProvider(value = "test03", location = DataProviders.class)
    public void testPriceWithGauarantee(Filter filter) {
        utils.openMenuSmartphones2019();
        utils.setFilterValues(filter);
        utils.filterByShowButton(filter.getBrandValues().get(0));
        utils.getProductListLinks().get(0).click();

        String currentPrice = utils.getProductPageCurrentPrice();

        utils.selectProductGuarantee("1 год");

        String totalPrice = utils.getProductPageTotalPrice();
        String quarateePrice = utils.calcProductPageGuaranteeValue(currentPrice, totalPrice);

        allurePint(currentPrice, quarateePrice, totalPrice);

    }

    // allure report
    @Step("Print price of a phone with a guarantee")
    private void allurePint(String currentPrice, String quarateePrice, String totalPrice) {
        System.out.println("Phone price: " + currentPrice + " Guarantee: " + quarateePrice + " Total price: " + totalPrice);
    }
}