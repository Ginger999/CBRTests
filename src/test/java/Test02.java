import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Test02 extends TestBase {

    public Test02() {
    }

    @Before
    public void open_smartfony_2019_test() {
        open_smartfony_2019();
    }

   @Test
    public void TestComparisonOfTwoPhones() {
        // wait for the element - 'Наличие'
        wait.until(presenceOfElementLocated(By.cssSelector("span.ui-collapse__link-text")));

        // find section - 'Цена' and set values
        List<String> valueOfPrice = Arrays.asList("10001");
        setSectionValues("Цена", "radio", "data-min", valueOfPrice, false);

        // apply filter
        WebElement btnFilter = isSubElementPresent(null, "button.button-ui.button-ui_brand.left-filters__button",
                "textContent", "Применить", "startsWith");
        if (!(btnFilter.equals(null))) {
            btnFilter.click();
        }

        String cssPhones = "label.ui-checkbox[data-commerce-target='CATALOG_PRODUCT_COMPARE']";
        List<WebElement> phones;
        int phonesCount = 2;

        phones = driver.findElements(By.cssSelector(cssPhones));
        // add phones to compare
        for (int p = 0; p < phonesCount; p++) {
            phones = driver.findElements(By.cssSelector(cssPhones));
            phones.get(p).click();
            findItemPerformClick(cssPhones, true, false);
        }

        //actions.moveToElement(phones.get(phonesCount));
        List<WebElement> btnCompare = driver.findElements(By.cssSelector(".button-ui.button-ui_brand[href='/catalog/product/compare/'"));
        btnCompare.get(1).click();


        List<WebElement> rows = driver.findElements(By.cssSelector("div.table-row"));
        ArrayList<String> values = new ArrayList<String>();
        WebElement row;
        List<WebElement> e1;
        String s1 = "";
        String s2;
        String[][] a = new String[2][rows.size()];
        for (int i = 0; i < rows.size(); i++) {
            row = rows.get(i);
            e1 = row.findElements(By.cssSelector("div.table-value>p")); // value
            boolean isEqual = true;
            for (int j = 0; j < phonesCount - 1; j++) {
                s1 = e1.get(j).getAttribute("textContent");
                s2 = e1.get(j + 1).getAttribute("textContent");
                isEqual = isEqual & s1.equals(s2);
            }
            if (isEqual) {
                e1 = row.findElements(By.cssSelector("span.title.hidden-small-screen")); // name
                a[0][i] = s1; // value
                a[1][i] = e1.get(0).getAttribute("textContent"); // w
                values.add(s1);
            }

        }
        WebElement slider = driver.findElement(By.cssSelector("span.ui-toggle__slider"));
        slider.click();


    }
}
