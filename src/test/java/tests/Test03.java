package tests;

import model.Filter;

import io.qameta.allure.Description;
import io.qameta.allure.Step;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class Test03 extends TestBase {
    @Description("Проверяет стоимость телефона с гарантией и без. Выводит в отчет цену телефона, стоимость гарантии.")
    @Test
    @UseDataProvider(value = "test03", location = DataProviders.class)
    public void testPriceWithGauarantee(Filter filter) {
        app.menuLeft.openSmartphones2019();
        app.filterLeft.setFilterValues(filter);
        app.filterLeft.filterByShowButton(filter.getBrandValues().get(0));
        app.productList.getLinks().get(0).click();

        String currentPrice = app.productPage.getCurrentPrice();

        app.productPage.selectGuarantee("1 год");

        String totalPrice = app.productPage.getTotalPrice();
        String quarateePrice = utils.calcProductPageGuaranteeValue(currentPrice, totalPrice);

        allurePint(currentPrice, quarateePrice, totalPrice);
    }

    // allure report
    @Step("Print price of a phone with a guarantee")
    public void allurePint(String currentPrice, String quarateePrice, String totalPrice) {
        System.out.println(
                "Phone price: " + currentPrice + " Guarantee: " + quarateePrice + " Total price: " + totalPrice);
    }
}