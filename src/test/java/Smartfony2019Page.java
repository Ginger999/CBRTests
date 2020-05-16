import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Step;
import static org.openqa.selenium.support.ui.ExpectedConditions.numberOfElementsToBeMoreThan;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Smartfony2019Page extends Page {
    public Smartfony2019Page(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    @Step("Choose smartphones 2019")
    public Smartfony2019Page open() {
        // left menu links
        String locGadgets = "[href*=smartfony-planshety-i-fototexnika]";
        String locSmartphone = "a.ui-link.menu-desktop__second-level[href*=smartfony]";
        String locSmartphone2019 = "a.ui-link.menu-desktop__popup-link[href*='2019-goda'";

        // find gadgets -> smartfony -> smartfony2019
        driver.manage().timeouts().implicitlyWait(TestBase.TIME_I_WAIT_MAXIMUM, TimeUnit.MILLISECONDS);
        wait.until(numberOfElementsToBeMoreThan(By.cssSelector("div.menu-desktop__root-info"), 2));

        utils.findItemPerformClick(locGadgets, true, false);
        utils.findItemPerformClick(locSmartphone, true, false);
        utils.findItemPerformClick(locSmartphone2019, false, true);

        // wait for the element - 'Наличие'
        wait.until(presenceOfElementLocated(By.cssSelector("span.ui-collapse__link-text")));

        driver.manage().timeouts().implicitlyWait(TestBase.TIME_I_WAIT_DEFAULT, TimeUnit.MILLISECONDS);
        return this;
    }
}
