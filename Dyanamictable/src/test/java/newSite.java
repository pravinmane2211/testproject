

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
@Test
public class newSite {
	File file=new File("UserNamAndpass.xlsx");
	WebDriver driver;
	WebElement element;
	  
	public static String url="https://www.instantbusinesslist.in/";
	public void launch() {
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(url);
	}
@Test
public void methode() throws InterruptedException, IOException {
	FileInputStream fis= new FileInputStream(file);
    Workbook workbook =new XSSFWorkbook(fis);
    	
    Sheet sheet = workbook.getSheetAt(0);
    String Message;
    WebDriverWait wait= new WebDriverWait(driver,10);
	WebElement login= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='wilcity-login-btn']")));
	login.click();
    for (int i = 1; i <=sheet.getLastRowNum(); i++) {
    	System.out.println("iteration="+(i-1));
    	String username=sheet.getRow(i).getCell(0).getStringCellValue();
    	String password=sheet.getRow(i).getCell(1).getStringCellValue();
	
	Set handle= driver.getWindowHandles();
	Iterator<String>itr= handle.iterator();
	driver.switchTo().window(itr.next());
	
	//WebElement userName=driver.findElement(By.xpath("//a[@id='wilcity-login-btn']"))
	//userName.click();
	WebElement userName=driver.findElement(By.xpath("//*[@id='wilcity-wrapper-all-popup']/div[2]/div[2]/div/div/div/div[1]/div[2]/div[2]/div[1]/div/input"));
	userName.sendKeys(username);
	WebElement pass =driver.findElement(By.xpath("//*[@id='wilcity-wrapper-all-popup']/div[2]/div[2]/div/div/div/div[1]/div[2]/div[2]/div[2]/div/input"));
	pass.sendKeys(password);
	
	WebElement button=driver.findElement(By.xpath("//*[@id='wilcity-wrapper-all-popup']/div[2]/div[2]/div/div/div/div[1]/div[2]/div[2]/button"));
	button.click();
	WebElement Errormsg = driver.findElement(By.xpath("//div[contains(text(),'ERROR: Invalid username or password')]"));
	Thread.sleep(1000);
	if(Errormsg.isDisplayed()) {
		System.out.println("tset fail");
		Message="fail";
	}
	else {
		System.out.println("test pass");
		Message="pass";
		WebElement logout= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='wilcity-profile-nav-menu']/a/div)")));
		logout.click();
		driver.findElement(By.xpath("//*[@id='wilcity-profile-nav-menu']/div/div/ul/li[4]/a/span[2]")).click();
	}
	sheet.getRow(i).createCell(2).setCellValue(Message);
	FileOutputStream Fos=new FileOutputStream(file);
	workbook.write(Fos);

}
    workbook.close();
	driver.close();
}
}