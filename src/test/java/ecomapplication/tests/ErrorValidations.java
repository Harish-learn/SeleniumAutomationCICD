package ecomapplication.tests;

import ecomapplication.testComponents.BaseTest;
import ecomapplication.testComponents.Retry;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageobjects.CartPage;
import pageobjects.ProductCatelogPage;

import java.util.List;

public class ErrorValidations extends BaseTest {

    @Test(retryAnalyzer = Retry.class)
    public void loginPageErrorValidation()
    {
        ProductCatelogPage productCatelogPage=landingPage.loginApplication("postman_api@gmail.com", "1234589@hH");
        Assert.assertEquals("Incorrect email or password.",landingPage.getErrorMessage());
    }

    @Test
    public void productMatchFailureValidation() throws InterruptedException {
        //Global variables and code
        String productName="ZARA COAT 3";

        //Login to website
        ProductCatelogPage productCatelogPage=landingPage.loginApplication("postman_api@gmail.com", "123456789@hH");

        //wait till the all products are loaded //Getting all the products and adding the desired product to cart //Wait till the loading and add to cart success msg
        List<WebElement> products=productCatelogPage.getProductList();
        productCatelogPage.addProductToCart(productName);

        Thread.sleep(1000);

        //click on cart button
        CartPage cartPage=productCatelogPage.goToCart();

        //Verifying the added to cart product is present
        Boolean match=cartPage.verfiyProductDisplay("ZARA COAT 33");
        Assert.assertFalse(match);
    }
}
