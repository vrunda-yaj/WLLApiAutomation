package api.endpoints;

import io.restassured.response.Response;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import api.payload.UserPojo;
import static io.restassured.RestAssured.given;

import java.util.HashMap;

import org.testng.ITestContext;


//created for Create, Read, update and delete requests the user API
public class UserEndPoints {
	
	
	public static Response WLLLoginUser(HashMap<String, String> requestBody)
	{
		Response response = given()
				 .header("x-api-key", "4sbXBT2xSc6jc6Y70DVctQJct4dt1SI5MLj2ItSvf")
		            .contentType("application/json")
				.body(requestBody)
				
		.when()
		.post(Routes.wllpost_url);
		return response;
		
	}
	public static Response WLLGetUser(String authToken)
	{
		//context.getAttribute("au_token",au_token);
	//	System.out.println("auth tokenGetuser"+authToken);
		Response response = given()
				 .header("x-api-key", "4sbXBT2xSc6jc6Y70DVctQJct4dt1SI5MLj2ItSvf")
		            .contentType("application/json")
					.header("Authorization","Bearer "+authToken)			
		.when()
		.get(Routes.wllget_url);
		return response;
		
	}
	
	public static Response WLLUpdateUser(String requestBody,String authToken)
	{
		
		Response response = given()
				 .header("x-api-key", "4sbXBT2xSc6jc6Y70DVctQJct4dt1SI5MLj2ItSvf")
		            .contentType("application/json")
		            .header("Authorization","Bearer "+authToken)	
				.body(requestBody)
				
		.when()
		.put(Routes.wllupdate_url);
		return response;
		
	}
	
	public static Response WLLogoutUser(String authToken)
	{
		Response response = given()
				 .header("x-api-key", "4sbXBT2xSc6jc6Y70DVctQJct4dt1SI5MLj2ItSvf")
		            .contentType("application/json")
					.header("Authorization","Bearer "+authToken)			
		.when()
		.get(Routes.wlllogout_url);
		return response;
		
	}
	
	/*
	public static Response deleteUser(String userName,UserPojo payload)
	{
		Response response = given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.pathParam("username", userName)
				.body(payload)
				
				.when()
		.get(Routes.delete_url);
		return response;
		
	}
*/
}
