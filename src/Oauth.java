
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.Getcources;
import pojo.WebAutomation;

public class Oauth {

	public static void main(String[] args) {
		
		String[]cources= {"Selenium Webdriver Java","Cypress","Protractor"};
		String code="https://rahulshettyacademy.com/getCourse.php?code=4%2F0AVHEtk451KnboBTzJNMvD3V5iwOMnIBaOtHyFfHvfmnDBhuh4-wHfUKRqKUXbOob33bkbQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=2&prompt=none";
		
		String value=code.split("code=")[1];
		String authcode=value.split("&scope")[0];
		System.out.println(authcode);
		String accesstoken_response=given().urlEncodingEnabled(false)
		.queryParams("code",authcode)
		.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code")
		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		System.out.println(accesstoken_response);
		
		JsonPath js=new JsonPath(accesstoken_response);
		String accesstoken=js.get("access_token");
		System.out.println("accesstoken "+accesstoken);
		Getcources gc=given().queryParam("access_token", accesstoken).expect().defaultParser(Parser.JSON)
		.when().get("https://rahulshettyacademy.com/getCourse.php").as(Getcources.class);
		
		System.out.println(gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getPrice());
		
		List<Api> no=gc.getCourses().getApi();
		
		for(int i=0;i<no.size();i++) {
			if((gc.getCourses().getApi().get(i).getCourseTitle()).equalsIgnoreCase("SoapUI Webservices testing")){
				System.out.println(gc.getCourses().getApi().get(i).getPrice());
			}
		}
		
		//verifying the web automation cources title
		
		ArrayList<String>actualcources=new ArrayList<String>();
		
		List<WebAutomation> nom=gc.getCourses().getWebAutomation();
		for(int i=0;i<nom.size();i++) {
			actualcources.add(gc.getCourses().getWebAutomation().get(i).getCourseTitle());
			
			}
		Assert.assertTrue(actualcources.equals(Arrays.asList(cources)))  ;
		
		}

	

}
