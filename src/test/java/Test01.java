import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.aspectj.lang.annotation.Before;
import org.junit.Assert;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Test01 extends TestBase {
    private WebDriverWait wait;

    @Test
    public void TestFilter() {
        // open smartphones 2019
        app.smartfony2019PageOpen();

        // wait for the element - 'Наличие'
        app.smartfony2019Page.utils.wait.until(presenceOfElementLocated(By.cssSelector("span.ui-collapse__link-text")));

        // find section - 'Цена' and set values
        List<String> valueOfPrice = Arrays.asList("10001");
        app.smartfony2019Page.utils.setSectionValues("Цена", "radio", "data-min", valueOfPrice, false);

        // find section - 'Производитель' and set values
        List<String> valueOfBrand = Arrays.asList("xiaomi");
        app.smartfony2019Page.utils.setSectionValues("Производитель", "check", "value", valueOfBrand, true);

        // find section - 'Объем встроенной памяти' and set values
        List<String> valueOfMemory = Arrays.asList("64 ГБ", "128 ГБ");
        List<String> values = Arrays.asList("32tl", "32tg");
        app.smartfony2019Page.utils.setSectionValues("Объем встроенной памяти", "check", "value", values, true);

        // apply filter
        app.smartfony2019Page.utils.applyFilterButton ();

        // check if there are any pages
        List<WebElement> pages = app.smartfony2019Page.driver.findElements(By.cssSelector("li.pagination-widget__page"));
        int pageCount;

        if (pages.size() > 0) {
            pageCount = pages.size() - 4; // pages.size() - number of navigation buttons
        } else {
            pageCount = 1;
        }

        List<WebElement> phones;
        String phonesContent;
        // check asserts for each page
        for (int p = 0; p < pageCount; p++) {
            phones = app.smartfony2019Page.driver.findElements(By.cssSelector("div[data-id='product']"));
            phonesContent = phones.get(p).getAttribute("textContent");

            // check asserts for a brand and for memory
            Assert.assertTrue("полет1 нормальный", hasOneOfValues(phonesContent, valueOfBrand));
            Assert.assertTrue("полет2 нормальный", hasOneOfValues(phonesContent, valueOfMemory));

            if (pageCount > 1) {
                pages.get(pages.size() - 1).click(); // get '>' navigation button
            }
        }
        System.out.println("********** Test01 finished");
    }

    private boolean hasOneOfValues(String Text, List<String> values){
        for (int j = 0; j < values.size(); j++) {
            if(Text.toUpperCase().contains(values.get(j).toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}