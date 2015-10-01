package Pages;

import Utilities.HelperFunctions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.concurrent.TimeUnit;

/**
 * Created by Sakie on 9/26/2015.
 */
public class UploadPage extends BasePage{

    @FindBy(id = "login-username")              WebElement field_EmailLogin;     //css="#login-username"
    @FindBy(id = "login-passwd")                WebElement field_PasswordLogin;    //css="#login-passwd"
    @FindBy(id = "choose-photos-and-videos")    WebElement uploadFileBtn;
    @FindBy(id = "button-add-photos")           WebElement uploadCornerBtn;
    //@FindBy(partialLinkText = "choose-photos-and-videos") WebElement uploadFileBtn;
    @FindBy(id = "action-publish")              WebElement actionPublishBtn;
    @FindBy(id = "confirm-publish")             WebElement confirmPublishBtn;
    //<input id="action-remove-errors" type="button" value="Remove one bad file" class="Butt DeleteButt">
    @FindBy(id = "action-remove-errors")        WebElement removeErrorsBtn;


    String currentWindow;
    WebDriver windowFile;
    String readyLoadTitle;
    String verifyingTitle;

    public UploadPage(WebDriver driver) {
        super(driver);
        this.Page_URL = "https://www.flickr.com/photos/upload/";
        this.Page_Title = "Flickr: Upload photos and videos";
        this.readyLoadTitle = "(Ready to upload) | Flickr: Upload photos and videos";
        this.verifyingTitle = "Verifying... | Flickr: Upload photos and videos";
    }

    public void loadPage(){
        //driver.get("https://www.flickr.com/photos/upload/");
        driver.get(getPageURL());
    }

