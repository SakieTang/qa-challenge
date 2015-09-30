package com.Flickr.Tests;

import Pages.LoginPage;
import Pages.UploadPage;
import Utilities.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 api_key = '6df8e4c2fbd1e8b5b99f420105d883f5'
 api_secret ='5326834e59e53d3e'
 user_id = '51151157@N04'                        #nsid
 email = 'tiqqqit@ymail.com'
 password = 'Loyal30924'
 tokenWrite = "72157656803302903-b68ae1df32adda23"    #perms = "write"
 username = "TiqqqiT"
 */
public class FlickrTestCases {
    public WebDriver driver;
    public LoginPage loginPage;
    public UploadPage uploadPage;
    //public WebDriverWait wait;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        this.driver = DriverFactory.getDriver(DriverFactory.BrowserType.CHROME);
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        uploadPage = PageFactory.initElements(driver, UploadPage.class);
    }

    @AfterClass(alwaysRun = true)
    public void teardown(){
        driver.close();
    }

    @Parameters({"userName","password"})
    @Test(enabled = true, groups = "login")
    public void testLoginPage(@Optional("tiqqqit@ymail.com") String userName, @Optional("Loyal30924") String password) throws InterruptedException {

        System.out.println("testLoginPage");
        //loginPage.loadPage();
        loginPage.loginPage(userName, password);
        TimeUnit.SECONDS.sleep(2);              //Can cause uploadPage needs to re-login
    }

    @Parameters({"userName","password","numOfImagesString","imageDir"})
    @Test(enabled = true, groups = "upload", dependsOnMethods = "testLoginPage")
    public void testLaunchLoadOneImage(@Optional("tiqqqit@ymail.com") String userName, @Optional("Loyal30924") String password,
                                       @Optional("1") String numOfImagesString, @Optional("C:\\FlickrImages") String imageDir) throws InterruptedException{

        System.out.println("testLaunchLoadOneImage");
        try {
            uploadPage.loadPage();
            //Handle the page does not login
            assertTrue(uploadPage.driver.getTitle().equals(uploadPage.getPageTitle()));
        }catch(AssertionError e){
            System.out.println("Login false, need to re-login.");
            loginPage.login(userName, password);
            TimeUnit.SECONDS.sleep(1);
            uploadPage.loadPage();
        }finally {
            Assert.assertEquals(driver.getTitle(), uploadPage.getPageTitle());
            TimeUnit.SECONDS.sleep(1);
            if (uploadPage.loadListOfImages(imageDir, numOfImagesString)){
                assertEquals(driver.getTitle(), "(Ready to upload) | Flickr: Upload photos and videos");
                uploadPage.clickActionPublishBtn();
                uploadPage.clickConfirmPublicBtn();
            }else{
                System.out.println("There is no image uploaded.");
            }

            TimeUnit.SECONDS.sleep(3 * Integer.parseInt(numOfImagesString));
        }
    }

    @Parameters({"userName","password","numOfImagesString","imageDir"})
    @Test(enabled = true, groups = "upload")
    public void testLaunchLoadTwoImageWithoutLogin(@Optional("tiqqqit@ymail.com") String userName, @Optional("Loyal30924") String password,
                                                   @Optional("2") String numOfImagesString, @Optional("C:\\FlickrImages") String imageDir) throws InterruptedException{

        System.out.println("testLaunchLoadTwoImageWithoutLogin");
        try {
            uploadPage.loadPage();
            //Handle the page does not login
            assertTrue(uploadPage.driver.getTitle().equals(uploadPage.getPageTitle()));
        }catch(AssertionError e){
            System.out.println("Login false, need to re-login.");
            loginPage.login(userName, password);
            TimeUnit.SECONDS.sleep(1);
            uploadPage.loadPage();
        }finally {
            Assert.assertEquals(driver.getTitle(),uploadPage.getPageTitle());
            TimeUnit.SECONDS.sleep(1);
            uploadPage.loadListOfImages(imageDir,numOfImagesString);
            assertEquals(driver.getTitle(), "(Ready to upload) | Flickr: Upload photos and videos");
            uploadPage.clickActionPublishBtn();
            uploadPage.clickConfirmPublicBtn();
            TimeUnit.SECONDS.sleep(3 * Integer.parseInt(numOfImagesString));
        }
    }

    @Parameters({"userName","password","numOfImagesString","imageDir"})
    @Test(enabled = true, groups = "upload", dependsOnMethods = "testLoginPage")
    public void testLaunchLoadTenImage(@Optional("tiqqqit@ymail.com") String userName, @Optional("Loyal30924") String password,
                                       @Optional("10") String numOfImagesString, @Optional("C:\\FlickrImages") String imageDir) throws InterruptedException {

        System.out.println("testLaunchLoadTenImage");
        try {
            uploadPage.loadPage();
            //Handle the page does not login
            assertTrue(uploadPage.driver.getTitle().equals(uploadPage.getPageTitle()));
        }catch(AssertionError e){
            System.out.println("Login false, need to re-login.");
            loginPage.login(userName, password);
            TimeUnit.SECONDS.sleep(1);
            uploadPage.loadPage();
        }finally {
            Assert.assertEquals(driver.getTitle(),uploadPage.getPageTitle());
            TimeUnit.SECONDS.sleep(1);
            uploadPage.loadListOfImages(imageDir,numOfImagesString);
            assertEquals(driver.getTitle(), "(Ready to upload) | Flickr: Upload photos and videos");
            uploadPage.clickActionPublishBtn();
            uploadPage.clickConfirmPublicBtn();
            TimeUnit.SECONDS.sleep(3 * Integer.parseInt(numOfImagesString));
        }
    }
}
