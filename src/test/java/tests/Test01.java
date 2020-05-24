package tests;

import model.Filter;

import org.openqa.selenium.WebElement;

import io.qameta.allure.Description;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

import java.util.List;

@RunWith(DataProviderRunner.class)
public class Test01 extends TestBase {
    @Description("Проверяет, что выборка соответствует заданным фильтрам.")
    @Test
    @UseDataProvider(value = "test01", location = DataProviders.class)
    public void testFilter(Filter filter) {
        app.menuLeft.openSmartphones2019();
        app.filterLeft.setFilterValues(filter); // set filter values to filter phones
        app.filterLeft.filterByApplyButton(); // apply filter using 'Приметить' button

        List<WebElement> phones;
        String phonesContent;
        WebElement nextPageButton;
        // check asserts for each page
        do {

            phones = app.productList.getProductBlocks();
            for (WebElement phone : phones) {
                phonesContent = phone.getAttribute("textContent");

                // check asserts for a brand and for memory
                Assert.assertTrue("Производитель: " + filter.getBrandValues() + " отсутствует в описании товара: "
                        + phonesContent, utils.hasOneOfValues(phonesContent, filter.getBrandValues()));

                Assert.assertTrue(
                        "Память: " + filter.getMemoryValues() + "отсутствует в описании товара^ " + phonesContent,
                        utils.hasOneOfValues(phonesContent, filter.getMemoryCaptions()));
            }
            // the next page
            nextPageButton = app.pagination.getEnableNextPageButton();
            app.pagination.clickNextPage(nextPageButton);
        } while (nextPageButton != null);
    }
}
