import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;	
public class JiraTest {

	public static void main(String[] args) {
		
		RestAssured.baseURI="http://localhost:8080";
		SessionFilter sf=new SessionFilter();
		//login jira and create cookies
		given().log().all().header("Content-Type","application/json").body("{ \"username\": \"neena\", \"password\": \"August@1234\" }").filter(sf)
		.when().post("rest/auth/1/session")
		.then().log().all().assertThat().statusCode(200).body("session.name", equalTo("JSESSIONID"));
		
		
		//create issue -for that we need cookies-So we are using a new class called session
		
//		given().header("cookie","JSESSIONID=091F6B8EBD117766EF1A32C5C24F4F2D")
		
		
		//Add comment
		
		String addcommentResponse=given().log().all().pathParam("id", "10001").header("Content-Type","application/json").
		filter(sf).
		body("{\r\n"
				+ "    \"body\": \"Test comment is adding through Api\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").when().post("/rest/api/2/issue/{id}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js=new JsonPath(addcommentResponse);
		String commentid=js.getString("id");
		//Add attchment
		given().log().all().header("X-Atlassian-Token","no-check").header("Content-Type","multipart/form-data").pathParam("id", "10001").multiPart("file", new File("file.txt")).filter(sf)
		.when().post("/rest/api/2/issue/{id}/attachments")
		.then().assertThat().statusCode(200);
		
		String allDetails=given().filter(sf).pathParam("id", "10001").queryParam("fields", "comment").
				when().get("/rest/api/2/issue/{id}").then().log().all()
		.assertThat().statusCode(200).extract().response().asString();
		js=new JsonPath(allDetails);
		int count=js.getInt("fields.comment.comments.size()");
		
		for(int i=0;i<count;i++) {
			String actualid=js.get("fields.comment.comments["+i+"].id").toString();
			if(actualid.equalsIgnoreCase(commentid)) {
				System.out.println(js.get("fields.comment.comments["+i+"].body").toString());
				break;
			}
		}
		

	}

	
}
