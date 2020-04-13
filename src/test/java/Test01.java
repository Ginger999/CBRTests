/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import io.qameta.allure.Attachment;
//import io.qameta.allure.Description;
//import io.qameta.allure.Step;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
//import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
//import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

//import java.util.concurrent.TimeUnit;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

/**
 *
 * @author Ginger
 */
public class Test01 extends TestBase {

    public Test01() {
    }

    private void FindItemPerformClick(String cssLocator, boolean toPerform, boolean toClick) {
        System.out.println(cssLocator);
        WebElement item = wait.until(visibilityOfElementLocated(By.cssSelector(cssLocator)));
        if (toPerform) {
            actions.moveToElement(item).build().perform();
        }
        if (toClick) {
            item.click();
        }
    }

    private void PrintItems(List<WebElement> Items) {
        int ItemsCount = Items.size();
        System.out.println("ItemsCount: " + String.valueOf(ItemsCount));
        for (int i = 0; i < ItemsCount; i++) {
            System.out.println(String.valueOf(ItemsCount) + ": " + String.valueOf(i) + " |" + Items.get(i).getText());

        }
    }

    private boolean FindInListByValueClick(List<WebElement> Items, String Value, boolean toClick) {
        boolean res = false;
        int ItemsCount = Items.size();
        for (int i = 0; i < ItemsCount; i++) {
            res = Items.get(i).getText().contains(Value);
            System.out.println(Items.size() + " : " + i + " | " + Items.get(i).getText() + " | " + res + " | " + Value);
            if (toClick) {
                Items.get(i).click();
            }
            if (res) {
                return res;
            }
        }
        return res;
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void TestCase1() {
        System.out.println(baseURL);
        driver.get(baseURL);

        // set Region
        List< WebElement> region = driver.findElements(By.cssSelector("a.btn.btn-additional"));
        if (region.size() > 0) {
            region.get(0).click();
        }

        //left menu links
        String locGadgets = "[href*=smartfony-planshety-i-fototexnika]";
        String locSmartphone = "a.ui-link.menu-desktop__second-level[href*=smartfony]";
        String locSmartphone2019 = "a.ui-link.menu-desktop__popup-link[href*='2019-goda'";

        //find gadgets -> smartfony -> smartfony2019
        FindItemPerformClick(locGadgets, true, false);
        FindItemPerformClick(locSmartphone, true, false);
        FindItemPerformClick(locSmartphone2019, false, true);

        driver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);
        wait.until(titleIs("Смартфоны 2019 года: купить в интернет магазине DNS. Смартфоны 2019 года: цены, большой каталог, новинки"));

        //find the 1st visible button in the list - 'Наличие'
        String cssDownButtons = "span.ui-collapse__link-text";
        FindItemPerformClick(cssDownButtons, true, false);
        actions.sendKeys(Keys.PAGE_DOWN).perform();

        //find rest buttons in the list
        List< WebElement> Buttons = driver.findElements(By.cssSelector(cssDownButtons));
        int Buttons_Count = Buttons.size();

        for (int j = 0; j < Buttons_Count; j++) {
            System.out.println(String.valueOf(Buttons.size()) + ": " + String.valueOf(j) + " |" + Buttons.get(j).getText());
            actions.moveToElement(Buttons.get(j)).build().perform();
            driver.findElements(By.cssSelector(cssDownButtons));
        }

        String cssRadio = "div.ui-radio.ui-radio_list .ui-radio__content_list";
        //boolean founded = ScanItems(cssRadio, "10001");

        System.out.println("cssRadio");
        String value = "10 001";

        int i = 0;
        boolean isFound = false;
        do {
            i++;
            Buttons = driver.findElements(By.cssSelector(cssRadio));
            System.out.println(Buttons.size() + " | " + i + " | ");
            isFound = FindInListByValueClick(Buttons, value, true);
            if (!isFound) {
                actions.sendKeys(Keys.PAGE_DOWN).perform();
            }
        } while (i < 5 & isFound == false);

        System.out.println("cssDownButtons");
        value = "Производитель";
        i = 0;
        isFound = false;
        do {
            i++;
            Buttons = driver.findElements(By.cssSelector(cssDownButtons));
            PrintItems(Buttons);
            isFound = FindInListByValueClick(Buttons, value, false);
            System.out.println(Buttons.size() + " | " + i + " | " + isFound);
            if (!isFound) {
                actions.sendKeys(Keys.PAGE_DOWN).perform();
            } else {
                i = 30;
            }
        } while (i < 30 & isFound == false);

        //String cssShowAll = "a.ui-link.ui-link_blue.ui-link_pseudolink.ui-list-controls__link.ui-list-controls__link_fold >span";
        driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
        String cssShowAll = "i.ui-list-controls__icon.ui-list-controls__icon_down";
        FindItemPerformClick(cssShowAll, false, true);
        driver.manage().timeouts().implicitlyWait(timeI, TimeUnit.SECONDS);
//
////        actions.sendKeys(Keys.PAGE_DOWN).perform();
//        cssDownButtons ="div.ui-checkbox-group.ui-checkbox-group_list";
//        Buttons = driver.findElements(By.cssSelector(cssDownButtons)); 
//        PrintItems(Buttons);
//       

        //String cssXiaomi = "input.ui-checkbox__input.ui-checkbox__input_list[value='xiaomi'";
        String cssXiaomi = "input.ui-checkbox__input.ui-checkbox__input_list[value='xiaomi']";
        Buttons = driver.findElements(By.cssSelector(cssXiaomi));
        PrintItems(Buttons);
        WebElement item = driver.findElement(By.cssSelector(cssXiaomi));
        System.out.println("attribute " + item.getAttribute("value"));
        //actions.moveToElement(item).build().perform();
        //item = driver.findElement(By.cssSelector(cssXiaomi));
        item.click();

//        FindItemPerformClick(cssXiaomi, false, false);
//
//        for (int j = 0; j < Buttons_Count; j++) {
//            System.out.println(String.valueOf(Buttons.size()) + ": " + String.valueOf(j) + " |" + Buttons.get(j).getText());
//            actions.moveToElement(Buttons.get(j)).build().perform();
//            driver.findElements(By.cssSelector(cssDownButtons));
//        }
//        Buttons = driver.findElements(By.cssSelector(".ui-collapse__link_in"));
//        Buttons.get(3).click();
//        WebElement el = wait.until(visibilityOfElementLocated(By.cssSelector(locGadgets)));
//        el.click();
//
//        String cssManufacturer = ".ui-checkbox.ui-checkbox_list > input[value='xiaomi']";
        //WebElement price = driver.findElement(By.xpath("/html/body/div[1]/div/div[5]/div[1]/div[2]/div[5]/div[2]/a/span"));
        //actions.moveToElement(price).build().perform();
        //WebElement price = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div/div[5]/div[1]/div[2]/div[5]/div[2]/a/span")));
        //actions.moveToElement(price).build().perform();
        //System.out.println("Price " + price.getText());
        //price.click();
        //List<WebElement> price_value = driver.findElements(By.className("ui-radio__content ui-radio__content_list"));
        //System.out.println("Number of elements:" + price_value.size());
        //for (int i = 0; i < price_value.size(); i++) {
        //    System.out.println("Radio button text:" + price_value.get(i).getAttribute("data-min"));
        //}
        //
        //String locE1 = "[href='https://www.dns-shop.ru/guide/17a8a01d-1640-11e5-a679-00259074e77d/']";
        //driver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);
//        WebElement e1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locE1)));
//        actions.moveToElement(e1).build().perform();
    }
}
