import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class Test02 extends TestBase {
    @Test
    @UseDataProvider(value = "test02", location = DataProviders.class)
    public void testPhoneComparison(Filter filter) {
        app.smartfony2019PageOpen();
        utils.setProductFilterValues(filter);
        utils.applyFilterByApllyButton();

        // add phones to compare
        int phonesCount = 2;
        utils.addProductsToCompare(phonesCount);

        List < WebElement > rows = driver.findElements(By.cssSelector("div.group-table__option-wrapper"));
        WebElement row;
        List < WebElement > e1ement;
        String s1;
        String s2;
        List < String > listEqual = new ArrayList < > ();
        List < String > listDfifferent = new ArrayList < > ();
        String title;
        for (int i = 0; i < rows.size(); i++) {
            row = rows.get(i);
            e1ement = row.findElements(By.cssSelector("div.group-table__data>p")); // row values
            boolean isEqual = true;
            for (int j = 0; j < phonesCount - 1; j++) {
                s1 = e1ement.get(j).getAttribute("textContent");
                s2 = e1ement.get(j + 1).getAttribute("textContent");
                isEqual = isEqual & s1.equals(s2);
            }
            e1ement = row.findElements(By.cssSelector("span.group-table__option-name")); // row name
            title = e1ement.get(0).getAttribute("textContent"); // w
            if (isEqual) {
                listEqual.add(title);
            } else {
                listDfifferent.add(title);
            }

        }
        utils.showDifferentPhoneSettings();

        // scan rows
        rows = driver.findElements(By.cssSelector("div.group-table__option-wrapper"));
        List < String > listDfifferent2 = new ArrayList < > ();
        for (int i = 0; i < rows.size(); i++) {
            row = rows.get(i);
            e1ement = row.findElements(By.cssSelector("span.group-table__option-name"));
            listDfifferent2.add(e1ement.get(0).getAttribute("textContent"));
        }

        listEqual.retainAll(listDfifferent);
        Assert.assertEquals("Перестали отображаться одинаковые параметры", listEqual.size(), 0);
    }
}