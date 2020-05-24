import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Utils {
    public WebDriver driver;
    private WebDriverWait wait;
    public Actions actions;

    public Utils(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(driver);
    }

    public void actionDragAndDrop(WebElement drag, WebElement drop) {
        new Actions(driver).moveToElement(drag).clickAndHold().moveToElement(drop).release().perform();
    }

    public void actionOpenLinlInNewTab(WebElement element, int numberOfWIndows) {
        new Actions(driver).moveToElement(element).keyDown(Keys.CONTROL).keyDown(Keys.SHIFT).click(element)
                .keyUp(Keys.CONTROL).keyUp(Keys.SHIFT).perform();
        wait.until(numberOfWindowsToBe(numberOfWIndows));
    }

    /* Returns the calculated product guarantee value */
    public String calcProductPageGuaranteeValue(String currentPrice, String totalPrice) {
        return Float.toString(Float.parseFloat(totalPrice) - Float.parseFloat(currentPrice));
    }

    /*
     * Retuns true if webelement is founded after down scrolling using driver or an
     * element as a root to search.
     */
    public WebElement isSubElementFoundedAfterScrolling(WebElement rootElement, String cssElements,
            String attributeName, String attributeValue, String comparison) {
        List<WebElement> elements;
        WebElement currentElement;
        int scroll_max = 6; // max number of scrolling attempts to find the ellement
        int scroll_index = 0;

        // scroll down the page to find the ellement
        while (scroll_index < scroll_max) {
            // define the rooot for a searching
            if (rootElement == null) {
                elements = driver.findElements(By.cssSelector(cssElements));
            } else {
                elements = rootElement.findElements(By.cssSelector(cssElements));
            }

            // scan elements which where founded by cssSelector
            for (WebElement element : elements) {
                currentElement = element;
                switch (comparison) {
                    case "":
                        actions.moveToElement(currentElement).build().perform();
                        return element;
                    case "equals":
                        if (currentElement.getAttribute(attributeName).equalsIgnoreCase(attributeValue)) {
                            try {
                                actions.moveToElement(currentElement).build().perform();
                            } catch (Exception e) {
                                System.out.println(cssElements + " " + attributeName + " " + attributeValue);
                            }
                            return element;
                        }
                    case "startsWith":
                        if (currentElement.getAttribute(attributeName).startsWith(attributeValue)) {
                            actions.moveToElement(currentElement).build().perform();
                            return currentElement;
                        }
                }
            }
            actions.sendKeys(Keys.PAGE_DOWN).perform();
            scroll_index++;
        }
        return null;
    }

    /* Returns true if webelement is present in the current document */
    public boolean isSubElementPresent(WebElement rootElement, By locator) {
        if (rootElement == null) {
            return driver.findElements(locator).size() > 0;
        } else {
            return rootElement.findElements(locator).size() > 0;
        }
    }

    /* Moves to the element and rebuilds the document, clicks on the element */
    public void findItemPerformClick(String cssLocator, boolean isPerform, boolean isClick) {
        WebElement element = wait.until(visibilityOfElementLocated(By.cssSelector(cssLocator)));
        if (isPerform) {
            actions.moveToElement(element).build().perform();
        }
        if (isClick) {
            element.click();
        }
    }

    /* Tries to recieve all elements if it is possible */
    public List<WebElement> getAllElements(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        if (elements.size() > 0) {
            return wait.until(presenceOfAllElementsLocatedBy(locator));
        } else {
            return elements;
        }
    }

    public Float getNumPrice(String price) {
        return Float.parseFloat(price.replaceAll(" ", ""));
    }

    /* Retuns true if Text contains one of the value from the List of values */
    public boolean hasOneOfValues(String Text, List<String> values) {
        for (String value : values) {
            if (Text.toUpperCase().contains(value.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}