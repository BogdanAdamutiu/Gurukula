package Testing;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Actions {

	private static final Logger log = Logger.getLogger(Loggin.class);	
	FirefoxDriver Mozila = new FirefoxDriver();
	String Results = "";
	String SecondResults = "";
	String Status = "";
	String BranchCheck = "";
	String CodeCheck = "";
	String IDCheck = "";
	String StaffCheck = "";
	String ViewedBranch = "";
	String ViewedCode = "";
	String BranchSecondCheck = "";
	String CodeSecondCheck = "";
	String StaffSecondCheck = "";
	String FirstNameCheck = "";
	String LastNameCheck = "";
	String EmailCheck = "";
	String LanguageCheck = "";
	int NrOfResults = 0;
	int SecondNrOfResults = 0;
	int Error = 0;
	int Matched = 0;
	int Decrement = 0;
	int DeletedStaffs = 0;
	
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
	
	@DataProvider(name = "Authentication")	 
	public static Object[][] credentials() {
		return new Object[][] { { "admin", "" , "no"} , {"" , "admin" , "no"} , {"admin" , "admin" , "da"} };	 
	}	 
	  
	@Test (dependsOnMethods = {"OpenBrowser"} , dataProvider = "Authentication")
	public void Login(String User, String Password, String RememberMe) throws InterruptedException, IOException {
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
		
		Assert.assertTrue(RememberMe.equalsIgnoreCase("yes") || RememberMe.equalsIgnoreCase("no") , "In order to use remeber me functionality a value of \"yes\" or \"no\" must be assigned to the variable RememberMe!");
		if (RememberMe == "no") {
			Mozila.findElement(By.xpath("//*[@id=\"rememberMe\"]")).click();
			log.info("RememberMe check box is unchecked");
		}
		else if (RememberMe == "yes"){
			log.info("RememberMe check box is checked");
		}
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/button")).click();
		Thread.sleep(2000);
		log.info("Click action performed on Authentificate button");
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/div[1]")).isDisplayed(), "The entered credentials are not correct!");
		
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
	
	@Test (dependsOnMethods = {"Login"})
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
			else if (i == NrOfResults){
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

	@Test (dependsOnMethods = {"NavigateToBranch"})
	@Parameters ({"BranchNameView" , "BranchCodeView"})
	public void ViewBranch(String BranchName, String BranchCode) throws InterruptedException, IOException {
		Matched = 0;
		
		Mozila.findElement(By.xpath("//*[@id=\"searchQuery\"]")).clear();
		log.info("Cleared the branch search text box");
		
		Mozila.findElement(By.xpath("//*[@id=\"searchQuery\"]")).sendKeys(BranchName);
		log.info("Entered the branch name in to the search text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[2]/form/button")).click();
		Thread.sleep(1000);
		log.info("Click action performed on the Search button");
		
		//check if a branch with the given name exists
		Results = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody")).getAttribute("childElementCount");
		NrOfResults = Integer.parseInt(Results);
		Assert.assertFalse(NrOfResults == 0, "You tried to view a non existing branch!");

		for (int i = 1; i <= NrOfResults; i++) {
			//search for the branch with the desired name and code
			BranchCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[2]")).getAttribute("innerText");
			CodeCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[3]")).getAttribute("innerText");
			if (BranchCheck.equalsIgnoreCase(BranchName) && CodeCheck.equalsIgnoreCase(BranchCode)) {
				Matched ++;
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[4]/button[1]")).click();
				Thread.sleep(1000);
				log.info("Click action performed on the View button");
				
				Status = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/h2/span")).getAttribute("innerText");
				ViewedBranch = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/table/tbody/tr[1]/td[2]/input")).getAttribute("defaultValue");
				ViewedCode = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/table/tbody/tr[2]/td[2]/input")).getAttribute("defaultValue");
				Assert.assertTrue(Status.equalsIgnoreCase("Branch"), "You are not on the branch view page");
				Assert.assertTrue(ViewedBranch.equalsIgnoreCase(BranchName), "The branch with the name "+ BranchName +" and code "+ BranchCode +" hasn't been viewed!");
				Assert.assertTrue(ViewedCode.equalsIgnoreCase(BranchCode), "The branch with the name "+ BranchName +" and code "+ BranchCode +" hasn't been viewed!");
				Reporter.log("Branch "+ BranchName +" with code "+ BranchCode +" has been viewed");
				
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/button")).click();
				log.info("Click action performed on the Return button");
			}
		}
		Assert.assertFalse(Matched == 0, "The branch with the name "+ BranchName +" and code "+ BranchCode +" doesn't exist and can't be viewed!");
	}
	
	@Test (dependsOnMethods = {"NavigateToStaff"})
	@Parameters ({"StaffNameView" , "StaffBranchView"})
	public void ViewStaff(String StaffName, String StaffBranch) throws InterruptedException, IOException {
		Matched = 0;
		
		Results = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody")).getAttribute("childElementCount");
		NrOfResults = Integer.parseInt(Results);
		Assert.assertFalse(NrOfResults == 0, "You tried to view a non existing staff");

		for (int i = 1; i <= NrOfResults; i++) {
			//search for the staff with the desired name and branch
			StaffCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[2]")).getAttribute("innerText");
			BranchCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[3]")).getAttribute("innerText");
			if (StaffCheck.equalsIgnoreCase(StaffName) && BranchCheck.equalsIgnoreCase(StaffBranch)) {
				Matched ++;
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[4]/button[1]")).click();
				Thread.sleep(1000);
				log.info("Click action performed on the View button");
				
				Status = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/h2/span")).getAttribute("innerText");
				String StaffView = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/table/tbody/tr[1]/td[2]/input")).getAttribute("defaultValue");
				String BranchView = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/table/tbody/tr[2]/td[2]/input")).getAttribute("defaultValue");
				Assert.assertTrue(Status.equalsIgnoreCase("Staff"), "You are not on the staff view page");
				Assert.assertTrue(StaffView.equalsIgnoreCase(StaffName), "The staff with the name "+ StaffName +" and assigned branch "+ StaffBranch +" hasn't been viewed!");
				Assert.assertTrue(BranchView.equalsIgnoreCase(StaffBranch), "The staff with the name "+ StaffName +" and assigned branch "+ StaffBranch +" hasn't been viewed!");
				Reporter.log("Staff "+ StaffName +" with assigned branch "+ StaffBranch +" has been viewed");
				
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/button")).click();
				log.info("Click action performed on the Return button");
			}			
		}
		Assert.assertFalse(Matched == 0, "The staff with the name "+ StaffName +" and assigned branch "+ StaffBranch +" doesn't exist and can't be viewed!");
	}
	
	@Test (dependsOnMethods = {"NavigateToBranch"})
	@Parameters ({"BranchToEdit" , "NewBranchName" , "CodeToEdit" , "NewBranchCode"})
	public void EditBranch(String Name, String NewName, String Code, String NewCode) throws InterruptedException, IOException {				
		Matched = 0;
		
		//search for the branch you want to edit
		Mozila.findElement(By.xpath("//*[@id=\"searchQuery\"]")).clear();
		log.info("Cleared the branch search text box");
		
		Mozila.findElement(By.xpath("//*[@id=\"searchQuery\"]")).sendKeys(Name);
		log.info("Entered the branch name in to the search text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[2]/form/button")).click();
		Thread.sleep(1500);
		log.info("Click action performed on the Search button");
		
		Results = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody")).getAttribute("childElementCount");
		NrOfResults = Integer.parseInt(Results);
		Assert.assertFalse(NrOfResults == 0, "You tried to edit a non existing branch!");
		
		for (int i = 1; i <= NrOfResults; i++) {
			BranchCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[2]")).getAttribute("innerText");
			CodeCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[3]")).getAttribute("innerText");
			if (BranchCheck.equalsIgnoreCase(Name) && CodeCheck.equalsIgnoreCase(Code)) {
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[4]/button[2]")).click();
				Thread.sleep(1000);
				log.info("Click action performed on the Edit button");
				
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/input")).clear();
				log.info("Cleared the branch name text box");
				
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[3]/input")).clear();
				log.info("Cleared the branch code text box");
				
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/input")).sendKeys(NewName);
				log.info("Entered the branch new name in to the name text box");
				
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[3]/input")).sendKeys(NewCode);
				log.info("Entered the branch new code in to the code text box");
				
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[3]/button[2]")).click();
				Thread.sleep(1500);
				log.info("Click action performed on the Save button");
				
				Mozila.findElement(By.xpath("//*[@id=\"searchQuery\"]")).clear();
				log.info("Cleared the branch search text box");
				
				Mozila.findElement(By.xpath("//*[@id=\"searchQuery\"]")).sendKeys(NewName);
				log.info("Entered the edited branch name in to the search text box");
				
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[2]/form/button")).click();
				Thread.sleep(1000);
				log.info("Click action performed on the Search button");
					
				SecondResults = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody")).getAttribute("childElementCount");
				SecondNrOfResults = Integer.parseInt(SecondResults);
				Assert.assertFalse(SecondNrOfResults == 0, "There is no branch with the edited name! The edit function didn't work!");
				
				for (int j = 1; j <= SecondNrOfResults; j++) {
					BranchSecondCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ j +"]/td[2]")).getAttribute("innerText");
					CodeSecondCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ j +"]/td[3]")).getAttribute("innerText");
					Assert.assertTrue(BranchSecondCheck.equalsIgnoreCase(NewName), "The branch with the name "+ Name +" and code "+ Code +" hasn't been edited!");
					Assert.assertTrue(CodeSecondCheck.equalsIgnoreCase(NewCode), "The branch with the name "+ Name +" and code "+ Code +" hasn't been edited!");
					Matched ++;
					Reporter.log("Branch "+ Name +" with code "+ Code +" has been edited to branch with name "+ NewName +" and code "+ NewCode);
				}
			}
		}
		Assert.assertFalse(Matched == 0, "The branch with the name "+ Name +" and code "+ Code +" hasn't been edited!");
	}
	
	@Test (dependsOnMethods = {"NavigateToStaff"})
	@Parameters ({"StaffToEdit" , "NewStaffName" , "ActualStaffBranch" , "NewStaffBranch"})
	public void EditStaff(String Name, String NewName, String Branch, String NewBranch) throws InterruptedException, IOException {			
		Matched = 0;
		
		Results = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody")).getAttribute("childElementCount");
		NrOfResults = Integer.parseInt(Results);
		Assert.assertFalse(NrOfResults == 0, "You tried to edit a non existing staff!");

		for (int i = 1; i <= NrOfResults; i++) {
			StaffCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[2]")).getAttribute("innerText");
			BranchCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[3]")).getAttribute("innerText");
			if (StaffCheck.equalsIgnoreCase(Name) && BranchCheck.equalsIgnoreCase(Branch)) {
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[4]/button[2]")).click();
				Thread.sleep(1000);
				log.info("Click action performed on the Edit button");
				
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/input")).clear();
				log.info("Cleared the staff name text box");
				
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[2]/input")).sendKeys(NewName);
				log.info("Entered the staff new name in to the name text box");
				
				Select SelectBranch = new Select(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[3]/select")));		
				//open branch list
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[2]/div[3]/select")).click();
				Thread.sleep(500);
				log.info("Opened the branch list");
				
				SelectBranch.selectByVisibleText(NewBranch);
				log.info("Selected the branch new branch");
				
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[2]/div/div/form/div[3]/button[2]")).click();
				Thread.sleep(1500);
				log.info("Click action performed on the Save button");
				
				SecondResults = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody")).getAttribute("childElementCount");
				SecondNrOfResults = Integer.parseInt(Results);
				Assert.assertFalse(SecondNrOfResults == 0, "There is no staff with the edited name! The edit function didn't work!");
				
				for (int j = 1; j <= SecondNrOfResults; j++) {
					StaffSecondCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ j +"]/td[2]")).getAttribute("innerText");
					BranchSecondCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ j +"]/td[3]")).getAttribute("innerText");
					Assert.assertTrue(StaffSecondCheck.equalsIgnoreCase(NewName), "The staff with the name "+ Name +" and assigned branch "+ Branch +" hasn't been edited!");
					Assert.assertTrue(BranchSecondCheck.equalsIgnoreCase(NewBranch), "The staff with the name "+ Name +" and assigend branch "+ Branch +" hasn't been edited!");
					Matched ++;
					Reporter.log("Staff "+ Name +" with assigned branch "+ Branch +" has been edited to staff with name "+ NewName +" and assigned branch "+ NewBranch);
				}
			}
		}
		Assert.assertFalse(Matched == 0, "The staff with the name "+ Name +" and assigned branch "+ Branch +" hasn't been edited!");
	}
	
	@Test (dependsOnMethods = {"NavigateToBranch"})
	@Parameters ({"BranchToDelete" , "CodeToDelete"})
	public void DeteleBranch(String Name, String Code) throws InterruptedException, IOException {
		if (Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[3]/div/div/form/div[3]/button[1]")).isDisplayed()) {
			Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[3]/div/div/form/div[3]/button[1]")).click();
			Thread.sleep(1000);
			log.info("Click action performed on the Cancel button");
		}
		
		Mozila.findElement(By.xpath("//*[@id=\"searchQuery\"]")).clear();
		log.info("Cleared the branch search text box");
		
		Mozila.findElement(By.xpath("//*[@id=\"searchQuery\"]")).sendKeys(Name);
		log.info("Entered the branch name in to the search text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[2]/form/button")).click();
		Thread.sleep(1000);
		log.info("Click action performed on the Search button");
		
		Results = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody")).getAttribute("childElementCount");
		NrOfResults = Integer.parseInt(Results);
		Decrement = NrOfResults;
		Assert.assertFalse(NrOfResults == 0, "You tried to delete a non existing branch!");
		
		for (int i = 1; i <= NrOfResults; i++) {				
			BranchCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ Decrement +"]/td[2]")).getAttribute("innerText");
			CodeCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ Decrement +"]/td[3]")).getAttribute("innerText");
			if (BranchCheck.equalsIgnoreCase(Name) && CodeCheck.equalsIgnoreCase(Code)) {
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ Decrement +"]/td[4]/button[3]")).click();
				Thread.sleep(500);
				log.info("Click action performed on the Delete button");
				
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[3]/div/div/form/div[3]/button[2]")).click();
				Thread.sleep(1000);
				log.info("Click action performed on the Confirmation delete button");
				
				Status = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[3]/div/div/form/div[3]/button[2]")).getAttribute("disabled");
				Assert.assertTrue(Status == null, "Can't delete branch with staff assigned!");
				Reporter.log("Succesfully deleted branch "+ Name +" with code "+ Code);
			}
			Decrement --;
		}
	}	
	
	@Test (dependsOnMethods = {"NavigateToStaff"})
	@Parameters ({"StaffToDelete" , "StaffBranchToDelete"})
	public void DeleteStaff(String Name, String Branch) throws InterruptedException, IOException {		
		Results = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody")).getAttribute("childElementCount");
		NrOfResults = Integer.parseInt(Results);
		Decrement = NrOfResults;
		Assert.assertFalse(NrOfResults == 0, "There are no staff created, so there is nothing to delete!");

		for (int i = 1; i <= NrOfResults; i++) {
			StaffCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[2]")).getAttribute("innerText");
			BranchCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[3]")).getAttribute("innerText");
			if (StaffCheck.equalsIgnoreCase(Name) && BranchCheck.equalsIgnoreCase(Branch)) {
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[4]/button[3]")).click();
				Thread.sleep(500);
				log.info("Click action performed on the Delete button");
				
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[3]/div/div/form/div[3]/button[2]")).click();
				Thread.sleep(500);
				log.info("Click action performed on the Confirmation delete button");
				
				DeletedStaffs ++;
				NrOfResults --;
				i = 1;
			}
		}
		
		Results = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody")).getAttribute("childElementCount");
		NrOfResults = Integer.parseInt(Results);
		if (NrOfResults == 0) {
			Reporter.log("Deleted a total number of "+ DeletedStaffs +" staffs with the name "+ Name +" and assigned branch "+ Branch);
		}
		for (int i = 1; i <= NrOfResults; i++) {
			StaffCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[2]")).getAttribute("innerText");
			BranchCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[3]")).getAttribute("innerText");
			Assert.assertTrue(StaffCheck.equalsIgnoreCase(Name) && BranchCheck.equalsIgnoreCase(Branch), "Staff has not been deleted");
			Assert.assertTrue(DeletedStaffs == 0, "There is no staff to delete with name "+ Name +" and branch "+ Branch);
			Reporter.log("Staff with name "+ Name +" and assigned branch "+ Branch +" has been successfully deleted!");
		}
	}
	
	@Test (dependsOnMethods = {"NavigateToBranch"})
	@Parameters ({"BranchSearchCriteria"})
	public void QueryBranch(String SearchCriteria) throws InterruptedException, IOException {
		Mozila.findElement(By.xpath("//*[@id=\"searchQuery\"]")).clear();
		log.info("Cleared the branch search text box");
		
		Mozila.findElement(By.xpath("//*[@id=\"searchQuery\"]")).sendKeys(SearchCriteria);
		log.info("Search information entered in the Branch Name text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[2]/form/button")).click();
		Thread.sleep(1000);
		log.info("Click action performed on the Search button");
		
		Results = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody")).getAttribute("childElementCount");
		NrOfResults = Integer.parseInt(Results);
		Assert.assertFalse(NrOfResults == 0, "There is no branch that contains in it the search infomation!");
		
		for (int i = 1; i <= NrOfResults; i++) {
			IDCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[1]/a")).getAttribute("innerText");
			BranchCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[2]")).getAttribute("innerText");
			CodeCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr["+ i +"]/td[3]")).getAttribute("innerText");
			Assert.assertTrue(IDCheck.equalsIgnoreCase(SearchCriteria) || BranchCheck.equalsIgnoreCase(SearchCriteria) || CodeCheck.equalsIgnoreCase(SearchCriteria), "There is no branch that contains in it the search infomation!");
			
			Reporter.log("Found branch with the folowing information: ID "+ IDCheck +" Name "+ BranchCheck +" Code "+ CodeCheck);								
		}
	}
	
	@Test (dependsOnMethods = {"NavigateToStaff"})
	@Parameters ({"StaffSearchCriteria"})
	public void QueryStaff(String SearchCriteria, String Test) throws InterruptedException, IOException {
		Mozila.findElement(By.xpath("//*[@id=\"searchQuery\"]")).clear();
		log.info("Cleared the staff search text box");
		
		Mozila.findElement(By.xpath("//*[@id=\"searchQuery\"]")).sendKeys(SearchCriteria);
		log.info("Search information entered in the Staff Name text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[1]/div/div[2]/form/button")).click();
		Thread.sleep(1000);
		log.info("Click action performed on the Search button");
		
		Results = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody")).getAttribute("childElementCount");
		NrOfResults = Integer.parseInt(Results);
		Assert.assertFalse(NrOfResults == 0, "There is no staff that contains in it the search infomation!");

		for (int i = 1; i <= NrOfResults; i++) {
			IDCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr[" + i + "]/td[1]/a")).getAttribute("innerText");
			StaffCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div[4]/table/tbody/tr[" + i + "]/td[2]")).getAttribute("innerText");
			Assert.assertTrue(IDCheck.equalsIgnoreCase(SearchCriteria) || StaffCheck.equalsIgnoreCase(SearchCriteria), "There is no staff that contains in it the search infomation!");
			Reporter.log("Found staff with the folowing information: ID "+ IDCheck +" Name "+ BranchCheck);								
		}
	}
		
	@Test (dependsOnMethods = {"NavigateToAccountInformation"})
	@Parameters ({"FirstNameCheck" , "LastNameCheck" , "EmailCheck" , "LanguageCheck"})
	public void CheckAccountInformation(String FirstName, String LastName, String Email, String Language) throws IOException {
		Status = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/h2")).getAttribute("innerText");
		FirstNameCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/input")).getAttribute("value");
		LastNameCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[2]/input")).getAttribute("value");
		EmailCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[3]/input")).getAttribute("value");
		LanguageCheck = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[4]/select")).getAttribute("innerText");
		Assert.assertTrue(Status.equalsIgnoreCase("User settings for [admin]") && 
				FirstNameCheck.equalsIgnoreCase(FirstName) &&	
				LastNameCheck.equalsIgnoreCase(LastName) &&	
				EmailCheck.equalsIgnoreCase(Email) && 
				LanguageCheck.equalsIgnoreCase("English"), 
				"The account information is incorrect!");
		log.info("Account information has been checked and the information was correct");
		Reporter.log("Account information is correct!");
	}
	
	@Test (dependsOnMethods = {"NavigateToAccountInformation"})
	@Parameters ({"FirstName" , "LastName" , "Email"})
	public void ChangeAllAccountInformation(String FirstName, String LastName, String Email) throws InterruptedException, IOException {
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/input")).clear();
		log.info("Cleared the first name text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[2]/input")).clear();
		log.info("Cleared the last name text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[3]/input")).clear();
		log.info("Cleared the email text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/input")).sendKeys(FirstName);
		log.info("Desired first name has been entered in the First Name text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[2]/input")).sendKeys(LastName);
		log.info("Desired last name has been entered in the Last Name text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[3]/input")).sendKeys(Email);
		log.info("Desired email address has been entered in the Email text box");
		
		Assert.assertTrue(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/div/p[1]")).isDisplayed(), "The chosen first name can't be set as a first name!");
		Assert.assertTrue(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[2]/div/p[1]")).isDisplayed(), "The chosen last name can't be set as a last name!");
		Assert.assertTrue(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[3]/div/p[1]")).isDisplayed() ||
			Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[3]/div/p[2]")).isDisplayed() ||
			Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[3]/div/p[3]")).isDisplayed() , 
			"The chosen email is not a correct email address!");
		Reporter.log("First name, last name and email have the correct format");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/button")).click();		
		Thread.sleep(1000);
		log.info("Click action performed on the save button");
		
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/div[3]/strong")).isDisplayed(), "Account information hasn't been changed!");
		Assert.assertTrue(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/div[1]/strong")).isDisplayed(), "Account information hasn't been changed!");
		Reporter.log("Account information has been changed!");
	}

	@Test (dependsOnMethods = {"NavigateToPasswordChange"})
	@Parameters ({"Password" , "ConfirmationPassword"})
	public void ChangePassword(String Password, String ConfirmationPassword) throws InterruptedException, IOException {
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/input")).clear();
		log.info("Cleared the password text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[2]/input")).clear();
		log.info("Cleared the confirmation password text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/input")).sendKeys(Password);
		log.info("New password has been entered in the Password text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[2]/input")).sendKeys(ConfirmationPassword);
		log.info("New confirmation password has been entered in the Confirmation Password text box");
		
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/div[1]/p[1]")).isDisplayed() ||
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/div[1]/p[2]")).isDisplayed() ||
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/div[1]/p[3]")).isDisplayed(),
				"The chosen password doesn't meet the standard format!");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/button")).click();
		Thread.sleep(1500);
		log.info("Click action performed on Save button");
		
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/div[2]")).isDisplayed() &&
			!Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/div[3]")).isDisplayed(), 
			"An error has occurred! The password could not be changed.");
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/div[3]")).isDisplayed(), "An error has occurred! Password and confirmation password need to be the same");
		Reporter.log("Password has benn changed");
	}

	@Test (dependsOnMethods = {"OpenBrowser"})
	@Parameters ({"UserID" , "UserEmail" , "UserPassword" , "UserConfirmationPassword"})
	public void RegisterUser(String User, String Email, String Password, String ConfirmationPassword) throws InterruptedException, IOException {
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div[2]/div/div[2]/a")).click();
		Thread.sleep(1500);
		log.info("Click action performed on Register a new user button");
			
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/input")).clear();
		log.info("Cleared the user name text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[2]/input")).clear();
		log.info("Cleared the email text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[3]/input")).clear();
		log.info("Cleared the password text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[4]/input")).clear();
		log.info("Cleared the confirmation password text box");
			
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/input")).sendKeys(User);
		log.info("User name has been entered in the Login text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[2]/input")).sendKeys(Email);
		log.info("Email address has been entered in the EMail text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[3]/input")).sendKeys(Password);
		log.info("Password has been entered in the Password text box");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[4]/input")).sendKeys(ConfirmationPassword);
		log.info("Confirmation password has been entered in the Confirmation Password text box");
		
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/div/p[1]")).isDisplayed() ||
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/div/p[2]")).isDisplayed() ||
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/div/p[3]")).isDisplayed() ||
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[1]/div/p[4]")).isDisplayed(), 
				"User "+ User +" doesn't respect the standard format!");
		
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[2]/div/p[1]")).isDisplayed() ||
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[2]/div/p[2]")).isDisplayed() ||
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[2]/div/p[3]")).isDisplayed(), 
				"Email "+ Email +" doesn't respect the standard format!");
		
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[3]/div[1]/p[1]")).isDisplayed() ||
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[3]/div[1]/p[2]")).isDisplayed() ||
				Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/div[3]/div[1]/p[3]")).isDisplayed(), 
				"Password "+ Password +" doesn't respect the standard format!");
		
		Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/form/button")).click();
		Thread.sleep(1500);
		log.info("Click action performed on Save button");
		
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/div[5]")).isDisplayed(), "Password "+ Password +" has to be the same as confirmation password "+ ConfirmationPassword);
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/div[2]")).isDisplayed(), "An error has appeared and the user hasn't been registered!");
		Assert.assertFalse(Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div/div[3]")).isDisplayed(), "A user with this name "+ User +" already exists!");
		Reporter.log("User has been successfully registered");	
	}
	
	@Test (dependsOnMethods = {"Login"})
	public void Logout() throws InterruptedException, IOException {
		Mozila.findElement(By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[3]/a/span/span[2]")).click();
		log.info("Click action performed on Account drop down menu");
		
		Mozila.findElement(By.xpath("/html/body/div[2]/nav/div/div[2]/ul/li[3]/ul/li[4]/a/span[2]")).click();
		Thread.sleep(1500);
		log.info("Click action performed on Log out");
		
		Status = Mozila.findElement(By.xpath("/html/body/div[3]/div[1]/div/div/div[2]/h1")).getAttribute("innerText");												
		Assert.assertTrue(Status.equalsIgnoreCase("Welcome to Gurukula!"), "The log out action was not performed!");
		Reporter.log("Logout succcessfully");
	}
	
	
	@Test (dependsOnMethods = {"OpenBrowser"})
	public void Close() throws InterruptedException {
		Mozila.close();
		Thread.sleep(2000);
		log.info("Browser was been closed");
		Reporter.log("Browser was been closed");
	}	
}