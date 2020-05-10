import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOf;


public class Test02 extends TestBase {

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

       // add phoes to compare
       String cssPhones = "label.ui-checkbox[data-commerce-target='CATALOG_PRODUCT_COMPARE']";
       int phonesCount = 2; // number of comparied phones

       List<WebElement> phones = driver.findElements(By.cssSelector(cssPhones));
       // add phones to compare
       for (int p = 0; p < phonesCount; p++) {
           phones = driver.findElements(By.cssSelector(cssPhones));
           phones.get(p).click();
           findItemPerformClick(cssPhones, true, false);
       }

       // get 'Сравнить' buttons for checked phones
       List<WebElement> y = driver.findElements(By.cssSelector("label.ui-checkbox.popover-wrapper"));
       // move to the last button
       if (y.size() < 2) {
           System.out.println("zzz");
       } else {
           actions.moveToElement(y.get(phonesCount - 1)).build().perform();
           actions.moveToElement(y.get(phonesCount - 1), 10, 20);
        }

       String cssCompareButton = "a.button-ui.button-ui_brand[href*='/compare/'";
       List<WebElement> btnCopmare = driver
               .findElements(By.cssSelector("a.button-ui.button-ui_brand[href*='/compare/'"));
       if (btnCopmare.size() > 0) {
           boolean isClicked = false;
           while (!isClicked) {
               actions.moveToElement(y.get(phonesCount - 1)).build().perform();
               try  {
                   driver.findElements(By.cssSelector(cssCompareButton)).get(0).click();
                    isClicked = true;
                } catch (Exception ex) {
                    Thread.currentThread().interrupt();
                    System.out.println("---");
                }
           }
       } else {
           System.out.println("********");
       }

       wait.until(titleContains("Сравнение товаров"));
       wait.until(visibilityOf(driver.findElement(By.cssSelector("section.group-table__row"))));

       List<WebElement> rows = driver.findElements(By.cssSelector("div.group-table__option-wrapper"));
       WebElement row;
       List<WebElement> e1;
       String s1 = "";
       String s2;
       List<String> listEqual = new ArrayList<>();
       List<String> listDfifferent = new ArrayList<>();
       String title;
       for (int i = 0; i < rows.size(); i++) {
           row = rows.get(i);
           e1 = row.findElements(By.cssSelector("div.group-table__data>p")); // row values
           boolean isEqual = true;
           for (int j = 0; j < phonesCount - 1; j++) {
               s1 = e1.get(j).getAttribute("textContent");
               s2 = e1.get(j + 1).getAttribute("textContent");
               isEqual = isEqual & s1.equals(s2);
           }
           e1 = row.findElements(By.cssSelector("span.group-table__option-name")); // row name
           title = e1.get(0).getAttribute("textContent"); // w
           if (isEqual) {
               listEqual.add(title);
           } else {
               listDfifferent.add(title);
           }

       }
       // slider
       driver.findElement(By.cssSelector("span.base-ui-toggle__icon")).click();
       // scan rows
       rows = driver.findElements(By.cssSelector("div.group-table__option-wrapper"));
       List<String> listDfifferent2 = new ArrayList<>();
       for (int i = 0; i < rows.size(); i++) {
           row = rows.get(i);
           e1 = row.findElements(By.cssSelector("span.group-table__option-name"));
           listDfifferent2.add(e1.get(0).getAttribute("textContent"));
       }

       System.out.println("Original List " + listDfifferent);
       listDfifferent.removeAll(listDfifferent2);
       Assert.assertEquals("Перестали отображаться одинаковые параметры", listDfifferent.size(), 0);

    }
}
