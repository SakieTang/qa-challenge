package com.Flickr.Tests;

import java.io.IOException;

/**
 * Created by thinkpad on 9/27/2015.
 */
public class TestFlickr {

    public static void main(String args[]) throws InterruptedException {

        String dir1 = "C:\\FlickrImages\\2.75x75.jpg";
        String dir2 = "C:\\FlickrImages\\12.20mp.jpg";
        String[] dirA1 = dir1.split("\\\\");
        String[] dirA2 = dir2.split("\\\\");

        System.out.println(dirA1.length);
        System.out.println(dirA1[dirA1.length-1]);
        if (dirA1[dirA1.length-1].contains("mp")){
            System.out.println("contain mp");
        }else{
            System.out.println("not contain mp");
        }
        if (dirA2[dirA2.length-1].contains("mp")){
            System.out.println("contain mp");
        }else{
            System.out.println("not contain mp");
        }


/*
        if ( 66 == KeyEvent.VK_A ){
            System.out.println("k == VK_K");
        }else{
            System.out.println(('x' == KeyEvent.VK_X));
            System.out.println(KeyEvent.VK_0);      //48
            System.out.println(KeyEvent.VK_1);      //49
            System.out.println(KeyEvent.VK_9);      //57
            System.out.println(KeyEvent.VK_A);      //65
            System.out.println(KeyEvent.VK_B);      //66
            System.out.println("-----------");
            System.out.println(KeyEvent.VK_M);      //77
            System.out.println(KeyEvent.VK_P);      //80
            System.out.println(KeyEvent.VK_J);      //74
            System.out.println(KeyEvent.VK_G);      //71
            System.out.println(KeyEvent.VK_X);      //88
            System.out.println(KeyEvent.VK_MULTIPLY);   //106
            System.out.println(KeyEvent.VK_PERIOD); //46
            System.out.println(KeyEvent.VK_UNDERSCORE); //_ 523
            //C:\FlickrImages
            System.out.println(KeyEvent.VK_C);
            System.out.println(KeyEvent.VK_D);
            System.out.println(KeyEvent.VK_COMMA);
            System.out.println(KeyEvent.VK_BACK_SLASH);
            System.out.println(KeyEvent.VK_F);
            System.out.println(KeyEvent.VK_L);
            System.out.println(KeyEvent.VK_I);
            System.out.println(KeyEvent.VK_C);
            System.out.println(KeyEvent.VK_K);
            System.out.println(KeyEvent.VK_R);
            System.out.println(KeyEvent.VK_I);
            System.out.println(KeyEvent.VK_M);
            System.out.println(KeyEvent.VK_A);
            System.out.println(KeyEvent.VK_G);
            System.out.println(KeyEvent.VK_E);
            System.out.println(KeyEvent.VK_S);

        }
        System.out.println(KeyEvent.VK_Z - KeyEvent.VK_A + 1);
*/


        //imageFoder C:\Flickr_Sakie\API_TEST\flickr
/*
        File imageFolder = new File("D:\\NewIDEA_IntelliJ\\src\\test\\flickr_imageS");
        File[] filesList = imageFolder.listFiles();
        String[] filesNameList = new String[filesList.length];

        String imageList = "";

        int i =0;
        for ( File f : filesList ){
            if (f.isFile()){
                System.out.println("== " + f.getName() );
                imageList.concat(f.getName());
                imageList += f.getName() + ";";
                filesNameList[i++] = f.getName();
            }
        }
        System.out.println(imageList);
        String[] x = imageList.split(";");
        System.out.println(x.length);
        for(String y : x){
            System.out.println("-- " + y);
        }
        String[] y = x[0].split("");
        System.out.println(y[1]);

        String str = x[0];

        char[] ch = str.toCharArray();

        System.out.println(str);
        for(char c : ch){
            //System.out.println( c + " : "+ findKeyEvent(c));
            System.out.println( c + " : "+ HelperFunctions.getKeyEvent(c));
        }

        for(int j =0; j<30; j++){
            System.out.println( "number is(1-12) " + (int)(Math.random()*10) );
        }


        //array to store N random integers (0 - N-1) (0 - 10-1) ( 1 - 10 )
        int[] nums = new int[10];


        int numberCount = 10;
        int indexNum = 0;
        Set set = new TreeSet();
        while (set.size() < numberCount) {
            int number = (int) (Math.random() * 10 + 1);
            if (!set.contains(number)) {
                set.add(number);
                nums[indexNum++] = number;
            }
        }

        for(int j =0; j<10; j++){
            System.out.println("++++++++ "+ filesNameList[nums[j]]);
        }



        System.out.println("????????????????????????????????????");
        System.out.println("????????????????????????????????????");
        System.out.println("????????????????????????????????????");
        System.out.println("????????????????????????????????????");
        int manNum = HelperFunctions.getImageFromFolder("D:\\NewIDEA_IntelliJ\\src\\test\\flickr_imageS").length;
        String[] testimage = HelperFunctions.getRandomImages(1, 13);

        */


        /*
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        robot.delay(500);
        for (String imageName : testimage){
            char[] nameChars = imageName.toCharArray();
            for(char chr : nameChars){
                int keycode = HelperFunctions.getKeyEvent(chr);
                robot.keyPress(keycode);
            }
        }
        */


    }

    public static void callPython(){


        Process proc = null;
        try {
            System.out.println("Before Run Python");
            proc = Runtime.getRuntime().exec("python D:\\NewIDEA_IntelliJ\\src\\test\\java\\Utilities\\sakie.py"); //D:\NewIDEA_IntelliJ\src\test\java\Utilities
            System.out.println("After Run Python");
            proc.waitFor();
            System.out.println("finish Run Python??");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
        try{

            String prg = "import sys\nprint int(sys.argv[1])+int(sys.argv[2])\n";
            BufferedWriter out = new BufferedWriter(new FileWriter("test1.py"));
            out.write(prg);
            out.close();
            int number1 = 10;
            int number2 = 32;
            Process p = Runtime.getRuntime().exec("python test1.py "+number1+" "+number2);
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int ret = new Integer(in.readLine()).intValue();
            System.out.println("value is : "+ret);
        }catch(Exception e){}
        */
    }


    public static String findKeyEvent(char s){
        String str = "abc";
        String returnValue;
        switch(s){
            case '0': return  "KeyEvent.VK_0";
            case '1': return "KeyEvent.VK_1";
            case '2': return "KeyEvent.VK_2";
            case '3': return "KeyEvent.VK_3";
            case '4': return "KeyEvent.VK_4";
            case '5': return "KeyEvent.VK_5";
            case '6': return "KeyEvent.VK_6";
            case '7': return "KeyEvent.VK_7";
            case '8': return "KeyEvent.VK_8";
            case '9': return "KeyEvent.VK_9";
            case 'x': return "KeyEvent.VK_X";
            case 'm': return "KeyEvent.VK_M";
            case '_': return "KeyEvent.VK_UNDERSCORE";
            case '.': return "KeyEvent.VK_PERIOD";
            case 'j': return "KeyEvent.VK_J";
            case 'p': return "KeyEvent.VK_P";
            case 'g': return "KeyEvent.VK_G";
        }
        return str;
    }



}
