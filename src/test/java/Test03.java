import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;


public class Test03 extends TestBase {

    public Test03() {
    }

    @Before
    public void open_smartfony_2019_test() {
        open_smartfony_2019();
    }

    @Test
    public void TestPriceWithGauarantee() {
        // wait for the element - 'Наличие'
        wait.until(presenceOfElementLocated(By.cssSelector("span.ui-collapse__link-text")));

        // find section - 'Цена' and set values
        List<String> valueOfPrice = Arrays.asList("27001");
        setSectionValues("Цена", "radio", "data-min", valueOfPrice, false);

        // find section - 'Производитель' and set values
        List<String> valuesOfBrand = Arrays.asList("apple");
        setSectionValues("Производитель", "check", "value", valuesOfBrand, true);

        // find the 1st part of complex checkbox: element by it's value
        String cssCheckProperty = "input.ui-checkbox__input.ui-checkbox__input_list[value='" + valuesOfBrand.get(0)
                + "']";
        WebElement btnCheckProperty = driver.findElement(By.cssSelector(cssCheckProperty));
        actions.moveToElement(btnCheckProperty).build().perform();

        // find the 2nd part of complex checkbox: element to click
        String xpathCheckClick = "//input[contains(@class, 'ui-checkbox__input') and contains(@class, 'ui-checkbox__input_list') and @value='"
                + valuesOfBrand.get(0) + "']/..";
        WebElement btnCheckClick = driver.findElement(By.xpath(xpathCheckClick));

        boolean isAppliedShowButton = false;
        boolean isChecked;
        do{
            // get attribute("checked")
            try {
                isChecked = btnCheckProperty.getAttribute("checked").equals("true");
            } catch (Exception e) {
                isChecked = false; // because attribute("checked") = null when checkbox is clear
            }
            if (!isChecked){
                // click on the float button - 'Показать'
                try {
                    btnCheckClick.click();
                    wait.until(presenceOfElementLocated(By.cssSelector("div.apply-filters-float-btn"))).click();
                    //driver.findElement(By.cssSelector("div.apply-filters-float-btn")).click();
                    isAppliedShowButton = true;
                } catch (Exception e) {
                    isAppliedShowButton = false;
                }
                if (!isAppliedShowButton) {
                    btnCheckClick.click();
                }
            } else {
                if (!isAppliedShowButton) {
                    btnCheckClick.click();
                }
            }
        }
        while (!isAppliedShowButton);
        List<WebElement> phones = driver
                .findElements(By.cssSelector("div.product-info__title-link"));
        phones.get(0).click();
        WebElement price = driver.findElement(By.cssSelector("span.current-price-value[data-role*='current']"));
        String phonePrice = price.getAttribute("data-price-value");

        Select x = new Select(driver.findElement(By.cssSelector("select.form-control.select")));
        x.selectByIndex(1);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        String phonePriceUp = price.getAttribute("textContent").replaceAll(" ", "");
        float guarantee = Float.parseFloat(phonePriceUp) - Float.parseFloat(phonePrice);

        System.out.println("Phone prices: " + " " + phonePrice + " " + phonePriceUp + " " + guarantee);

    }
}
