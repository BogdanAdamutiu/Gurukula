package Testing;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Actions {

	private static final Logger log = Logger.getLogger(Loggin.class);	
	FirefoxDriver Mozila = new FirefoxDriver();
	String Results = "";
	String BranchCheck = "";
	String CodeCheck = "";
	String StaffCheck = "";
	int NrOfResults = 0;
	int Error = 0;
	
	@Test	
	public void OpenBrowser() {
		Mozila.manage().window().maximize();;
		Mozila.navigate().to("http://192.168.178.227:8080/");
		Mozila.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		log.info("Web application launched");
		if (Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div[2]/h1")).isDisplayed()) {
			Reporter.log("Application lauched successfully");
		}
		else {
			Reporter.log("Application didn't lauched successfully");
		}			
	}
	
	@Test (dependsOnMethods = {"OpenBrowser"})
	@Parameters ({"User", "Password" , "RememberMe"})
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
			Reporter.log("Sign in successful");
		}
		else {
			Reporter.log("Sign in didn't work");
		}
		Thread.sleep(1500);
	}
	
	@Test (dependsOnMethods = {"Login"})
	public void NavigateToBranch() throws InterruptedException {
		Mozila.findElement(By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[2]/a/span/b")).click();
		log.info("Click action performed on Entities drop down menu");
		Mozila.findElement(By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[2]/ul/li[1]/a/span[2]")).click();
		Thread.sleep(1000);
		log.info("Click action performed on Branch");
		if (Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/h2")).getAttribute("innerText").equalsIgnoreCase("Branches")) {
			Reporter.log("Opened Branch page successfully");
		}
		else {
			Reporter.log("Branch page did not open");
		}
	}
	
	@Test (dependsOnMethods = {"Login" , "CreateBranch"})
	public void NavigateToStaff() throws InterruptedException {
		Mozila.findElement(By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[2]/a/span/span[2]")).click();
		log.info("Click action performed on Entities drop down menu");
		Mozila.findElement(By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[2]/ul/li[2]/a/span[2]")).click();											
		Thread.sleep(1000);
		log.info("Click action performed on Staff");
		if (Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/h2")).getAttribute("innerText").equalsIgnoreCase("Staffs")) {
			Reporter.log("Opened Staff page successfully");
		}
		else {
			Reporter.log("Staff page did not open");
		}
	}
	
	@Test (dependsOnMethods = {"Login"})
	public void NavigateToAccountInformation() throws InterruptedException {
		Mozila.findElement(By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[3]/a/span/span[2]")).click();
		log.info("Click action performed on Account drop down menu");
		Mozila.findElement(By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[3]/ul/li[1]/a/span[2]")).click();
		Thread.sleep(500);
		log.info("Click action performed on Settings");
		if (Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/h2")).getAttribute("innerText").equalsIgnoreCase("User settings for [admin]")) {
			Reporter.log("Opened Account Information page successfully");
		}
		else {
			Reporter.log("Account Information page did not open");
		}
	}
	
	@Test (dependsOnMethods = {"Login"})
	public void NavigateToPasswordChange() throws InterruptedException {
		Mozila.findElement(By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[3]/a/span/span[2]")).click();
		log.info("Click action performed on Account drop down menu");
		Mozila.findElement(By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[3]/ul/li[2]/a/span[2]")).click();
		Thread.sleep(500);
		log.info("Click action performed on Password");
		if (Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/h2")).getAttribute("innerText").equalsIgnoreCase("Password for [admin]")) {
			Reporter.log("Opened Password page successfully");
		}
		else {
			Reporter.log("Password page did not open");
		}
	}

	@Test (dependsOnMethods = {"NavigateToBranch"})
	@Parameters ({"Branch" , "Code"})
	public void CreateBranch(String Branch, String Code) throws InterruptedException, IOException {
		if (Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[3]/button[1]")).isDisplayed()) {
			Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[3]/button[1]")).click();
			log.info("Click action performed on Cancel button");
		}
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[1]/button")).click();
		Thread.sleep(1000);
		log.info("Click action performed on Create a new Branch button");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/input")).sendKeys(Branch);
		log.info("Name entered in the Branch Name text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[3]/input")).sendKeys(Code);
		log.info("Code entered in the Branch Code text box");
		
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/div/p[1]")).isDisplayed(), "Branch name " + Branch + " can't be set as a branch name because it doesn't respect the format standard");
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/div/p[2]")).isDisplayed(), "Branch name " + Branch + " can't be set as a branch name because it doesn't respect the format standard");
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/div/p[3]")).isDisplayed(), "Branch name " + Branch + " can't be set as a branch name because it doesn't respect the format standard");
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/div/p[4]")).isDisplayed(), "Branch name " + Branch + " can't be set as a branch name because it doesn't respect the format standard");
		log.info("The format of the branch name was checked and it is according to the standard");

		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[3]/div/p[1]")).isDisplayed(), "Branch code " + Code + " can't be set as a branch code because it doesn't respect the format standard");
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[3]/div/p[2]")).isDisplayed(), "Branch code " + Code + " can't be set as a branch code because it doesn't respect the format standard");
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[3]/div/p[3]")).isDisplayed(), "Branch code " + Code + " can't be set as a branch code because it doesn't respect the format standard");
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[3]/div/p[4]")).isDisplayed(), "Branch code " + Code + " can't be set as a branch code because it doesn't respect the format standard");
		log.info("The format of the branch code was checked and it is according to the standard");

		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[3]/button[2]")).click();
		log.info("Click action performed on Save button");
		Thread.sleep(1000);
		
		Mozila.findElement(By.xpath("//*[@id=\"searchQuery\"]")).clear();
		log.info("Cleared the search text box");
		
		Mozila.findElement(By.xpath("//*[@id=\"searchQuery\"]")).sendKeys(Branch);
		log.info("Entered the name of the newly created branch in the search text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[2]/form/button")).click();
		Thread.sleep(1000);
		log.info("Click action performed on Search a Branch button");
		
		//check that there is at least one branch with the given name and code
		Results = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody")).getAttribute("childElementCount");
		NrOfResults = Integer.parseInt(Results);
		Assert.assertTrue(NrOfResults > 0 , "The branch with the name "+ Branch +" and code "+ Code +" hasn't been created");
		
		for (int i = 1; i <= NrOfResults; i++) {
			BranchCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[2]")).getAttribute("innerText");
			CodeCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[3]")).getAttribute("innerText");
			if (BranchCheck.equalsIgnoreCase(Branch) && CodeCheck.equalsIgnoreCase(Code)) {
				Reporter.log("The branch with the name "+ Branch +" and code "+ Code +" has been created");
			}
			else {
				Reporter.log("The branch with the name "+ Branch +" and code "+ Code +" hasn't been created");
			}
		}
	}

	@Test (dependsOnMethods = {"NavigateToStaff"})
	@Parameters ({"Staff","AssigningBranch"})
	public void CreateStaff(String Staff, String AssigningBranch) throws InterruptedException, IOException {	
		if (Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[3]/button[1]")).isDisplayed()) {
			Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[3]/button[1]")).click();
			log.info("Click action performed on Cancel button");
		}

		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[1]/button")).click();
		Thread.sleep(1500);
		log.info("Click action performed on Create a new Staff button");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/input")).sendKeys(Staff);
		Thread.sleep(500);
		log.info("Name entered in the Staff Name text box");
		
		//check that the name of the staff respects the standard
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/div/p[1]")).isDisplayed(), "Staff name "+ Staff +" can't be set as a branch name because it doesn't respect the format standard");
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/div/p[2]")).isDisplayed(), "Staff name "+ Staff +" can't be set as a branch name because it doesn't respect the format standard");
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/div/p[3]")).isDisplayed(), "Staff name "+ Staff +" can't be set as a branch name because it doesn't respect the format standard");
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/div/p[4]")).isDisplayed(), "Staff name "+ Staff +" can't be set as a branch name because it doesn't respect the format standard");
		log.info("The format of the staff name was checked and it is according to the standard");
				
		//create a select that contains all the available branches
		Select SelectBranch = new Select(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[3]/select")));		

		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[3]/select")).click();
		Thread.sleep(500);	
		log.info("Branch list has been opened");

		SelectBranch.selectByVisibleText(AssigningBranch);
		log.info("Desired branch has been selected");

		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[3]/button[2]")).click();
		Thread.sleep(1500);
		log.info("Click action performed on Save button");
		
		//check that there is at least one staff in the staff list after the save button has been pressed
		Results = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody")).getAttribute("childElementCount");
		NrOfResults = Integer.parseInt(Results);
		Assert.assertTrue(NrOfResults > 0 , "The Staff with the name "+ Staff +" hasn't been created");
		
		for (int i = 1; i <= NrOfResults; i++) {
			StaffCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[2]")).getAttribute("innerText");
			BranchCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[3]")).getAttribute("innerText");
			if (StaffCheck.equalsIgnoreCase(Staff) && BranchCheck.equalsIgnoreCase(AssigningBranch)) {
				Reporter.log("The staff with the name "+ Staff +" and assigning branch "+ AssigningBranch +" has been created");
			}
		}
	}	

}
