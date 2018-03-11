package Testing;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Actions {

	private static final Logger log = Logger.getLogger(Loggin.class);	
	FirefoxDriver Mozila = new FirefoxDriver();
	
	@Test	
	public void OpenBrowser() {
		Mozila.manage().window().maximize();;
		Mozila.navigate().to("http://192.168.178.227:8080/");
		Mozila.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		log.info("Web application lanuched");
		if (Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div[2]/h1")).isDisplayed()) {
			Reporter.log("Application lauched successfully | ");
		}
		else {
			Reporter.log("Application didn't lauched successfully | ");
		}			
	}
	
	@Test (dependsOnMethods = {"OpenBrowser"})
	@Parameters({ "User", "Password" , "RememberMe"})
	public void Login(String User, String Password, Boolean RememberMe) throws InterruptedException, IOException {
		Mozila.findElement(By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[1]/a[2]/span[2]")).click();
		Thread.sleep(1000);
		log.info("Click on Home button");
		
		//click on login
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div[2]/div/div[1]/a")).click();	
		Thread.sleep(1000);
		log.info("Click on login button");
		
		//enter user name and password
		Mozila.findElement(By.xpath("//*[@id=\"username\"]")).clear();
		log.info("Cleared the Username text box");
		Mozila.findElement(By.xpath("//*[@id=\"password\"]")).clear();
		log.info("Cleared the Password text box");
		Mozila.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys(User);
		log.info("Username entered in the Username text box");
		Mozila.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys(Password);
		log.info("Password entered in the Password text box");
		if (!RememberMe) {
			Mozila.findElement(By.xpath("//*[@id=\"rememberMe\"]")).click();
			log.info("RememberMe check box is unchecked");
		}
		else {
			log.info("RememberMe check box is checked");
		}
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/button")).click();
		Thread.sleep(2000);
		log.info("Click action performed on Authentificate button");
		
		//check that we are logged in
		if (Mozila.findElement(By.xpath("/html/body/div[2]/nav/div/div[2]/ul")).getAttribute("childElementCount").equalsIgnoreCase("4")) {
			Reporter.log("Sign in successful | ");
		}
		else {
			Reporter.log("Sign in didn't work | ");
		}
		Thread.sleep(1500);
	}
	
}