    public void clickUploadPhotoBtn() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(uploadFileBtn));
        currentWindow = driver.getWindowHandle();
        uploadFileBtn.click();
        TimeUnit.SECONDS.sleep(3);
    }

    public void clickUploadBtnCorner() throws InterruptedException {
        //wait.until(ExpectedConditions.elementToBeClickable(uploadCornerBtn));
        clickElement(uploadCornerBtn);
        //uploadCornerBtn.click();
        //TimeUnit.SECONDS.sleep(1);
    }

    public void clickActionPublishBtn() throws InterruptedException {
        while(!checkActionPublicBtnEnabled() && driver.getTitle().equals(readyLoadTitle)){
            TimeUnit.MILLISECONDS.sleep(500);
        }
        clickElement(actionPublishBtn);
    }

    public boolean checkActionPublicBtnEnabled(){
        return actionPublishBtn.isDisplayed();
    }

    public void clickConfirmPublicBtn() throws InterruptedException {
        while (!checkConfirmPublicBtnEnabled()){
            TimeUnit.MILLISECONDS.sleep(500);
        }
        clickElement(confirmPublishBtn);
    }

    public boolean checkConfirmPublicBtnEnabled(){
        return confirmPublishBtn.isDisplayed();
    }

    public void clickRemoveErrorsBtn() throws InterruptedException {
        while(!removeErrorsBtn.isEnabled()){
            TimeUnit.MILLISECONDS.sleep(500);
        }
        clickElement(removeErrorsBtn);
    }

    public boolean checkRemoveErrorsBtnEnabled(){
        return removeErrorsBtn.isDisplayed();
    }

    public String getCurrentWindowHandle(){
        currentWindow = driver.getWindowHandle();
        return currentWindow;
    }

    public boolean loadListOfImages(String imageDir, String numOfImagesString) throws InterruptedException {

        boolean flag = false;
        int countSucUploadImage = 0;
        int numOfImages = Integer.parseInt(numOfImagesString);
        String[] imagesName = HelperFunctions.getRandomImages(imageDir, 1, numOfImages);

        for (int i = 0; i < numOfImages; i++){

            TimeUnit.SECONDS.sleep(3);
            this.clickUploadBtnCorner();

            String imageName = imagesName[i];
            //imageName = "6.16x16.jpg";
            HelperFunctions.typeNameByMouse(imageName);

            TimeUnit.SECONDS.sleep(5);
            extraWaitTimeForLargeImage(imageName);

            if(!handleErrorImage(imageName)){
                countSucUploadImage++;
            }
            TimeUnit.SECONDS.sleep(2);
        }
        if (countSucUploadImage > 0){
            flag = true;
        }else {
            flag = false;
        }
        return flag;
    }

    public void extraWaitTimeForLargeImage(String imageName) throws InterruptedException {

        String[] names = imageName.split("\\\\");

        if(names[names.length-1].contains("mp")){
            System.out.println(imageName + " is a big image, so need extra time to upload!");
            TimeUnit.SECONDS.sleep(6);
        }
    }

    public boolean handleErrorImage( String imageName ) throws InterruptedException {

        boolean flag = false;
        //Handle an error image and remove it
        if ((driver.getTitle().equals(verifyingTitle) && this.checkRemoveErrorsBtnEnabled())) {
            System.out.println("Cannot upload " + imageName);
            System.out.println("Flickr does not supoort : " + imageName.split("\\.")[1]);
            flag = true;
            TimeUnit.SECONDS.sleep(2);
            //System.out.println("removeErrorsBtn.isEnabled() : " + removeErrorsBtn.isEnabled());
            //removeErrorsBtn.click();
            this.clickRemoveErrorsBtn();
            TimeUnit.SECONDS.sleep(2);
        }
        return flag;
    }

    public void getUploadTime( String imageName ) throws InterruptedException {

        long uploadTime = 0;
        long startUploadTime = System.currentTimeMillis();
        while (driver.getTitle().equals(readyLoadTitle) ){

            System.out.println("not finished yet");
            if ((driver.getTitle().equals(verifyingTitle) && this.checkRemoveErrorsBtnEnabled())){
                handleErrorImage( imageName );
            }
        }
        if (this.checkRemoveErrorsBtnEnabled()){
            uploadTime = 0;
            System.out.println( imageName + " upload is false!" );
        }else{
            long finishUploadTime = System.currentTimeMillis();
            uploadTime = finishUploadTime - startUploadTime;
            System.out.println( imageName + " upload is successful. " + "It took " + uploadTime + " milliseconds.");
        }
    }

    /*
    public void switchtoWindow() throws InterruptedException {
        Set<String> handles = driver.getWindowHandles();
        Iterator<String> it = handles.iterator();

        String currentHandle = driver.getWindowHandle();
        String windowTitle = "Open";
        boolean flag = false;

        System.out.println("TESTING "+it.hasNext());

        System.out.println("111111"+it.next());
        System.out.println("111111TESTING "+it.hasNext());
    */

        /*
        String popup = null;
        String popupText = "aaaaaaaaa";
        try{
            for(String str : driver.getWindowHandles()){
                driver.switchTo().window(str);
                Thread.sleep(5000);

                if(driver.getPageSource().contains(popupText)){
                    System.out.println("Popup has the following text: "+popupText);
                    popup = driver.getWindowHandle();
                    driver.switchTo().window(popupHandle);
                //do all your actions
                }
            }
        }catch (Exception e){
//
        }
        */


        /*
        while(it.hasNext()){
            //System.out.println("1111###################");
            //System.out.println(it.next());

            String windowHandle = it.next();
            if( currentWindow.equals(windowHandle) ) continue;

                System.out.println("1111###################");
                //System.out.println(it.next());
                windowFile = driver.switchTo().window(windowHandle);
                System.out.println("title,url = " + windowFile.getTitle() + "," + windowFile.getCurrentUrl());
                System.out.println("2222###################");


        }
        */

        //String strMainHandler = driver.getWindowHandle();
        //driver.switchTo().window(strMainHandler);
        /*
        String printTitle = windowFile.getTitle();
        System.out.print(printTitle);
        WebElement fileInput = windowFile.findElement(By.name("uploadfile"));
        fileInput.sendKeys("C:/Flickr_Sakie/API_TEST/7.1440x900..jpg");      //C:\Flickr_Sakie\API_TEST\flickr
        */




        /*
        while(it.hasNext()){
            if ( getCurrentWindowHandle()== it.next() )
                continue;
            window = driver.switchTo().window(it.next());
            TimeUnit.SECONDS.sleep(10);
            System.out.println("#####################");
            System.out.println(window.getTitle());
            System.out.println("#####################");
         }



        //driver.switchTo().defaultContent();
        //driver.switchTo().frame()
        //driver.switchTo().window(windowName);
        */

    //}


}
