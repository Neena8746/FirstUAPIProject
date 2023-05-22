import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.Addplace;
import pojo.Location;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializationAddplaceRequestSpec {

	public static void main(String[] args) {
		
//		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		Addplace a=new Addplace();
		a.setAccuracy(50);
		a.setAddress("29, side layout, cohen 09");
		a.setLanguage("French-IN");
		a.setName("Frontline house");
		a.setPhone_number("(+91) 983 893 3937");
		a.setWebsite("http://google.com");
		Location l=new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		a.setLocation(l);
		ArrayList<String>list=new ArrayList<String>();
		list.add("shoe park");
		list.add("shop");
		a.setTypes(list);
		
		RequestSpecification req=new RequestSpecBuilder().addQueryParam("key","qaclick123").setContentType(ContentType.JSON).setBaseUri("https://rahulshettyacademy.com").build();
		
		RequestSpecification res=given().spec(req).
		body(a);
		ResponseSpecification resp=new ResponseSpecBuilder().expectContentType(ContentType.JSON).expectStatusCode(200).build();
		res.when().post("/maps/api/place/add/json")
		.then().spec(resp).log().all()
		.extract().response();

	}

}
