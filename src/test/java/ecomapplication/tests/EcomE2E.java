package ecomapplication.tests;

import ecomapplication.testComponents.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pageobjects.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class EcomE2E extends BaseTest {

    //Global variables and code
    String productName="ZARA COAT 3";

    @Test(dataProvider="getData",groups={"Purchase"})
    public void sumbitOrder(HashMap<String,String> input) throws IOException, InterruptedException {

        //Login to website
        ProductCatelogPage productCatelogPage=landingPage.loginApplication(input.get("email"), input.get("password"));

        //wait till the all products are loaded //Getting all the products and adding the desired product to cart //Wait till the loading and add to cart success msg
        List<WebElement> products=productCatelogPage.getProductList();
        productCatelogPage.addProductToCart(input.get("product"));

        //click on cart button
        CartPage cartPage=productCatelogPage.goToCart();

        //Verifying the added to cart product is present
        Boolean match=cartPage.verfiyProductDisplay(input.get("product"));
        Assert.assertTrue(match);

        //checkout button
        CheckOutPage checkOutPage =cartPage.goToCheckout();
        checkOutPage.selectCountry("India");

        //Place order button
        ConfirmationPage confirmationPage=checkOutPage.submitOrder();

        //success msg validation
        String successMessage=confirmationPage.getSuccessMessage();
        Assert.assertTrue(successMessage.equalsIgnoreCase("Thankyou for the order."));
    }

    @Test(dependsOnMethods={"sumbitOrder"})
    public void orderHistory()
    {
        //Login to website
        ProductCatelogPage productCatelogPage=landingPage.loginApplication("postman_api@gmail.com", "123456789@hH");
        OrdersPage ordersPage=productCatelogPage.goToOrders();
        Assert.assertTrue(ordersPage.verfiyOrderDisplay(productName));
    }

    @DataProvider
    public Object[][] getData() throws IOException {

        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//ecomaaplication//data//PurchaseOrder.json");
        return new Object[][] {{data.get(0)},{data.get(1)}};
        
        //return new Object[][]{{"postman_api@gmail.com", "123456789@hH", "ZARA COAT 3"}, {"postman_api@gmail.com", "123456789@hH", "ADIDAS ORIGINAL" }};

        /*HashMap<String,String> map = new HashMap<String,String>();
        map.put("email","postman_api@gmail.com");
        map.put("password","123456789@hH");
        map.put("product","ZARA COAT 3");

        HashMap<String,String> map1 = new HashMap<String,String>();
        map1.put("email","postman_api@gmail.com");
        map1.put("password","123456789@hH");
        map1.put("product","ADIDAS ORIGINAL");
        return new Object[][] {{map},{map1}};*/

    }
}
