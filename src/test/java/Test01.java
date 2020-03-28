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
    public void TestSelectRegion(){
        System.out.println(baseURL);
        driver.get(baseURL);
        // set Region
        WebElement element = driver.findElement(By.xpath("/html/body/header/div[2]/div/ul[1]/li[1]/div/div[2]/a[1]"));
        element.click();
        //WebElement element2 = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/div/div[2]/div[1]/a"));
        //element2.click();
        // find Smartphone
        String locGadgets = "[href='/catalog/17a890dc16404e77/smartfony-planshety-i-fototexnika/']";
        WebElement gadgets = driver.findElement(By.cssSelector(locGadgets));
        gadgets.click(); 
        
        //String locSmartphone = "[href='/catalog/17a8a01d16404e77/smartfony/']";
        //WebElement smartfony = gadgets.findElement(By.cssSelector(locSmartphone));
        //smartfony.click();
        
        //WebElement smartfony1 = wait.until(ExpectedConditions.invisibilityOf(gadgets.findElement(By.cssSelector(locSmartphone))));
  
    }
}
