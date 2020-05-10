import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;


public class Test03 extends TestBase {

    @Test
    public void TestPriceWithGauarantee() {
        // wait for the element - 'Наличие'
        System.out.println("TestPriceWithGauarantee");
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

        // find the 2nd part of complex checkbox: element to click
        String xpathCheckClick = "//input[contains(@class, 'ui-checkbox__input') and contains(@class, 'ui-checkbox__input_list') and @value='"
                + valuesOfBrand.get(0) + "']/..";

        clickAfterChecked(cssCheckProperty, xpathCheckClick, "div.apply-filters-float-btn");

        List<WebElement> phones = driver.findElements(By.cssSelector("div.product-info__title-link"));
        phones.get(0).click();
        WebElement price = driver.findElement(By.cssSelector("span.current-price-value[data-role*='current']"));
        String phonePrice = price.getAttribute("data-price-value");

        WebElement select = wait.until(visibilityOfElementLocated(By.cssSelector("select.form-control.select")));
        Select x = new Select(select);
        x.selectByIndex(1);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        String totalPrice = price.getAttribute("textContent").replaceAll(" ", "");
        float guarantee = Float.parseFloat(totalPrice) - Float.parseFloat(phonePrice);

        System.out.println("Phone price: " + phonePrice + " Guarantee: " + guarantee + "Total price: " +  totalPrice);
    }
    public void clickAfterChecked(String cssCheckProperty, String xpathCheckClick, String cssFloatButton) {
        // find the 1st part of complex checkbox: element by it's value
        WebElement btnCheckProperty = driver.findElement(By.cssSelector(cssCheckProperty));
        actions.moveToElement(btnCheckProperty).build().perform();

        // find the 2nd part of complex checkbox: element to click
        WebElement btnCheckClick = driver.findElement(By.xpath(xpathCheckClick));

        boolean isAppliedFloatButton = false;
        boolean isChecked;
        do{
            // get attribute("checked")
            try {
                isChecked = btnCheckProperty.getAttribute("checked").equals("true");
            } catch (Exception e) {
                isChecked = false; // because attribute("checked") = null when checkbox is clear
            }
            if (!isChecked){
                // click on the float button
                try {
                    btnCheckClick.click();
                    wait.until(presenceOfElementLocated(By.cssSelector(cssFloatButton))).click();
                    isAppliedFloatButton = true;
                } catch (Exception e) {
                    isAppliedFloatButton = false;
                }
                if (!isAppliedFloatButton) {
                    btnCheckClick.click();
                }
            } else {
                if (!isAppliedFloatButton) {
                    btnCheckClick.click();
                }
            }
        }
        while (!isAppliedFloatButton);
    }
}
