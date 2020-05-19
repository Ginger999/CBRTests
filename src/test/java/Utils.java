import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeMoreThan;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.stalenessOf;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class Utils {
    public WebDriver driver;
    private WebDriverWait wait;
    public Actions actions;

    public Utils(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        actions = new Actions(driver);
    }

    public void dragAndDrop(WebElement drag, WebElement drop) {
        new Actions(driver).moveToElement(drag).clickAndHold().moveToElement(drop).release().perform();
    }

    @Step("Adds products to compare")
    public void addProductsToCompare(int phoneNumberToCompare) {
        // add products to compare
        String cssProducts = "label.ui-checkbox[data-commerce-target='CATALOG_PRODUCT_COMPARE']";

        List < WebElement > products = driver.findElements(By.cssSelector(cssProducts));
        // add products to compare
        for (int p = 0; p < phoneNumberToCompare; p++) {
            products = driver.findElements(By.cssSelector(cssProducts));
            products.get(p).click();
            // wait number of 'Сравнить' buttons
            wait.until(numberOfElementsToBe(By.cssSelector("label.ui-checkbox.popover-wrapper"), p + 1));
            if (p == phoneNumberToCompare - 1) {
                driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
            }
            findItemPerformClick(cssProducts, true, false);
        }
        //findItemPerformClick(cssProducts, true, false);
        // press the button 'Сравнить'
        driver.findElements(By.cssSelector("a.button-ui.button-ui_brand[href*='/compare/'")).get(0).click();

        wait.until(titleContains("Сравнение товаров"));
        wait.until(numberOfElementsToBeMoreThan(By.cssSelector("section.group-table__row"), 0));
        driver.manage().timeouts().implicitlyWait(TestBase.TIME_I_WAIT_DEFAULT, TimeUnit.MILLISECONDS);
    }

    /* Clicks on 'Применить' button */
    public void applyFilterByApllyButton() {
        WebElement btnFilter = isSubElementFoundedAfterScrolling(null, "button.button-ui.button-ui_brand.left-filters__button",
            "textContent", "Применить", "startsWith");
        if (btnFilter != null) {
            actions.moveToElement(btnFilter).build().perform();
            btnFilter.click();
        }
    }

    /* Clicks on 'Показать' button */
    public void applyFilterByShowButton2(String attributeValue) {
        // TO DO выбор не только по getBrandValues, сделать унивесальным
        //String attributeValue = filter.getBrandValues().get(0);

        // find the 1st part of complex checkbox: element by it's value
        String cssCheckProperty = "input.ui-checkbox__input.ui-checkbox__input_list[value='" + attributeValue + "']";

        // find the 2nd part of complex checkbox: element to click
        String xpathCheckClick = "//input[contains(@class, 'ui-checkbox__input') and contains(@class, 'ui-checkbox__input_list') and @value='" +
            attributeValue + "']/..";

        clickShowButton(cssCheckProperty, xpathCheckClick, "div.apply-filters-float-btn");
    }

    /* Clicks on 'Показать' button */
    public void applyFilterByShowButton(Filter filter) {
        // TO DO выбор не только по getBrandValues, сделать унивесальным
        String attributeValue = filter.getBrandValues().get(0);

        // find the 1st part of complex checkbox: element by it's value
        String cssCheckProperty = "input.ui-checkbox__input.ui-checkbox__input_list[value='" + attributeValue + "']";

        // find the 2nd part of complex checkbox: element to click
        String xpathCheckClick = "//input[contains(@class, 'ui-checkbox__input') and contains(@class, 'ui-checkbox__input_list') and @value='" +
            attributeValue + "']/..";

        clickShowButton(cssCheckProperty, xpathCheckClick, "div.apply-filters-float-btn");
    }

    /* Returns the calculated product guarantee value */
    public String calcProductGuaranteeValue(String currentPrice, String totalPrice) {
        return Float.toString(Float.parseFloat(totalPrice) - Float.parseFloat(currentPrice));
    }

    /* Catches 'Показать' button and clicks on it
    1. click on checkbox wich contains value name
    2. wait 'Показать' button
    3. click on 'Показать' button
    */
    public void clickShowButton(String cssCheckProperty, String xpathCheckClick, String cssFloatButton) {
        // find the 1st part of complex checkbox: element by it's value
        WebElement btnCheckProperty = driver.findElement(By.cssSelector(cssCheckProperty));
        actions.moveToElement(btnCheckProperty).build().perform();

        // find the 2nd part of complex checkbox: element to click
        WebElement btnCheckClick = driver.findElement(By.xpath(xpathCheckClick));

        boolean isAppliedFloatButton = false;
        boolean isChecked;
        do {
            // get attribute("checked")
            if (btnCheckProperty.getAttribute("checked") == null) {
                isChecked = false;
            } else {
                if (btnCheckProperty.getAttribute("checked").equals("true")) {
                    isChecked = true;
                } else {
                    isChecked = false;
                }
            }
            if (!isChecked) {
                btnCheckClick.click();
                // try click on the float button
                try {
                    //wait.until(presenceOfElementLocated(By.cssSelector(cssFloatButton))).click();
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

    @Step("Choose city")
    public void chooseCity(String city) {
        List < WebElement > cityPopups = driver.findElements(By.className("confirm-city-mobile"));
        if (cityPopups.size() > 0) {
            WebElement cityPopup = cityPopups.get(0);
            if (isSubElementPresent(null, By.cssSelector("a.w-choose-city-widget.pseudo-link.pull-right"))) {
                wait.until(elementToBeClickable(By.cssSelector("a.w-choose-city-widget.pseudo-link.pull-right")))
                    .click();
                // input a city name
                wait.until(presenceOfElementLocated(By.cssSelector("input.form-control")))
                    .sendKeys(Keys.HOME + city + Keys.ENTER);
            }
            // wait: city popup will disappear
            wait.until(stalenessOf(cityPopup));
        }
        // wait: city name
        wait.until(attributeToBe(By.cssSelector("div.city-select.w-choose-city-widget"), "textContent", city));
        // wait: left menu items
        wait.until(numberOfElementsToBeMoreThan(By.cssSelector("div.menu-desktop__root-info"), 2));
    }

    /* Retuns true if webelement is founded after down scrolling
       using driver or an element as a root to search.
    */
    public WebElement isSubElementFoundedAfterScrolling(WebElement rootElement, String cssElements,
        String attributeName, String attributeValue, String comparison) {
        List < WebElement > elements;
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
            for (WebElement element: elements) {
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
            if (scroll_index < scroll_max) {
                actions.sendKeys(Keys.PAGE_DOWN).perform();
            }
            scroll_index++;
        }
        return null;
    }

    /* Returns true if webelement is present in the current document*/
    public boolean isSubElementPresent(WebElement rootElement, By locator) {
        if (rootElement == null) {
            return driver.findElements(locator).size() > 0;
        } else {
            return rootElement.findElements(locator).size() > 0;
        }
    }

    /* Finds Section and marks buttons which have specified values */
    @Step("Filter product by sectionName and sectionValues")
    public void filterOneSection(String sectionName, List < String > sectionValues) {
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
        WebElement section = isSubElementFoundedAfterScrolling(null, ".ui-collapse.ui-collapse_list", "textContent", sectionName,
            "startsWith");

        // do actions if a section exists
        if (section != null) {
            // open section
            WebElement btnCollapse = isSubElementFoundedAfterScrolling(section, "span.ui-collapse__link-text", "textContent",
                sectionName, "equals");

            if (btnCollapse != null) {
                if (isSubElementPresent(section,
                        By.cssSelector("i.ui-collapse__icon.ui-collapse__icon_left.ui-collapse__icon_down"))) {
                    btnCollapse.click();
                }
            }
            // open all section values
            if (isShowAllButton) {
                WebElement btnShowAll = isSubElementFoundedAfterScrolling(section,
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
            for (String sectionValue: sectionValues) {
                value = sectionValue;
                locator = "input.ui-checkbox__input.ui-checkbox__input_list[" + attributeName + "='" + value + "']";
                xpath = "//input[contains(@class, 'ui-checkbox__input') and contains(@class, 'ui-checkbox__input_list') and @" +
                    attributeName + "='" + value + "']/..";

                switch (buttonType) {
                    case "radio":
                        locator = locator.replace("checkbox", "radio");
                        xpath = xpath.replace("checkbox", "radio");
                }
                // mark button
                setCheckValue(section, locator, xpath, attributeName, value);
            }
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

    public WebElement getActiveNextPageButton() {
        List < WebElement > pages = getPaginationButtons(); // refresh pagination element
        if (pages.size() > 0) {
            // WebElement btnFilter = isSubElementFoundedAfterScrolling(null, "a.pagination-widget__page-link",
            //     "textContent", "", "startsWith");

            // move to pagination element
            actions.moveToElement(pages.get(0)).build().perform();
            // get index of '>' button
            int nextPageButtonIndex = pages.size() - 1 - 1;
            // get last page index
            int lastPageIndex = pages.size() - 1 - 2;
            //get '>' button as webelement
            WebElement nextPageButtonDetails;
            pages = getPaginationButtons();
            if (driver.findElements(By.cssSelector("a.pagination-widget__page-link")).size() > 0) {
                wait.until(presenceOfAllElementsLocatedBy(By.cssSelector("a.pagination-widget__page-link")));
                nextPageButtonDetails = pages.get(nextPageButtonIndex)
                    .findElement(By.cssSelector("a.pagination-widget__page-link"));
            } else {
                throw new IllegalArgumentException("WTF?");
            }

            // check is '>' button is enabled
            if (!pages.get(lastPageIndex).getAttribute("className").contains("active") &
                !nextPageButtonDetails.getAttribute("className").contains("disabled")) {
                return pages.get(nextPageButtonIndex);
            }
        }
        return null;
    }

    public List < WebElement > getAllElements(By locator) {
        List < WebElement > elements = driver.findElements(locator);
        if (elements.size() > 0) {
            return wait.until(presenceOfAllElementsLocatedBy(locator));
        } else {
            return elements;
        }
    }

    public String getCssOfLeftMenuItem(String menuItemLabel) {
        // TO DO определять локаторы в  зависимости от уровня пункта меню
        switch (menuItemLabel) {
            case "Смартфоны и гаджеты":
                return "a.ui-link.menu-desktop__root-title";
            case "Смартфоны":
                return "a.ui-link.menu-desktop__second-level";
            case "2019 года":
                return "a.ui-link.menu-desktop__popup-link";
            case "С большим аккумулятором":
                return "a.ui-link.menu-desktop__popup-link";
            default:
                throw new IllegalArgumentException("cssSelector is not defined for this item: " + menuItemLabel);
        }
    }

    /* Returns the list of feature values within one feature block */
    public List < WebElement > getFeatureBlockValues(WebElement featureBlock) {
        return featureBlock.findElements(By.cssSelector("div.group-table__data>p"));
    }

    /* Returns the name of the feature */
    public String getFeatureTitle(WebElement featureBlock) {
        return featureBlock.findElement(By.cssSelector("span.group-table__option-name")).getAttribute("textContent");
    }

    public WebElement getLeftMenuItem(List < String > menuItems) {
        String cssMenuItem;

        List < WebElement > els;
        for (int i = 0; i < menuItems.size(); i++) {
            cssMenuItem = getCssOfLeftMenuItem(menuItems.get(i));
            els = wait.until(numberOfElementsToBeMoreThan(By.cssSelector(cssMenuItem), 2));
            for (WebElement element: els) {
                if (element.getAttribute("textContent").toLowerCase().contains(menuItems.get(i).toLowerCase())) {
                    actions.moveToElement(element).build().perform();
                    if (i == menuItems.size() - 1) {
                        return element;
                    }
                    break;
                }
            }
        }
        return null;
    }

    public int getProductCountInMenu(List < String > menuItems) {
        WebElement leftMenuItem = getLeftMenuItem(menuItems);
        if (leftMenuItem != null) {
            String popupCount = leftMenuItem.findElement(By.cssSelector("span.menu-desktop__popup-count"))
                .getAttribute("textContent");
            if (!(Pattern.compile("[^0-9]").matcher(popupCount).find())) {
                return Integer.parseInt(popupCount);
            } else {
                return 0;
            }
            //return  Integer.parseInt(leftMenuItem.getAttribute("textContent").replace(menuItems.get(menuItems.size()-1), ""));
        }
        return 0;
    }

    /* Returns page buttons after search */
    public List < WebElement > getPaginationButtons() {
        By cssSelector = By.cssSelector("li.pagination-widget__page");
        List < WebElement > elements = driver.findElements(cssSelector);
        if (elements.size() > 0) {
            return wait.until(presenceOfAllElementsLocatedBy(cssSelector));
        } else {
            return elements;
        }
    }

    /* Returns number of page buttons after search */
    public int getPaginationButtonsCount(List < WebElement > pages) {
        if (pages.size() > 0) {
            return pages.size() - 4; // pages.size() - number of navigation buttons
        } else {
            return 1;
        }
    }

    public List < WebElement > getProductsAfterSearch() {
        return getAllElements(By.cssSelector("div.product-info__title-link"));
    }

    public List < WebElement > getProductBlocks() {
        return getAllElements(By.cssSelector("div[data-id='product']"));
    }

    /* Returns the list of feature blocks */
    public List < WebElement > getProductComparisonFeaturesBlocks() {
        return getAllElements(By.cssSelector("div.group-table__option-wrapper"));
    }

    public String getProductCurrentPrice() {
        return getProductPriceBlock().getAttribute("data-price-value").replaceAll(" ", "");
    }

    private WebElement getProductPriceBlock() {
        return wait.until(presenceOfElementLocated(By.cssSelector("span.current-price-value[data-role*='current']")));
    }

    public String getProductTotalPrice() {
        return getProductPriceBlock().getAttribute("textContent").replaceAll(" ", "");
    }
    public List < String > getSmartphones2019MenuPath() {
        return Arrays.asList("Смартфоны и гаджеты", "Смартфоны", "2019 года");
    }

    public List < String > getSmartphonesLargeBattryMenuPath() {
        return Arrays.asList("Смартфоны и гаджеты", "Смартфоны", "С большим аккумулятором");
    }

    public List < WebElement > getStockPins() {
        return getAllElements(By.cssSelector("div.vobler"));
    }

    /* Retuns true if Text contains one of the value from the List of values */
    public boolean hasOneOfValues(String Text, List < String > values) {
        for (String value: values) {
            if (Text.toUpperCase().contains(value.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    /* Clicks on the check/radio buttons which have specified values */
    public void setCheckValue(WebElement sectionElement, String locator, String xpath, String attribute,
        String value) {
        // find the button wich has a specidfied value
        WebElement element = isSubElementFoundedAfterScrolling(sectionElement, locator, attribute, value, "equals");
        // click on the clickabele part of the button
        if (element != null) {
            element = driver.findElement(By.xpath(xpath));
            element.click();
        }
    }

    /* Sets the complex filter for the product */
    public void setProductFilterValues(Filter filter) {
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
    }

    // Selects Guarantee from the list
    @Step("Select guarantee")
    public void selectProductGuarantee(String guaranteePeriod) {
        WebElement select = wait.until(visibilityOfElementLocated(By.cssSelector("select.form-control.select")));
        Select guaranteeList = new Select(select);
        guaranteeList.selectByVisibleText(guaranteePeriod);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void showDifferentProductSettings() {
        // slider
        // TO DO check the current slider state
        driver.findElement(By.cssSelector("span.base-ui-toggle__icon")).click();
    }


    @Step("Open menu item")
    public void openLeftMenu(List < String > menuItems) {
        driver.manage().timeouts().implicitlyWait(TestBase.TIME_I_WAIT_MAXIMUM, TimeUnit.MILLISECONDS);
        wait.until(numberOfElementsToBeMoreThan(By.cssSelector("div.menu-desktop__root-info"), 2));
        WebElement leftMenuItem = getLeftMenuItem(menuItems);
        if (leftMenuItem != null) {
            leftMenuItem.click();
        }
        // wait for the element - 'Наличие'
        wait.until(presenceOfElementLocated(By.cssSelector("span.ui-collapse__link-text")));
        driver.manage().timeouts().implicitlyWait(TestBase.TIME_I_WAIT_DEFAULT, TimeUnit.MILLISECONDS);

    }

    @Step("Open page")
    public void openPage(String url, String title) {
        driver.get(url);
        wait.until(titleIs(title));
        wait.until(numberOfElementsToBeMoreThan(By.cssSelector("div.menu-desktop__root-info"), 2));
    }

    public void openSmartphones2019() {
        openLeftMenu(getSmartphones2019MenuPath());
    }

    /* Clicks on the next page button */
    public void paginationNextPageClick(WebElement nextPageButton) {
        //WebElement nextPageButton = getNextPageButton();
        if (nextPageButton != null) {
            nextPageButton.click();
            wait.until(stalenessOf(nextPageButton));
        }
    }

}