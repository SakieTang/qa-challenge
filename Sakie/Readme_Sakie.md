UI Testing
 - Java, Selenium, TestNG
 - IntelliJ IDEA open my UI coding (NewIDEA_IntelliJ)
 - Java
    src.test.java
     |
     |--- com
     |     |--- Flickr.Tests
     |               |---  FlickrTestCases.java
     |--- Pages
     |       |--- BasePage.java
     |       |--- LoginPage.java
     |       |--- UploadPage.java
     |
     |--- Utilities
     |        |--- DriverFactory
     |        |--- HelperFunctions
     |
     |--- testng.xml

    + src.test.java
	- com
		-Flickr.Tests
			-FlickrTestCases.java
	- Pages
		- BasePage.java
		- LoginPage.java
		- UploadPage.java
	- Utilities
		- DriverFactory
		- HelperFunctions
	- testng.xml


Prepare:
 1. Image dir:
    - Customer : 
	1. change in testng.xml (\NewIDEA_IntelliJ\src\test\testng.xml)
	   <parameter name="imageDir" value="C:\FlickrImages"/>
           <!-- imageDir's name can only contains chars [0-9.xflickrmagesjdp\] -->
	2. change in FlickrTestCases.java ( \NewIDEA_IntelliJ\src\test\java\com\Flickr\Tests\FlickrTestCases.java)
	   @Optional("C:\\FlickrImages") String imageDir
     - Default :
	1. Using default ( C:\FlickrImages" )

 2. Put source images to Image dir :
     - images under this dir : \NewIDEA_IntelliJ\src\test\FlickrImages

 3. Delete existing images:
	Url: https://www.flickr.com/cameraroll
	email = 'tiqqqit@ymail.com'
 	password = 'Loyal30924'

!!NOTE!!: If add any new image, the name can only contains chars [0-9.xflickrmagesjdp\]

Run :
 - Changed the image dir in xml, run xml
 - Changed the image dir in FlickrTestCases.java, run FlickrTestCases.java
 - No changed, run FlickrTestCases.java


Back-End(RestAPI) testing:
 - Python
 - PyCharm
 
Prepare:
 - download token.txt, put it in the same dir as Sakie.py
 - Image dir: base on the UI testing Image dir change this one in line 25 (ORIGINAL_DIR = 'C:\FlickrImages')
 - Download dir: change it in line 24 (DOWNLOAD_DIR = 'C:\downloads' )
