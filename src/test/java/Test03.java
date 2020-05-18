import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.Step;

@RunWith(DataProviderRunner.class)
public class Test03 extends TestBase {

    @Test
    @UseDataProvider(value = "test03", location = DataProviders.class)
    public void testPriceWithGauarantee(Filter filter) {
        //app.smartfony2019PageOpen();
        utils.openSmartphones2019();
        utils.setProductFilterValues(filter);
        utils.applyFilterByShowButton(filter);
        utils.getProductsAfterSearch().get(0).click();

        String currentPrice = utils.getProductCurrentPrice();

        utils.selectProductGuarantee("1 год");

        String totalPrice = utils.getProductTotalPrice();
        String quarateePrice = utils.calcProductGuaranteeValue(currentPrice, totalPrice);

        allurePint(currentPrice, quarateePrice, totalPrice);

    }
    // for allure
    @Step("Print price of a phone with a guarantee")
    private void allurePint(String currentPrice, String quarateePrice, String totalPrice) {
        System.out.println("Phone price: " + currentPrice + " Guarantee: " + quarateePrice + " Total price: " + totalPrice);
    }
}