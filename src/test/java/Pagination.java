import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Pagination extends Page {

    public Pagination(WebDriver driver, WebDriverWait wait, Utils utils) {
        super(driver, wait, utils);
        PageFactory.initElements(driver, this);
    }

    /* Clicks on the next page button */
    public Pagination clickNextPage(WebElement nextPageButton) {
        if (nextPageButton != null) {
            nextPageButton.click();
            wait.until(stalenessOf(nextPageButton));
        }
        return this;
    }

    /* Returns the next page clickable element */
    public WebElement getEnableNextPageButton() {
        List<WebElement> pages = getPaginationButtons(); // refresh pagination element
        if (pages.size() > 0) {
            // move to pagination element
            utils.actions.moveToElement(pages.get(0)).build().perform();
            // get index of '>' button
            int nextPageButtonIndex = pages.size() - 1 - 1;
            // get last page index
            int lastPageIndex = pages.size() - 1 - 2;
            // get '>' button as webelement
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
            if (!pages.get(lastPageIndex).getAttribute("className").contains("active")
                    & !nextPageButtonDetails.getAttribute("className").contains("disabled")) {
                return pages.get(nextPageButtonIndex);
            }
        }
        return null;
    }

    /* Returns pagination buttons after search */
    public List<WebElement> getPaginationButtons() {
        By cssSelector = By.cssSelector("li.pagination-widget__page");
        List<WebElement> elements = driver.findElements(cssSelector);
        if (elements.size() > 0) {
            return wait.until(presenceOfAllElementsLocatedBy(cssSelector));
        } else {
            return elements;
        }
    }
}
