/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import io.qameta.allure.Attachment;
//import io.qameta.allure.Description;
//import io.qameta.allure.Step;
import org.junit.After;
//import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
//import org.junit.BeforeClass;
import org.junit.Test;
//import static org.junit.Assert.*;
import org.openqa.selenium.By;

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
    public void TestHello() {
        System.out.print("Hello world!");
        Assert.assertEquals(7, 7);
    }

    //@Step
    //public void checkUserName() {
    //    Assert.assertEquals(7, 7);
    //}
    @Test
    public void TestURL() {
        System.out.println(baseURL);
        driver.get(baseURL);
        wait.until(titleIs("DNS – интернет магазин цифровой и бытовой техники по доступным ценам."));
    }

    @Test
    public void TestSelectRegion() {
        System.out.println(baseURL);
        driver.get(baseURL);
        
        // set Region
        WebElement region = driver.findElement(By.xpath("/html/body/header/div[2]/div/ul[1]/li[1]/div/div[2]/a[1]"));
        region.click();
 
        // menu links
        String locGadgets = "[href='/catalog/17a890dc16404e77/smartfony-planshety-i-fototexnika/']";
        String locSmartphone = "[href='/catalog/17a8a01d16404e77/smartfony/']";
        String locSmartphone2019 = "[href='/catalog/recipe/8659ebf990b82f13/2019-goda/']";

        // find gadgets -> smartfony -> smartfony2019
        Actions actions = new Actions(driver);
        WebElement gadgets = driver.findElement(By.cssSelector(locGadgets));
        actions.moveToElement(gadgets).build().perform();
  
        WebElement smartfony = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locSmartphone)));
        actions.moveToElement(smartfony).build().perform();
        
        WebElement smartfony2019 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locSmartphone2019)));
        smartfony2019.click();
   
    }
}
