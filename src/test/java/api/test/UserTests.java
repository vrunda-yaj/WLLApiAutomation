package api.test;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.UserPojo;
import io.restassured.response.Response;

public class UserTests {
	Faker faker;
	UserPojo userPayLoad;
	 HashMap<String, String> requestBody = new HashMap<>();
	 String auth_token;
	 ExtentReports extent;
	    ExtentTest test;
	    ExtentSparkReporter sparkReporter;
	    
	    @BeforeTest
	    public void setUpReport() {
	        // Specify the report location
	        sparkReporter = new ExtentSparkReporter("C:/Users/VrundaYajurvedi/eclipse-workspace/WLLFramework/Reports/ExtentReport.html");
	        sparkReporter.config().setDocumentTitle("API Test Report");
	        sparkReporter.config().setReportName("User API Test Report");
	        sparkReporter.config().setTheme(Theme.DARK);

	        extent = new ExtentReports();
	        extent.attachReporter(sparkReporter);
	        extent.setSystemInfo("Host Name", "Dev");
	        extent.setSystemInfo("Environment", "QA");
	        extent.setSystemInfo("User Name", "Vrunda Yajurvedi");
	    }
	    
	 
	@BeforeClass
	public void setupData()
	{
		/* faker=new Faker();
		 userPayLoad=new UserPojo();
		 
		 userPayLoad.setId(faker.idNumber().hashCode());
		 userPayLoad.setUsername(faker.name().username());
		 userPayLoad.setFirstName(faker.name().firstName());
		 userPayLoad.setLastName(faker.name().lastName());
		 userPayLoad.setEmail(faker.internet().safeEmailAddress());
		 userPayLoad.setPassword(faker.internet().password());
		 userPayLoad.setPhone(faker.phoneNumber().cellPhone());
		
		*/
	   // for WLL 
		 requestBody.put("email", "bane@paktolus.com");  // Replace with the email to test	
	        requestBody.put("password", "Wlldev@2024");    // Replace with the password to test 
	       
	
	}
	
	@BeforeMethod
    public void beforeMethod() {
        // Initialize ExtentTest before each test case
        test = extent.createTest("Starting Test: " + this.getClass().getSimpleName());
    }
	
	// for WLL 
	 @Test(priority=1)
	public void TestWLLPostUser()
	{
		 try {
	            test = extent.createTest("TestWLLPostUser");
		 // System.out.println("Response Body: " + requestBody.toString());
		Response response=UserEndPoints.WLLLoginUser(requestBody);
		response.then()
		.statusCode(200);
		auth_token = response.jsonPath().getString("token");
		//System.out.println("auth token"+auth_token);
		test.pass("User successfully logged in, token generated: " + auth_token);
	        } catch (Exception e) {
	            test.fail("TestWLLPostUser failed: " + e.getMessage());
	            throw e;
	        }
	}
	 @Test(priority=2)
	 public void TestWLLGetUser()
		{
		 try {
	            test = extent.createTest("TestWLLGetUser");
			 // System.out.println("Response Body: " + requestBody.toString());
		//System.out.println("auth tokenGet"+auth_token);
			Response response=UserEndPoints.WLLGetUser(auth_token);
			response.then()
			.statusCode(200);
		
			//context.setAttribute("au_token", auth_token);
			 test.pass("User details fetched successfully.");
	        } catch (Exception e) {
	            test.fail("TestWLLGetUser failed: " + e.getMessage());
	            throw e;
	        }
			
		}
	 @Test(priority=3)
	 public void TestWLLUpdateUser()
		{
		 try {
	            test = extent.createTest("TestWLLUpdateUser");
		 	JSONObject updaterequestBody = new JSONObject();
	        updaterequestBody.put("firstName", "Bane"); // Replace with the email to test
	        updaterequestBody.put("lastName", "paktolus");
	        updaterequestBody.put("countryCode", "+1");
	        updaterequestBody.put("phoneNo", "5644333344");
			//System.out.println("request Body: " + updaterequestBody.toString());
		
			Response response=UserEndPoints.WLLUpdateUser(updaterequestBody.toString(),auth_token);
			response.then()
			.statusCode(200);
			
			String updatemsg=response.jsonPath().getString("message");
			String Expectedupdatemsg="Profile updated successfully.";
			Assert.assertEquals(updatemsg,Expectedupdatemsg);
		
			//validate user data after update
			response=UserEndPoints.WLLGetUser(auth_token);
			
			//context.setAttribute("au_token", auth_token);
			test.pass("Profile updated successfully.");

	        } catch (Exception e) {
	            test.fail("TestWLLUpdateUser failed: " + e.getMessage());
	            throw e;
	        }
			
		}
	 @Test(priority=4)
	 public void TestWllLogout()
	 {
		 try {
	            test = extent.createTest("TestWllLogout");
		 Response response=UserEndPoints.WLLogoutUser(auth_token);
			response.then()
			.statusCode(200);
			String logoutmsg=response.jsonPath().getString("message");
			String Expectedlogoutmsg="You have been successfully logged out.";
			Assert.assertEquals(logoutmsg,Expectedlogoutmsg);
			test.pass("User logged out successfully.");

	        } catch (Exception e) {
	            test.fail("TestWllLogout failed: " + e.getMessage());
	            throw e;
	        }
		
			
	 }
	 
	 @AfterMethod
	    public void afterMethod() {
	        // End test after each test case
	        extent.flush();
	    }

	    @AfterClass
	    public void tearDownReport() {
	        extent.flush();
	    }

}
