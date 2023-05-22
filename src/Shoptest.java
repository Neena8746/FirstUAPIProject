import static io.restassured.RestAssured.*;

import java.io.File;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.RequestLogin;
import pojo.ResponseLogin;
public class Shoptest {

	public static void main(String[] args) {
		//login
		
		RequestLogin rl=new RequestLogin();
		rl.setUserEmail("nobete4310@syinxun.com");
		rl.setUserPassword("Tester@1234");
		RequestSpecification rs=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		ResponseLogin rp= given().spec(rs).body(rl)
				.when().post("/api/ecom/auth/login")
		.then().extract().response().as(ResponseLogin.class);
		String token=rp.getToken();
		String userid=rp.getUserId();
		System.out.println(rp.getMessage());

		//create product
		
		RequestSpecification cp= new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();
		
		RequestSpecification ip=given().spec(cp).log().all()
		.param("productName", "chappal").param("productAddedBy",userid)
		.param("productCategory", "fashion").param("productSubCategory", "shirts")
		.param("productPrice", "11500").param("productDescription", "Addias Originals")
		.param("productFor", "women").multiPart("productImage",new File("C://Users//neaugust//Downloads//pngwing.com.png"));
		
		String resp=ip.when().post("/api/ecom/product/add-product")
		.then().log().all().
		extract().response().asString();
		
		JsonPath js=new JsonPath(resp);
		String productId=js.get("productId");
		
	}

}
