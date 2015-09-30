package Pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

/**
 * Created by Sakie on 9/26/2015.
 */
public class LoginPage extends BasePage{

    @FindBy(id = "login-username") WebElement field_EmailLogin;     //css="#login-username"
    @FindBy(id = "login-passwd") WebElement field_PasswordLogin;    //css="#login-passwd"
    @FindBy(id = "login-signin") WebElement loginButton;
    //driver.findElement(By.partialLinkText("long")).click();
    @FindBy(partialLinkText = "Sign In") WebElement negToLoginLink;

    String loginPageTitle = "Flickr, a Yahoo company | Flickr - Photo Sharing!";    //un-login title
    String welcomePageTitle = "Welcome to Flickr - Photo Sharing";                  //login title

    public LoginPage(WebDriver driver) {
        super(driver);
        this.Page_Title = "Flickr, a Yahoo company | Flickr - Photo Sharing!";
        this.Page_URL = "https://www.flickr.com/?ytcheck=1";
    }

    public void loadPage(){
        driver.get(getPageURL());
        System.out.println("Load Page : " + getPageURL());
    }

    public void loginPage(String text_username, String text_password) throws InterruptedException {

        try {
            this.loadPage();
            //Handle the page does not login
            assertTrue(driver.getTitle().equals(welcomePageTitle));
        } catch (AssertionError e) {
            System.out.println("NOT log in yet, need to login");
            this.navigateToLoginPage();
            this.login(text_username, text_password);
            TimeUnit.SECONDS.sleep(1);
        } finally {
            Assert.assertEquals(driver.getTitle(),welcomePageTitle);
        }
    }

    public void login(String text_username, String text_password) {

        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        setElementText(field_EmailLogin, text_username);
        Assert.assertEquals(field_EmailLogin.getAttribute("value"),text_username);
        setElementText(field_PasswordLogin, text_password);
        Assert.assertEquals(field_PasswordLogin.getAttribute("value"),text_password);
        clickElement(loginButton);
    }

    public void navigateToLoginPage(){
        clickElement(negToLoginLink);
    }

    public void clickSingInBtn(){

        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        clickElement(loginButton);
    }

}
