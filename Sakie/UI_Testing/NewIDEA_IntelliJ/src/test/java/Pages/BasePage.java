package Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * BasePage
 */
public class BasePage {

    public WebDriver driver;
    public String Page_Title;
    public String Page_URL;
    public WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 5);
    }

    public void loadPage(){
        driver.get(getPageURL());
        Assert.assertEquals(driver.getTitle(), getPageTitle());
    }

    public void clickElement(WebElement element){
        element.click();
    }

    public void setElementText(WebElement element, String text){
        element.clear();
        element.sendKeys(text);
        Assert.assertEquals(element.getAttribute("value"), text);
    }

    public String getPageURL(){
        return Page_URL;
    }

    public String getPageTitle(){
        return Page_Title;
    }

}
