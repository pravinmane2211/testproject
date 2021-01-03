package table.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class excelRw {

	  WebDriver driver=null;
      WebElement element =null;
      Sheet sheet=null;
      static int i=0;
	public static void TakeScrenshot(WebDriver driver,String Destination) throws IOException{
		TakesScreenshot srcshot=((TakesScreenshot)driver);
		File srcFile= srcshot.getScreenshotAs(OutputType.FILE);
		File DestFile= new File(Destination);
		FileUtils.copyFile(srcFile, DestFile);
		
	}
	public static String url = "http://demo.sentrifugo.com/index.php/";
	public static void main(String args[]) throws IOException{
	
		excelRw ex=new excelRw();
		File file = new File("EMPID.xlsx");
		FileInputStream fio =new FileInputStream(file);
		Workbook workbook=new XSSFWorkbook(fio);
		Sheet sheet= workbook.getSheetAt(0);
		for(i=1;i<= sheet.getLastRowNum();i++){
		int id = (int)workbook.getSheetAt(0).getRow(i).getCell(0).getNumericCellValue();
	    String Testcase= sheet.getRow(i).getCell(1).getStringCellValue();
		System.out.println(id);
		switch (id) {
		case 1:
			ex.loadDriver();
			sheet.getRow(i).createCell(2).setCellValue("E:\\selenium image\\loaddriver.jpg");
			sheet.getRow(i).createCell(3).setCellValue("test case pass");
			FileOutputStream Fos=new FileOutputStream(file);
			workbook.write(Fos);
			break;
		case 2:
			ex.method1();
		    sheet.getRow(i).createCell(2).setCellValue("E:\\selenium image\\login.jpg");
			sheet.getRow(i).createCell(3).setCellValue("done");
			FileOutputStream Fos1=new FileOutputStream(file);
			workbook.write(Fos1);
		default:
			
			break;
		}
		
	
		
	 }
		
		
		}
			
		
      private void loadDriver() throws IOException {
    	WebDriverManager.chromedriver().setup();
  		driver= new ChromeDriver();
  		driver.manage().timeouts().implicitlyWait(1000,TimeUnit.SECONDS);
  		driver.manage().window().maximize();
  		driver.get(url);
  		TakeScrenshot(driver,"E:\\selenium image\\loaddriver.jpg");
      }
	private void method1() throws IOException {
		String url2="http://demo.sentrifugo.com/index.php/index";
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("superadmin@.com");
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("sentrif");
		driver.findElement(By.xpath("//input[@id='loginsubmit']")).click();

		if(url.equals(url2)) {
			TakeScrenshot(driver,"E:\\selenium image\\login.jpg");
			sheet.getRow(i).createCell(2).setCellValue("E:\\selenium image\\login.jpg");
			sheet.getRow(i).createCell(3).setCellValue("test case pass");
			
		}
		else {
			TakeScrenshot(driver,"E:\\selenium image\\loginfail.jpg");
			
			
		}

		
	}

}