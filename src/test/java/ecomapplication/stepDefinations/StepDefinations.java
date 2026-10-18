package ecomapplication.stepDefinations;

import ecomapplication.testComponents.BaseTest;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pageobjects.*;

import java.io.IOException;
import java.util.List;

public class StepDefinations extends BaseTest {

    public LandingPage landingPage;
    public ProductCatelogPage productCatelogPage;
    public CartPage cartPage;
    public CheckOutPage checkOutPage;
    public ConfirmationPage confirmationPage;

    @Given("User landed on Ecommerce page")
    public void user_landed_on_ecommerce_page() throws IOException {
        landingPage=launchApplication();
    }

    @Given("^User logged in with username (.+) and password (.+)$")
    public void user_logged_in_with_username_password(String userName, String password)
    {
        productCatelogPage=landingPage.loginApplication(userName,password);
    }

    @When("^User added product (.+) to cart$")
    public void user_added_product_to_cart(String productName) throws InterruptedException {
        List<WebElement> products=productCatelogPage.getProductList();
        productCatelogPage.addProductToCart(productName);
    }

    @And("^Checkout (.+) and submit the order$")
    public void checkout_submit_the_order(String productName)
    {
        cartPage=productCatelogPage.goToCart();

        //Verifying the added to cart product is present
        Boolean match=cartPage.verfiyProductDisplay(productName);
        Assert.assertTrue(match);

        //checkout button
        checkOutPage =cartPage.goToCheckout();
        checkOutPage.selectCountry("India");

        //Place order button
        confirmationPage=checkOutPage.submitOrder();
    }

    @Then("{string} message is displayed in confiirmation page")
    public void confirmation_message_displayed(String string)
    {
        String successMessage=confirmationPage.getSuccessMessage();
        Assert.assertTrue(successMessage.equalsIgnoreCase(string));
        driver.close();
    }

    @Then("{string} error message is displayed")
    public void error_message_displayed(String string)
    {
        Assert.assertEquals(string,landingPage.getErrorMessage());
        driver.close();
    }
}
