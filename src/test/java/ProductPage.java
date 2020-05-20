import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ProductPage extends Page {
    public ProductPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public ProductPage someActions() {
        // some prdouct page logic actions
        return this;
    }
}
