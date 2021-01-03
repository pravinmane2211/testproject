package table.com;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ExcelReadWrite {
	File file=new File("pass.xlsx");
	WebDriver driver =null;
	WebElement element=null;
	public static String Url="https://opensource-demo.orangehrmlive.com/";

	@Test
	public void launch() throws FileNotFoundException {
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.get(Url);
		driver.manage().window().maximize();
	}
	
	@Test(dependsOnMethods = {"launch"})
		public void signin() throws IOException {
		FileInputStream fis= new FileInputStream(file);
	    Workbook workbook =new XSSFWorkbook(fis);
	    	
	    Sheet sheet = workbook.getSheetAt(0);
	    String Message;
	    for (int i = 1; i <=sheet.getLastRowNum(); i++) {
	    	System.out.println("iteration="+(i-1));
	    	String username=sheet.getRow(i).getCell(0).getStringCellValue();
	    	String password=sheet.getRow(i).getCell(1).getStringCellValue();
	    	driver.findElement(By.xpath("//input[@id='txtUsername']")).sendKeys(username);
	    	driver.findElement(By.xpath("//input[@id='txtPassword']")).sendKeys(password);
	    	driver.findElement(By.id("btnLogin")).click();
	    	
	    String url="https://opensource-demo.orangehrmlive.com/index.php/dashboard";
	    String url1=driver.getCurrentUrl();
	    if(url.equals(url1)) {
	    	Message="passed";
	    	driver.findElement(By.xpath("//a[@id='welcome']")).click();
	    	WebDriverWait wait=new WebDriverWait(driver,120);
	    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='welcome-menu']/ul/li[2]/a[@href=\"/index.php/auth/logout\"]"))).click();
	    }
	    else {
			Message="failed";
		}
	    	System.out.println(Message);
	    	//div[@id='welcome-menu']/ul/li[2]/a[@href=\"/index.php/auth/logout\"]
	    	sheet.getRow(i).createCell(2).setCellValue(Message);
	    	FileOutputStream Fos=new FileOutputStream(file);
	    	workbook.write(Fos);
	    }
	    workbook.close();
    	driver.close();
	    	
	    	
		}
	

}
