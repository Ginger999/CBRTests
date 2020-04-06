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
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.openqa.selenium.interactions.Actions;

/**
 *
 * @author Ginger
 */
public class Test01 extends TestBase {

    public Test01() {
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
        WebElement region = driver.findElement(By.xpath("/html/body/header/div[2]/div/ul[1]/li[1]/div/div[2]/a[1]"));
        region.click();

        // left menu links
        String locGadgets = "[href*=smartfony-planshety-i-fototexnika]";
        String locSmartphone = "a.ui-link.menu-desktop__second-level[href*=smartfony]";
        String locSmartphone2019 = "a.ui-link.menu-desktop__popup-link[href*='2019-goda'";

        // find gadgets -> smartfony -> smartfony2019
        Actions actions = new Actions(driver);
        WebElement gadgets = driver.findElement(By.cssSelector(locGadgets));
        actions.moveToElement(gadgets).build().perform();

        WebElement smartfony = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locSmartphone)));
        actions.moveToElement(smartfony).build().perform();

        WebElement smartfony2019 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locSmartphone2019)));
        smartfony2019.click();
        driver.manage().timeouts().implicitlyWait(300, TimeUnit.MILLISECONDS);
        wait.until(titleIs("Смартфоны 2019 года: купить в интернет магазине DNS. Смартфоны 2019 года: цены, большой каталог, новинки"));

        //find the 1st visible button in the list - 'Наличие'
        String cssButtons = "span.ui-collapse__link-text";
        WebElement e1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(cssButtons)));
        //actions.moveToElement(e1).build().perform();
        actions.sendKeys(Keys.PAGE_DOWN).perform();

        //find rest buttons in the list
        List<WebElement> Buttons = driver.findElements(By.cssSelector(cssButtons));
        int Buttons_Count = Buttons.size();

        for (int j = 0; j < Buttons_Count; j++) {
            System.out.println(String.valueOf(Buttons.size()) + ": " + String.valueOf(j) + " |" + Buttons.get(j).getText());
            actions.moveToElement(Buttons.get(j)).build().perform();
            driver.findElements(By.cssSelector(cssButtons));
        }

        String cssRadio = "div.ui-radio.ui-radio_list .ui-radio__content_list";
        List<WebElement> Radio = driver.findElements(By.cssSelector(cssRadio));
        int Radio_Count = Radio.size();
        System.out.println("Radio_Count: " + String.valueOf(Radio_Count));
        for (int j = 0; j < Radio_Count; j++) {
            System.out.println(String.valueOf(Radio_Count) + ": " + String.valueOf(j) + " |" + Radio.get(j).getText());
        }
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
