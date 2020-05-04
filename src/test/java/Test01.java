import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Test01 extends TestBase {

    public Test01() {
    }

    @Before
    public void open_smartfony_2019_test() {
        open_smartfony_2019();
    }

    private boolean hasOneOfValues(String Text, List<String> values){
        for (int j = 0; j < values.size(); j++) {
            if(Text.toUpperCase().contains(values.get(j).toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void TestFilter() {
        // wait for the element - 'Наличие'
        wait.until(presenceOfElementLocated(By.cssSelector("span.ui-collapse__link-text")));

        // find section - 'Цена' and set values
        List<String> valueOfPrice = Arrays.asList("10001");
        setSectionValues("Цена", "radio", "data-min", valueOfPrice, false);

        // find section - 'Производитель' and set values
        List<String> valueOfBrand = Arrays.asList("xiaomi");
        setSectionValues("Производитель", "check", "value", valueOfBrand, true);

        // find section - 'Объем встроенной памяти' and set values
        List<String> valueOfMemory = Arrays.asList("64 ГБ", "128 ГБ");
        List<String> values = Arrays.asList("32tl", "32tg");
        setSectionValues("Объем встроенной памяти", "check", "value", values, true);

        // apply filter
        WebElement btnFilter = isSubElementPresent(null, "button.button-ui.button-ui_brand.left-filters__button", "textContent", "Применить", "startsWith");

        if (!(btnFilter.equals(null))) {
            btnFilter.click();
        }

        // check if there are any pages
        List<WebElement> pages = driver.findElements(By.cssSelector("li.pagination-widget__page"));
        int pageCount;

        if (pages.size() > 0) {
            pageCount = pages.size() - 4;  // pages.size() - number of navigation buttons
        } else {
            pageCount = 1;
        }

        List<WebElement> phones;
        String phonesContent;
        // check asserts for each page
        for (int p = 0; p < pageCount; p++) {
            phones = driver.findElements(By.cssSelector("div[data-id='product']"));
            phonesContent = phones.get(p).getAttribute("textContent");

            // check asserts for a brand and for memory
            Assert.assertTrue(hasOneOfValues(phonesContent, valueOfBrand));
            Assert.assertTrue(hasOneOfValues(phonesContent, valueOfMemory));
            if (pageCount > 1){
                pages.get(pages.size()-1).click(); // get '>' navigation button
            }
        }
    }
}