import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Description;

import java.util.List;


@RunWith(DataProviderRunner.class)
public class Test01 extends TestBase {
    @Description("Tests that each product in the list contains only filter values")
    @Test
    @UseDataProvider(value = "test01", location = DataProviders.class)
    public void testFilter(Filter filter) {
        utils.openMenuSmartphones2019();
        utils.setFilterValues(filter); // set  filter values to filter phones
        utils.filterByApllyButton(); // aply filter using 'Приметить' button

        List<WebElement> phones;
        String phonesContent;
        WebElement nextPageButton;
        // check asserts for each page
        do {

            phones = utils.getListPageProductBlocks();
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
            nextPageButton = utils.getEnableNextPageButton();
            utils.clickNextPage(nextPageButton);
        } while (nextPageButton != null);
    }
}
