
package nbpts_admin_login;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class Admin_login {

	String[][] data=null; //Null becz we going to take data  from excel sheet
	WebDriver driver;

	@DataProvider(name= "logindata")
	public String[][] loginDataprovider() throws BiffException, IOException
	{
		data=getexceldata(); //here we getting data from excel sheet
		return data;
	}

	@AfterTest
	public void aftertest()
	{
		driver.quit();
	}


	public String[][] getexceldata() throws BiffException, IOException
	{
		FileInputStream excel=new FileInputStream("C:\\Users\\NAZEER\\Documents\\logintest12.xls");

		Workbook workbook= Workbook.getWorkbook(excel);

		Sheet sheet =workbook.getSheet(0);

		int rowcount = sheet.getRows();
		int columnscount=sheet.getColumns();

		String testdata[][]=new String[rowcount-1][columnscount];

		for (int i =1; i<rowcount; i++) {

			for (int j=0; j<columnscount; j++) {

				testdata[i-1][j]=sheet.getCell(j,i).getContents();
			}

		}
		return testdata;

	}

	@BeforeTest
	public void beforetest()
	{
		System.setProperty("webdriver.chromedriver", "C:\\Users\\NAZEER\\Desktop\\chromedriver.exe");

		driver =new ChromeDriver();

	}


	@Test(dataProvider ="logindata")
	public void loginwithcorrectuserrname(String uname,String pword) throws InterruptedException
	{

		driver.get("https://pmtplan-dev1.nbptsdev.com/signin");

		WebElement username=driver.findElement(By.id("username"));
		username.sendKeys(uname);

		WebElement password =driver.findElement(By.id("candidateId"));
		password.sendKeys(pword);

		WebElement loginbutton=driver.findElement(By.xpath("/html/body/div/div[3]/div[2]/form/button"));
		loginbutton.click();
		Thread.sleep(2000);



	}

}


