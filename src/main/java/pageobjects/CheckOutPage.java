package pageobjects;

import abstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CheckOutPage extends AbstractComponent {

    WebDriver driver;

    public CheckOutPage(WebDriver driver)
    {
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="input[placeholder='Select Country']")
    WebElement country;

    @FindBy(xpath="(//button[contains(@class,'ta-item')])[2]")
    WebElement selectCountry;

    @FindBy(css=".action__submit")
    WebElement submit;

    By results=(By.cssSelector(".ta-results"));

    public void selectCountry(String countryName)
    {
        Actions action=new Actions(driver);
        action.sendKeys(country,countryName).build().perform();

        waitForElementToAppear(results);
        selectCountry.click();
    }

    public ConfirmationPage submitOrder()
    {
        submit.click();
        ConfirmationPage confirmationPage = new ConfirmationPage(driver);
        return confirmationPage;
    }
}
