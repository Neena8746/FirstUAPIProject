import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@Test(dataProvider="getData")
	public void addBooks(String isbn,String aisle,String dummy)
	{
		RestAssured.baseURI="http://216.10.245.166";
		given().log().all().body(Payload.getBooksJson(isbn,aisle))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200);
		
	}
	
	@DataProvider()
	public Object[][] getData() {
		return new Object[][] { {"dfs","6575","fgd"},{"hrhf","4757","fsgg"}};
	}
}
