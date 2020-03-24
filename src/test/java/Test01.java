/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import io.qameta.allure.Attachment;
//import io.qameta.allure.Description;
import io.qameta.allure.Step;

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

/**
 *
 * @author Ginger
 */
public class Test01 extends TestBase{
    public Test01() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void TestCalc() {
        System.out.print("Hello world!");
        Assert.assertEquals(7, 7);
    }
    
    @Step
    public void checkUserName() {
        Assert.assertEquals(7, 7);
    }
    
    @Test
    public void TestGoogle() {
        System.out.println(website);
        driver.get(website);
        driver.findElement(By.name("q")).sendKeys("webdriver");
        driver.findElement(By.name("btnK")).click();
        wait.until(titleIs("webdriver - Поиск в Google"));
    }
}
