package Utilities;

import com.sun.org.apache.xerces.internal.impl.PropertyManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import static Utilities.DriverFactory.BrowserType.*;
import static java.lang.System.setProperty;
//import junitx.util.PropertyMan

/**
 *
 */
public class DriverFactory {

    public enum BrowserType {
        CHROME("chrome"),
        IE("internet_explorer");


        private String value;

        private BrowserType(String value) {
            this.value = value;
        }

        public String getBrowserName() {
            return this.value;
        }
    }


    public static WebDriver getDriver(BrowserType type){
        WebDriver driver = null;
        switch (type) {
            case CHROME:
                setProperty("webdriver.chrome.driver", "c:/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case IE:
                setProperty("webdriver.ie.driver", "c:/IEDriverServer.exe");
                driver = new InternetExplorerDriver();
                break;
            default:
                setProperty("webdriver.chrome.driver", "c:/chromedriver.exe");
                driver = new ChromeDriver();
                break;
        }
        return driver;
    }
/*
    public static BrowserType getBrowserTypeByPreperty(){
        BrowserType type = null;
        String browserName = PropertyManager.
        return type;
    }
    */
}
