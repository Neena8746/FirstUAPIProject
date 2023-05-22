import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		
		JsonPath js=new JsonPath(Payload.cources());

		//1. Print No of courses returned by API
		
		int count=js.get("courses.size()");
		System.out.println(count);
		
		//2.Print Purchase Amount
		int purchaseAmount=js.get("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);
		
		//3. Print Title of the first course
		System.out.println(js.get("courses[0].title").toString());
		
		//4. Print All course titles and their respective Prices
		
		for(int i=0;i<count;i++) {
			System.out.println(js.get("courses["+i+"].title").toString());
			System.out.println(js.get("courses["+i+"].price").toString());
		}
		
		//5. Print no of copies sold by RPA Course
		
		for(int i=0;i<count;i++) {
			String title=js.get("courses["+i+"].title");
			if(title.equalsIgnoreCase("RPA")) {
				System.out.println(js.get("courses["+i+"].copies").toString());
				break;
			}
			
		}
		
		//6. Verify if Sum of all Course prices matches with Purchase Amount
		int courceprice=0;
		
		for(int i=0;i<count;i++) {
			int price=js.getInt("courses["+i+"].price");
			int copies=js.getInt("courses["+i+"].copies");
			courceprice=courceprice+price*copies;
		}
		System.out.println(courceprice);
		Assert.assertEquals(courceprice, purchaseAmount);
		
	}

}
