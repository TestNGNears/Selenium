package testScripts;

import java.io.File;
import java.io.IOException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import pageObjects.OrangeHRMCreateUser;
import pageObjects.OrangeHRMEditUser;
import pageObjects.OrangeHRMLogin;
import utilities.Base;
import utilities.CustomListeners;


@Listeners(CustomListeners.class)
public class EditUser extends Base {
	OrangeHRMLogin login;
	OrangeHRMCreateUser cuser;
	OrangeHRMEditUser euser;
	Base b ;
	
	public String screenshotFilePath="" ;
	public String reportspath="" ;
	public String URL="";
	public String user_url="";
	public String intialReport = "";
	public String TestDataSheet = "";
	
	
	
	
	
	@BeforeMethod
	public void load(ITestResult result) throws IOException {
		b = new Base();
		screenshotFilePath = (System.getProperty("user.dir")+File.separator+"src"+File.separator+"libraries"+File.separator+"screenshots"+File.separator).replace("\\", "/") ;
		reportspath=(System.getProperty("user.dir")+File.separator+"src"+File.separator+"libraries"+File.separator+"reports"+File.separator).replace("\\", "/") ;
		URL = b.getMapData("URL",0);
		user_url=b.getMapData("user_url",1);
		intialReport=(System.getProperty("user.dir")+File.separator+"test-output"+File.separator+"OrangeHRM-emailable-report-template.html").replace("\\", "/") ;
		TestDataSheet = (System.getProperty("user.dir")+File.separator+"src"+File.separator+"testData"+File.separator+"TestDataSheet.xlsx").replace("\\", "/") ;
		intialize("chrome");
		Reporter.setCurrentTestResult(result);
		Reporter.log("Step 1 : Browser Initialized------PASS");
		
		
		
	}
	
	@AfterMethod
	public void cleanup(ITestResult result) {
		driver.quit();
		Reporter.setCurrentTestResult(result);
		Reporter.log("<br>Browser Closed------PASS");

	}
	
	@Test(enabled=true)
	public void orageHRM_edit_user() {
		
		try {
				
		login = new OrangeHRMLogin(b.returnDriver());
		cuser = new OrangeHRMCreateUser();
		euser = new OrangeHRMEditUser();
		
		b.loadURL(URL);
		Reporter.log("<br>Step 2 : Url Loaded------PASS");
		
		login.login(b.getMapData("username", 1),b.getMapData("password", 1));
		Reporter.log("<br>Step 3 : Application logged in------PASS");
		

		
		b.click(euser.Admin);
		Reporter.log("<br>Step 4 : Admin button clicked------PASS");
		
		b.setText(cuser.search_username, b.getCellValueFromExcel(TestDataSheet, 2, 0));
		Reporter.log("<br>Step 5 : Username Entered in search Field ------PASS");
		
		b.click(cuser.search);
		b.javascriptElemClick(cuser.get_username1);
		Reporter.log("<br>Step 6 : Search Button clicked------PASS");
		
		b.click(cuser.save);
		String user_role_change =b.getMapData("user_role_edit", 1); 
		b.selectDropDown(cuser.User_Role, user_role_change);
		Reporter.log("<br>Step 7 : User Role Updated------PASS");
		
		String username_change = "";
		username_change = b.getaattribute(cuser.usernmae);
		username_change=username_change+"A";
		b.setText(cuser.usernmae, username_change);
		Reporter.log("<br>Step 8 : Username Updated------PASS");
		
		b.click(cuser.save);
		Reporter.log("<br>Step 9 : Save Button Clicked------PASS");
		
		b.isElementPresent(cuser.save);
		b.ElementWait(cuser.search_username);
		b.setText(cuser.search_username, username_change);
		Reporter.log("<br>Step 10 : Username entered in search Field------PASS");
		
		b.click(cuser.search);
		Reporter.log("<br>Step 11 : Search Button Clicked------PASS");
		
		String current_username = b.getText(cuser.get_username);
		String Current_role = b.getText(cuser.get_userrole);
		Assert.assertEquals(current_username, username_change);
		Assert.assertEquals(Current_role, user_role_change);
		b.screenShot(screenshotFilePath, this.getClass().getName());
		Reporter.log("<br>Step 12 : Validating the Username  and user role after Edit------PASS");
		
		b.writeToExcel(2, username_change, username_change);
		
		}catch (Exception e) {
			Reporter.log("<br>Test Step Failed------FAIL");
			Assert.fail(e.getMessage());
			
		}
		
	}
	
	
	
	

}
