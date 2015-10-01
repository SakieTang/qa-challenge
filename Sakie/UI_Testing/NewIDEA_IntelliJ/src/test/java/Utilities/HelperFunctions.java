package Utilities;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

/**
 * Created by thinkpad on 9/27/2015.
 */
public class HelperFunctions {

    public static int getKeyEvent(char s){

        switch(s){
            case '0': return 48;//"KeyEvent.VK_0";
            case '1': return 49;//"KeyEvent.VK_1";
            case '2': return 50;//"KeyEvent.VK_2";
            case '3': return 51;//"KeyEvent.VK_3";
            case '4': return 52;//"KeyEvent.VK_4";
            case '5': return 53;//"KeyEvent.VK_5";
            case '6': return 54;// "KeyEvent.VK_6";
            case '7': return 55;//"KeyEvent.VK_7";
            case '8': return 56;//"KeyEvent.VK_8";
            case '9': return 57;//"KeyEvent.VK_9";
            case 'x': return 88;//"KeyEvent.VK_X";
            case 'm': return 77;//"KeyEvent.VK_M";
            case '_': return 523;//"KeyEvent.VK_UNDERSCORE";
            case '.': return 46;//"KeyEvent.VK_PERIOD";
            case 'j': return 74;//"KeyEvent.VK_J";
            case 'p': return 80;//"KeyEvent.VK_P";
            case 'g': return 71;//"KeyEvent.VK_G";
            //C:\FlickrImages
            case 'c': return 67;
            case 'C': return 67;
            case 'd': return 68;
            case 'D': return 68;
            //case ':': return 513;                 //need shift + semicolon
            case '\\': return 92;                   //case '/': return 47;
            case 'f': return 70;
            case 'F': return 70;
            case 'l': return 76;
            case 'i': return 73;
            case 'I': return 73;
            case 'k': return 75;
            case 'r': return 82;
            case 'a': return 65;
            case 'e': return 69;
            case 's': return 83;

        }
        return 0;
    }

    public static void typeNameByMouse(String imageName) throws InterruptedException {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        robot.delay(1000);
        System.out.println(imageName);
        char[] nameChars = imageName.toCharArray();
        for(char chr : nameChars){
            System.out.print(" " + chr + " ");
            if ( chr == ':'){
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SEMICOLON);
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }else {
                int keyCode = getKeyEvent(chr);
                robot.keyPress(keyCode);
            }
        }
        System.out.println();
        TimeUnit.SECONDS.sleep(3);
        robot.keyPress(KeyEvent.VK_ENTER);
    }

    public static int[] getRandomNumber(int minNum, int maxNum ){

        int numberCount = maxNum-minNum+1;
        int[] nums = new int[numberCount];
        Set set = new TreeSet();
        int i = 0;
        while (set.size() < numberCount) {
            int number = (int) (Math.random() * maxNum + minNum);
            if (!set.contains(number)) {
                set.add(number);
                nums[i++] = number;
            }
        }

        return nums;
    }

    public static String[] getRandomImages(String imageDir, int minNum, int maxNum){

        int numberCount = maxNum - minNum + 1;
        String[] randomImageList = new String[numberCount];
        String[] fileNameList;
        if (imageDir == null){
            System.out.println("get image folder from default");
            fileNameList = getImageFromFolder("C:\\FlickrImages");
        }else{
            System.out.println("get image folder from user/optional");
            fileNameList = getImageFromFolder(imageDir);
        }
        int[] randomNums = getRandomNumber(1, numberCount);

        int index = 0;
        for(int i:randomNums){
            randomImageList[index++] = fileNameList[i-1];
            System.out.println(" ++++++++++ " + i + " ========== " + fileNameList[i-1]);
        }

        //System.out.println(randomImageList.length);

        return randomImageList;
    }

    public static String[] getImageFromFolder(String folderDir){
        //File imageFolder = new File("D:\\NewIDEA_IntelliJ\\src\\test\\FlickrImages");
        //File imageFolder = new File(folderDir);
        File imageFolder;
        if (folderDir != null){
            imageFolder = new File(folderDir);
        }else{
            imageFolder = new File("C:\\FlickrImages");
        }
        File[] filesList = imageFolder.listFiles();
        String[] filesNameList = new String[filesList.length];

        int i =0;
        for(File f: filesList){
            if(f.isFile()){
                String dirOfFile = folderDir + '\\' + f.getName();
                filesNameList[i++] = dirOfFile;
                //System.out.println( dirOfFile );
            }
        }

        return filesNameList;
    }

    public static boolean setImagePath(String path){
        boolean flag = false;

        System.out.println("Please give me the absolute path for a folder that contains images?(or use default dir C:\\FlickrImages) y/n");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();

        System.out.println("Path is "+ name);

        while ( ! new File(name).exists() ){
            if (new File(name).exists()){
                break;
            }
            System.out.println("Not exist Dir, please try(C:\\FlickrImages): or use default one(Y/y)? ");
            name = sc.nextLine();
            if(name.toLowerCase().equals("y")){
                name = "C:\\FlickrImages";
            }
        }

        if(new File(name).exists()){
           flag = true;
        }else{
            System.out.println("The default file's(D:\\NewIDEA_IntelliJ\\src\\test\\flickr_imageS) path is not existing");
            System.out.println("Please move pictures to default path, then try it again");
            flag = false;
        }

        return flag;
    }
}
