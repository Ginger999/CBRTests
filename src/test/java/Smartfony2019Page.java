import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Smartfony2019Page extends Page {
    public Smartfony2019Page(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        // left menu links
        String locGadgets = "[href*=smartfony-planshety-i-fototexnika]";
        String locSmartphone = "a.ui-link.menu-desktop__second-level[href*=smartfony]";
        String locSmartphone2019 = "a.ui-link.menu-desktop__popup-link[href*='2019-goda'";

        // find gadgets -> smartfony -> smartfony2019
        driver.manage().timeouts().implicitlyWait(Application.TIME_I_WAIT_MAXIMUM, TimeUnit.MILLISECONDS);
        utils.findItemPerformClick(locGadgets, true, false);
        utils.findItemPerformClick(locSmartphone, true, false);
        utils.findItemPerformClick(locSmartphone2019, false, true);
        driver.manage().timeouts().implicitlyWait(Application.TIME_I_WAIT_DEFAULT, TimeUnit.MILLISECONDS);
    }
}
