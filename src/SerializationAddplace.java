import io.restassured.RestAssured;
import pojo.Addplace;
import pojo.Location;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializationAddplace {

	public static void main(String[] args) {
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
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
		
		given().queryParam("key","qaclick123").log().all().
		body(a)
		.when().post("/maps/api/place/add/json")
		.then().log().all()
		.statusCode(200).extract().response();

	}

}
