import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebElement;

import io.qameta.allure.Description;

import java.util.ArrayList;
import java.util.List;

@RunWith(DataProviderRunner.class)
public class Test02 extends TestBase {
    @Description("Compares two phones and checks that only difference phone features are shown when choosing 'Only differing'")
    @Test
    @UseDataProvider(value = "test02", location = DataProviders.class)
    public void testPhoneComparison(Filter filter) {
        utils.openMenuSmartphones2019();
        utils.setFilterValues(filter);
        utils.filterByApllyButton();

        // add phones to compare
        int phonesCount = 2;
        utils.addListPageProductsToCompare(phonesCount);

        List<String> listOfEqual = getFeatureList(phonesCount, "equal");
        utils.showDifferentProductSettings();
        List<String> listOfDifference = getFeatureList(phonesCount, "");

        listOfEqual.retainAll(listOfDifference); // get intersection list

        Assert.assertEquals("Перестали отображаться одинаковые параметры", listOfEqual.size(), 0);
    }

    private List<String> getFeatureList(int phonesCount, String kind) {
        List<WebElement> rows = utils.getProductPageComparisonFeaturesBlocks();
        WebElement feature;
        List<WebElement> values;
        String v1;
        String v2;
        List<String> featureNamesList = new ArrayList<>();
        boolean isEqual;
        for (WebElement row : rows) {
            feature = row;
            values = utils.getFeatureBlockValues(feature); // featureBlock values
            switch (kind) {
                case "equal":
                    isEqual = true;
                    // compare the values of the feature within one block
                    for (int j = 0; j < phonesCount - 1; j++) {
                        v1 = values.get(j).getAttribute("textContent");
                        v2 = values.get(j + 1).getAttribute("textContent");
                        isEqual = isEqual & v1.equals(v2);
                    }
                    if (isEqual) {
                        featureNamesList.add(utils.getFeatureTitle(feature)); // add feature name to list
                    }
                    break;

                case "":
                    featureNamesList.add(utils.getFeatureTitle(feature)); // add feature name to list
                    break;
            }
        }
        return featureNamesList;
    }
}