package twoShared;


import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

public class TwoShared {
	
	static WebDriver driver=new FirefoxDriver();
		
	public static void openUrl() throws Throwable {
		driver.get("http://www.2shared.com/");
		System.out.println("Title of the window: "+driver.getTitle());
		System.out.println("Page uploaded successfully");
		driver.manage().window().maximize();
		Thread.sleep(3000);
		System.out.println("Window has maximized");
	}
		
	public static void exitUrl() {
		driver.quit();
	}
	
	public static String tooltip(String element,String xpath)
	{
		Actions cursor = new Actions(driver);
		WebElement object=driver.findElement(By.xpath(xpath));
		Action mouseHover = cursor.moveToElement(object).build();
		mouseHover.perform();
		String tool=object.getAttribute("title");
		System.out.println(tool);
		return tool;
	}
		
	public static void screenshot(String path) throws Throwable {
		File scrFile = (File) ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(path));
	}
 	
	public static String excelRead(int c,int r) throws Throwable {
		FileInputStream fi = new FileInputStream("C://Users//PC//Desktop//Auto//Excel1.xls");
		Workbook wwb1 = Workbook.getWorkbook(fi);
		Sheet wsht1 = wwb1.getSheet("Sheet");
		String s = wsht1.getCell(c, r).getContents();
		System.out.println(s);
		return s;
	}
 	
	public static void excelWrite() throws Throwable {
		FileOutputStream fn = new FileOutputStream("C://Users//PC//Desktop//Auto//Excel2.xls");
        	WritableWorkbook wwb1 = Workbook.createWorkbook(fn);
		WritableSheet wsht1 = wwb1.createSheet("Sheet", 0);
		
		System.out.println("The Error Messages are:");
        	String e1=("Error message when no data is entered: ");
        	System.out.print(e1);
        	Label em1 = new Label(0, 0, e1);
		wsht1.addCell(em1);
		login("","","");
		WebElement ErrMsg10 =driver.findElement(By.id("loginErrorMsg"));
		String error10=ErrMsg10.getText();
	    	Assert.assertEquals(excelRead(0,1),error10);
	    	Label l1 = new Label(1, 0, error10);
		wsht1.addCell(l1);

		String e2=("Error message when only email is entered: ");
	    	System.out.print(e2);
		Label em2 = new Label(0, 1, e2);
		wsht1.addCell(em2);
		login("a","","");
		WebElement ErrMsg20 =driver.findElement(By.id("loginErrorMsg"));
	    	String error20=ErrMsg20.getText();
	    	Assert.assertEquals(excelRead(0,2),error20);
	    	Label l2 = new Label(1, 1, error20);
		wsht1.addCell(l2);

		String e3=("Error message when wrong email and password is entered: ");
	    	System.out.print(e3);
	    	Label em3 = new Label(0, 2, e3);
		wsht1.addCell(em3);
		login("a","a","");
		WebElement ErrMsg30 =driver.findElement(By.id("loginErrorMsg"));
	    	String error30=ErrMsg30.getText();
	    	Assert.assertEquals(excelRead(0,3),error30);
	    	Label l3 = new Label(1, 2, error30);
		wsht1.addCell(l3);

		String e4=("Error message when password and confirm password entered dont match: ");
	    	System.out.print(e4);
	    	Label em4 = new Label(0, 3, e4);
		wsht1.addCell(em4);
		login("a@test.com","a","");
		WebElement ErrMsg40 =driver.findElement(By.id("loginErrorMsg"));
	    	String error40=ErrMsg40.getText();
	    	Assert.assertEquals(excelRead(0,4),error40);
	    	Label l4 = new Label(1, 3, error40);
		wsht1.addCell(l4);

		String e5=("Error message when already existing email is entered: ");
	    	System.out.print(e5);
	    	Label em5 = new Label(0, 4, e5);
		wsht1.addCell(em5);
		login("a@gmail.com","a","a");
		WebElement ErrMsg50 =driver.findElement(By.id("loginErrorMsg"));
	    	String error50=ErrMsg50.getText();
	    	Assert.assertEquals(excelRead(0,5),error50);
	    	Label l5 = new Label(1, 4, error50);
		wsht1.addCell(l5);
		
		System.out.println("Tooltips are:");
		String tt1="Tooltip for 2shared logo: ";
		System.out.print(tt1);
		Label to1 = new Label(0, 6, tt1);
		wsht1.addCell(to1);
		Label t1 = new Label(1, 6, tooltip(tt1,"html/body/div[1]/table/tbody/tr[1]/td/a/img"));
		wsht1.addCell(t1);
		
		String tt2="Tooltip for Upload button: ";
		System.out.print(tt2);
		Label to2 = new Label(0, 7, tt2);
		wsht1.addCell(to2);
		Label t2 = new Label(1, 7, tooltip(tt2,"html/body/div[1]/table/tbody/tr[2]/td/form/input[3]"));
		wsht1.addCell(t2);
					
		wwb1.write();
		
		int i = wsht1.getRows();
		System.out.println("No. of Rows: "+i);
		
		int j = wsht1.getColumns();
		System.out.println("No. of Columns: "+j);
		
		wwb1.close();
		fn.close();
	}
	
	public static void login(String email,String password, String confirm) throws Throwable	{
		WebElement Email =driver.findElement(By.id("login"));
		Email.clear();
		Email.sendKeys(new String[]{email});
		WebElement Pass =driver.findElement(By.id("password"));
		Pass.clear();
		Pass.sendKeys(new String[]{password});
		WebElement Pass2 =driver.findElement(By.id("password2"));
		Pass2.clear();
		Pass2.sendKeys(new String[]{confirm});
		WebElement Login=driver.findElement(By.xpath(".//*[@id='innerModalPopupDiv']/div/div/form/table/tbody/tr[4]/td/table/tbody/tr[1]/td[2]/button"));
		Login.click();
		Thread.sleep(3000);
	}
	
	public static void validLogin(String email,String password) throws Throwable {
		WebElement Acct=driver.findElement(By.linkText("I already have account"));
		Acct.click();
		Thread.sleep(3000);
		System.out.println("Logging in into the existing account");
		
		WebElement Email =driver.findElement(By.id("login"));
		Email.sendKeys(new String[]{email});
		WebElement Pass =driver.findElement(By.id("password"));
		Pass.sendKeys(new String[]{password});
		WebElement Login=driver.findElement(By.xpath(".//*[@id='innerModalPopupDiv']/div/div/form/table/tbody/tr[3]/td/span/table/tbody/tr[4]/td/button"));
		Login.click();
		Thread.sleep(3000);		
	}
    
	public static void logo(String Loc) throws Throwable {
		List<WebElement> images = driver.findElements(By.cssSelector("a>img"));
		int j=images.size();
		System.out.println("Total number of images: "+j);
		for(WebElement image : images){ 
		String s = image.getAttribute("src"); 
		if(s.contains("logo")) {
		System.out.println("The url of the logo: "+s);
		URL url=null;
		URLConnection con=null;
		int i;
		url=new URL(s);
		con=url.openConnection();
		File Logo=new File(Loc);
		BufferedInputStream bis=new BufferedInputStream(con.getInputStream());
		BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(Logo));
		while((i=bis.read())!=-1){
			bos.write(i);
		}
			bos.flush();
			bis.close();
			bos.close();
		}
		}
		System.out.println("Logo Downloaded");
	}
	public static void fileDelete(String file) throws Throwable
	{
		String PW=driver.getWindowHandle();
		
		WebElement Pic=driver.findElement(By.linkText(file));
		Pic.click();
		Thread.sleep(5000);
		System.out.println("File is opened in a new window");
		System.out.println("Title of the window: "+driver.getTitle());
				
		for(String CW:driver.getWindowHandles())
		{
			driver.switchTo().window(CW);
		}
		Thread.sleep(5000);
		System.out.println("Control is moved to the new window");
		
		WebElement Delete1 =driver.findElement(By.xpath(".//*[@id='overall']/tbody/tr[3]/td/table/tbody/tr/td[1]/form/table/tbody/tr[7]/td[1]/input"));
		Delete1.click();
		Thread.sleep(3000);
		System.out.println("Delete File button is clicked");
		System.out.println("Alert is displayed");
		
		Alert alert1=driver.switchTo().alert();
		System.out.println("Control is moved to the alert");
		System.out.println("Alert message: "+alert1.getText());
		alert1.accept();
		Thread.sleep(5000);
		System.out.println("OK button is clicked");
		
		System.out.println("Title of the window: "+driver.getTitle());
		System.out.println("File is successfully deleted");
		driver.close();
		System.out.println("File window is closed");
		
		driver.switchTo().window(PW);
		Thread.sleep(5000);
		System.out.println("Control is moved back to the My Files window");
	}
	
	
			
	public static void upload(String path) throws Throwable
	{
		WebElement Browse=driver.findElement(By.id("upField"));
		Browse.click();
		Thread.sleep(3000);
		System.out.println("Browse button is clicked");
		
		Robot robot=new Robot();
		robot.delay(3000);	
		System.out.println("Browse for the file to be uploaded");
		
		String convert=path.toUpperCase();
		for (int i =0;i<convert.length();i++)
		{
			if(convert.charAt(i)==':')
			{
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SEMICOLON);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				robot.delay(500);
			}
			else{
				robot.keyPress(convert.charAt(i));
				robot.keyRelease(convert.charAt(i));
				robot.delay(250);
			}
		}
		robot.delay(500);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.delay(1000);
		System.out.println("File has been selected");
			
		WebElement UploadButton =driver.findElement(By.xpath(".//*[@id='overall']/tbody/tr[2]/td/form/input[3]"));
		UploadButton.click();
		Thread.sleep(12000);
		System.out.println("Upload button is clicked");
				
		Alert alert=driver.switchTo().alert();
		System.out.println("Control is moved to the alert");
		System.out.println("Alert message: "+alert.getText());
		alert.accept();
		Thread.sleep(5000);
		
		/*System.out.println("Pop-up window appears with the confirmation message");
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.delay(5000);*/
		
		System.out.println("OK is clicked");
		Thread.sleep(2000);
		System.out.println("Title of the window: "+driver.getTitle());
		
		WebElement UploadLink =driver.findElement(By.linkText("Upload"));
		UploadLink.click();
		Thread.sleep(3000);		
	}
	
	public static void main(String[] args) throws Throwable
	{
		openUrl();	
		WebElement Blue =driver.findElement(By.xpath(".//*[@id='overall']/tbody/tr[2]/td/form/input[3]"));
		Blue.click();
		Thread.sleep(3000);
		System.out.println("Upload button is clicked");
		excelWrite();
		validLogin("ashwinign21@gmail.com","astute@123");
		System.out.println("Logged in successfully");	
		logo("C://Users//PC//Desktop//Auto//Logo.png");
		System.out.println("Upload the 1st file");
		upload("C:\\Users\\PC\\Desktop\\Auto\\Image01.jpg");
		System.out.println("Now upload the 2nd file");
		upload("Image02.jpg");
		System.out.println("Now upload the 3rd file");
		upload("Image03.jpg");
		WebElement MyFiles =driver.findElement(By.linkText("My files"));
		MyFiles.click();
		Thread.sleep(3000);
		System.out.println("My Files link is opened to view the uploaded files");
		System.out.println("Title of the window: "+driver.getTitle());
		List<WebElement> l=driver.findElements(By.partialLinkText(".jpg"));
		int i=l.size();
		System.out.println("Screenshot is taken after uploading all files");
		System.out.println("No of files present: "+i);
		screenshot("C://Users//PC//Desktop//Auto//Screenshot1.png");
		System.out.println("File to be deleted is clicked");
		fileDelete("Image01.jpg");
		System.out.println("File to be deleted is clicked");
		fileDelete("Image02.jpg");
		System.out.println("File to be deleted is clicked");
		fileDelete("Image03.jpg");
		driver.findElement(By.linkText("My files")).click();
		System.out.println("Page is refreshed");
		System.out.println("Screenshot is taken after all the files are deleted");
		screenshot("C://Users//PC//Desktop//Auto//Screenshot2.png");
		System.out.println("Deleted Files are no longer found in My Files page");
		Thread.sleep(3000);
		driver.quit();
		System.out.println("Firefox window is closed");
	}

}
