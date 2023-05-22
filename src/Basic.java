import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.Payload;

public class Basic {

	public static void main(String[] args) throws IOException {
		//given -all input details
		//when  -HTTP request&resources
		//Then -validation
		
		//Store the place id from ADD call->Update the address based on the Stored placeid->Verify by seeing GET API response
		RestAssured.baseURI="http://rahulshettyacademy.com";
		
		//POST
//		String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
//		.body(Payload.addPlace()).when().post("maps/api/place/add/json")
//		.then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server","Apache/2.4.41 (Ubuntu)").extract().response().asString();
		
		//Static payload-give jsonfile path to the body
		String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\neaugust\\Downloads\\addPlace.json")))).when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server","Apache/2.4.41 (Ubuntu)").extract().response().asString();
				
		
		
		JsonPath js=new JsonPath(response);
		String placeid=js.getString("place_id");
		System.out.println(placeid);
		
		String address="70 winter walk, USA";
		
		//PUT
		given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json").body("{\r\n"
				+ "\"place_id\":\""+placeid+"\",\r\n"
				+ "\"address\":\""+address+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "").when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//GET
//		given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeid ).when().get("maps/api/place/get/json").then().log().all().assertThat()
//		.statusCode(200).body("address", equalTo("70 winter walk, USA"));
		
		response=given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeid ).when().get("maps/api/place/get/json").then().log().all().assertThat()
		.statusCode(200).extract().response().asString();
		
		JsonPath js1=new JsonPath(response);
		String actual=js1.getString("address");
		System.out.println(actual);
		
		Assert.assertEquals(actual, address);
		
		
		

	}

}
