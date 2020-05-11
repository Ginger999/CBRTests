import java.util.List;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import org.junit.Assert;

public class Utils {
    public WebDriver driver;
    public WebDriverWait wait;
    public Actions actions;

    public Utils(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Application.TIME_WAIT);
        actions = new Actions(driver);
    }
    public void openPage(String url, String title) {
        driver.get(url);
        wait.until(titleIs(title));
    }

    public boolean isSubElementPresent(WebElement rootElement, By locator) {
        if (rootElement == null) {
            return driver.findElements(locator).size() > 0;
        } else {
            return rootElement.findElements(locator).size() > 0;
        }
    }

    /* Moves to the element and basing on the parameters rebuilds the document, clicks on the element */
    public void findItemPerformClick(String cssLocator, boolean isPerform, boolean isClick) {
        WebElement element = wait.until(visibilityOfElementLocated(By.cssSelector(cssLocator)));
        if (isPerform) {
            actions.moveToElement(element).build().perform();
        }
        if (isClick) {
            element.click();
        }
    }

    /* Clicks on the check/radio buttons which have specified values */
    public void SetCheckValue(WebElement sectionElement, String locator, String xpath, String attribute,
            String value) {
        // find the button wich has a specidfied value
        WebElement element = isSubElementPresent(sectionElement, locator, attribute, value, "equals");
        // click on the clickabele part of the button
        if (!(element.equals(null))) {
            element = driver.findElement(By.xpath(xpath));
            element.click();
        }
    }

    /* Finds Section and marks buttons which have specified values */
    public void setSectionValues(String sectionLabel, String buttonType, String attributeName,
            List<String> attributeValues, boolean isShowAllButton) {

        // find section by sectionLabel
        WebElement section = isSubElementPresent(null, ".ui-collapse.ui-collapse_list", "textContent", sectionLabel,
                "startsWith");

        // do actions if a section exists
        if (!(section.equals(null))) {
            // open section
            WebElement btnCollapse = isSubElementPresent(section, "span.ui-collapse__link-text", "textContent",
                    sectionLabel, "equals");

            if (!(btnCollapse.equals(null))) {
                if (isSubElementPresent(section,
                        By.cssSelector("i.ui-collapse__icon.ui-collapse__icon_left.ui-collapse__icon_down"))) {
                    btnCollapse.click();
                }
            }
            // open all section values
            if (isShowAllButton) {
                WebElement btnShowAll = isSubElementPresent(section,
                        "i.ui-list-controls__icon.ui-list-controls__icon_down", "", "", "");
                if (!(btnShowAll.equals(null))) {
                    btnShowAll.click();
                }
            }

            // mark button which has a specified value
            String value;
            String locator;
            String xpath;
            // scan values
            for (int j = 0; j < attributeValues.size(); j++) {
                value = attributeValues.get(j);
                locator = "input.ui-checkbox__input.ui-checkbox__input_list[" + attributeName + "='" + value + "']";
                xpath = "//input[contains(@class, 'ui-checkbox__input') and contains(@class, 'ui-checkbox__input_list') and @"
                        + attributeName + "='" + value + "']/..";

                switch (buttonType) {
                    case "radio":
                        locator = locator.replace("checkbox", "radio");
                        xpath = xpath.replace("checkbox", "radio");
                }
                // mark button
                SetCheckValue(section, locator, xpath, attributeName, value);
            }
        }
    }

    /* Applies filter butto */
    public void applyFilterButton() {
        WebElement btnFilter = isSubElementPresent(null, "button.button-ui.button-ui_brand.left-filters__button",
                "textContent", "Применить", "startsWith");

        if (!(btnFilter.equals(null))) {
            btnFilter.click();
        }
    }

    /* Returns an element if it is founded.
       Сan use driver or an element as a root to search.
    */
    public WebElement isSubElementPresent(WebElement rootElement, String cssElements, String attributeName,
            String attributeValue, String comparison) {
        List<WebElement> elements;
        WebElement currentElement = null;
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
            for (int i = 0; i < elements.size(); i++) {
                currentElement = elements.get(i);
                switch (comparison) {
                    case "":
                        actions.moveToElement(currentElement).build().perform();
                        return elements.get(i);
                    case "equals":
                        if (currentElement.getAttribute(attributeName).equalsIgnoreCase(attributeValue)) {
                            try {
                                actions.moveToElement(currentElement).build().perform();
                            } catch (Exception e) {
                                System.out.println(cssElements + " " + attributeName + " " + attributeValue);
                            }
                            return elements.get(i);
                        }
                    case "startsWith":
                        if (currentElement.getAttribute(attributeName).startsWith(attributeValue)) {
                            actions.moveToElement(currentElement).build().perform();
                            return currentElement;
                        }
                }
            }
            if (scroll_index < scroll_max) {
                actions.sendKeys(Keys.PAGE_DOWN).perform();
            }
            scroll_index++;
        }
        return null;
    }
    /* Chooses city */
    public void chooseCity(String city) {
        if (isSubElementPresent(null, By.className("confirm-city-mobile"))) {
            if (isSubElementPresent(null, By.cssSelector("a.w-choose-city-widget.pseudo-link.pull-right"))) {
                wait.until(elementToBeClickable(By.cssSelector("a.w-choose-city-widget.pseudo-link.pull-right")))
                        .click();
                // input a city name
                wait.until(presenceOfElementLocated(By.cssSelector("input.form-control")))
                        .sendKeys(Keys.HOME + city + Keys.ENTER);
            }
            Assert.assertFalse(isSubElementPresent(null, By.cssSelector("confirm-city-mobile")));
        }
        wait.until(presenceOfElementLocated(By.cssSelector("div.menu-desktop__root-info")));
    }
}