import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.openqa.selenium.WebElement;


@RunWith(DataProviderRunner.class)
public class Test01 extends TestBase {

    @Test
    @UseDataProvider(value = "test01", location = DataProviders.class)
    public void testFilter(Filter filter) {
        app.smartfony2019PageOpen(); // open smartphones 2019
        utils.setProductFilterValues(filter); // set  filter values to filter phones
        utils.applyFilterByApllyButton(); // aply filter using 'Приметить' button

        // check how many pages after the search
        List < WebElement > pages = utils.getPaginationButtons();
        int pageCount = utils.getPaginationButtonsCount(pages);

        List < WebElement > phones;
        String phonesContent;
        // check asserts for each page
        for (int p = 0; p < pageCount; p++) {
            phones = utils.getProductBlocks();
            phonesContent = phones.get(p).getAttribute("textContent");

            // check asserts for a brand and for memory
            Assert.assertTrue(
                "Производитель: " + filter.getBrandValues() + " отсутствует в описании товара: " + phonesContent,
                utils.hasOneOfValues(phonesContent, filter.getBrandValues()));

            Assert.assertTrue(
                "Память: " + filter.getMemoryValues() + "отсутствует в описании товара^ " + phonesContent,
                utils.hasOneOfValues(phonesContent, filter.getMemoryCaptions()));

            // the next page
            utils.nextPageButtonClick(pages);
        }
    }
}