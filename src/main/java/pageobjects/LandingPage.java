package pageobjects;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LandingPage extends AbstractComponent {

    WebDriver driver;

    public LandingPage(WebDriver driver)
    {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id="userEmail")
    WebElement useremail;

    @FindBy(id="userPassword")
    WebElement userpassword;

    @FindBy(id="login")
    WebElement login;

    @FindBy(css="[class*='flyInOut']")
    WebElement errorMessage;

    public ProductCatelogPage loginApplication(String email, String password)
    {
        useremail.sendKeys(email);
        userpassword.sendKeys(password);
        login.click();
        ProductCatelogPage productCatelogPage= new ProductCatelogPage(driver);
        return productCatelogPage;
    }

    public String getErrorMessage()
    {
        waitForWebElementToAppear(errorMessage);
        return errorMessage.getText();
    }

    public void goTo()
    {
        driver.get("https://rahulshettyacademy.com/client/");
    }
}
