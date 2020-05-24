package pages;

import model.Filter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;

import java.util.List;

public class FilterLeft extends Page {

    public FilterLeft(WebDriver driver, WebDriverWait wait, Utils utils) {
        super(driver, wait, utils);
        PageFactory.initElements(driver, this);
    }

    /*
     * Catches 'Показать' button and clicks on it
     * 1. click on checkbox wich contains value name
     * 2. wait for 'Показать' button
     * 3. click on 'Показать' button
     */
    private void clickShowButton(String cssCheckProperty, String xpathCheckClick, String cssFloatButton) {
        // find the 1st part of complex checkbox: element by it's value
        WebElement btnCheckProperty = driver.findElement(By.cssSelector(cssCheckProperty));
        utils.actions.moveToElement(btnCheckProperty).build().perform();

        // find the 2nd part of complex checkbox: element to click
        WebElement btnCheckClick = driver.findElement(By.xpath(xpathCheckClick));

        boolean isAppliedFloatButton = false;
        boolean isChecked;
        do {
            // get attribute("checked")
            if (btnCheckProperty.getAttribute("checked") == null) {
                isChecked = false;
            } else {
                isChecked = btnCheckProperty.getAttribute("checked").equals("true");
            }
            if (!isChecked) {
                btnCheckClick.click();
                // try click on the float button
                try {
                    // wait.until(presenceOfElementLocated(By.cssSelector(cssFloatButton))).click();
                    driver.findElement(By.cssSelector(cssFloatButton)).click();
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
        } while (!isAppliedFloatButton);
    }

    /* Clicks on 'Применить' button */
    public FilterLeft filterByApplyButton() {
        WebElement btnFilter = utils.isSubElementFoundedAfterScrolling(null,
                "button.button-ui.button-ui_brand.left-filters__button", "textContent", "Применить", "startsWith");
        if (btnFilter != null) {
            utils.actions.moveToElement(btnFilter).build().perform();
            btnFilter.click();
        }
        return this;
    }

    /* Clicks on 'Показать' button */
    public FilterLeft filterByShowButton(String attributeValue) {
        // find the 1st part of complex checkbox: element by it's value
        String cssCheckProperty = "input.ui-checkbox__input.ui-checkbox__input_list[value='" + attributeValue + "']";

        // find the 2nd part of complex checkbox: element to click
        String xpathCheckClick = "//input[contains(@class, 'ui-checkbox__input') and contains(@class, 'ui-checkbox__input_list') and @value='"
                + attributeValue + "']/..";

        clickShowButton(cssCheckProperty, xpathCheckClick, "div.apply-filters-float-btn");
        return this;
    }

    /* Finds Section and marks buttons which have specified values */
    @Step("Filter product by sectionName and sectionValues")
    private void filterOneSection(String sectionName, List<String> sectionValues) {
        String buttonType = "check";
        String attributeName = "value";
        boolean isShowAllButton = true;
        switch (sectionName) {
            case "Цена":
                buttonType = "radio";
                attributeName = "data-min";
                isShowAllButton = false;
                break;
            case "Акция":
                buttonType = "check";
                attributeName = "value";
                isShowAllButton = false;
                break;
            case "Производитель":
                buttonType = "check";
                attributeName = "value";
                isShowAllButton = true;
                break;
            case "Объем встроенной памяти":
                buttonType = "check";
                attributeName = "value";
                isShowAllButton = true;
                break;
        }

        // find section by sectionName
        WebElement section = utils.isSubElementFoundedAfterScrolling(null, ".ui-collapse.ui-collapse_list",
                "textContent", sectionName, "startsWith");

        // do actions if a section exists
        if (section != null) {
            // open section
            WebElement btnCollapse = utils.isSubElementFoundedAfterScrolling(section, "span.ui-collapse__link-text",
                    "textContent", sectionName, "equals");

            if (btnCollapse != null) {
                if (utils.isSubElementPresent(section,
                        By.cssSelector("i.ui-collapse__icon.ui-collapse__icon_left.ui-collapse__icon_down"))) {
                    btnCollapse.click();
                }
            }
            // open all section values
            if (isShowAllButton) {
                WebElement btnShowAll = utils.isSubElementFoundedAfterScrolling(section,
                        "i.ui-list-controls__icon.ui-list-controls__icon_down", "", "", "");
                if (btnShowAll != null) {
                    btnShowAll.click();
                }
            }

            // mark button which has a specified value
            String value;
            String locator;
            String xpath;
            // scan values
            for (String sectionValue : sectionValues) {
                value = sectionValue;
                locator = "input.ui-checkbox__input.ui-checkbox__input_list[" + attributeName + "='" + value + "']";
                xpath = "//input[contains(@class, 'ui-checkbox__input') and contains(@class, 'ui-checkbox__input_list') and @"
                        + attributeName + "='" + value + "']/..";

                if ("radio".equals(buttonType)) {
                    locator = locator.replace("checkbox", "radio");
                    xpath = xpath.replace("checkbox", "radio");
                }
                // mark button
                setCheckValue(section, locator, xpath, attributeName, value);
            }
        }
    }

    /* Sets the complex filter for the product */
    public FilterLeft setFilterValues(Filter filter) {
        if (filter.getFilterPrice() != null) {
            filterOneSection(filter.getFilterPrice(), filter.getPriceValues());
        }
        if (filter.getFilterStock() != null) {
            filterOneSection(filter.getFilterStock(), filter.getStockValues());
        }
        if (filter.getFilterBrand() != null) {
            filterOneSection(filter.getFilterBrand(), filter.getBrandValues());
        }
        if (filter.getFilterMemory() != null) {
            filterOneSection(filter.getFilterMemory(), filter.getMemoryValues());
        }
        return this;
    }

    /* Clicks on the check/radio buttons which have specified values */
    private void setCheckValue(WebElement sectionElement, String locator, String xpath, String attribute,
            String value) {
        // find the button wich has a specidfied value
        WebElement element = utils.isSubElementFoundedAfterScrolling(sectionElement, locator, attribute, value,
                "equals");
        // click on the clickabele part of the button
        if (element != null) {
            element = driver.findElement(By.xpath(xpath));
            element.click();
        }
    }
}
