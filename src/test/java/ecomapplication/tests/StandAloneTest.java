package ecomapplication.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class StandAloneTest {

    ExtentReports reports;

    @BeforeTest
    public void reports()
    {
        String path=System.getProperty("user.dir")+"//reports//index.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Ecom Test Automation");
        reporter.config().setDocumentTitle("Ecom Order");

        reports = new ExtentReports();
        reports.attachReporter(reporter);
        reports.setSystemInfo("Tester","Harish S");
    }

    @Test
    public void orderOnline() throws InterruptedException {
        ExtentTest test=reports.createTest("Order online");
        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://rahulshettyacademy.com/client/");

        //Global variables and code
        String productName="ZARA COAT 3";
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));

        //Login to website
        driver.findElement(By.id("userEmail")).sendKeys("postman_api@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("123456789@hH");
        driver.findElement(By.id("login")).click();

        //wait till the all products are loaded
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));

        //Getting all the products and adding the desired product to cart
        List<WebElement> products=driver.findElements(By.cssSelector(".mb-3"));
        WebElement prod=products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

        //Wait till the loading and add to cart success msg
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-container")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
        Thread.sleep(900);

        //click on cart button
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        //Verifying the added to cart product is present
        List<WebElement> cartProducts=driver.findElements(By.cssSelector(".cartSection h3"));
        Boolean match=cartProducts.stream().anyMatch(cartProduct->cartProduct.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);

        //checkout button
        driver.findElement(By.xpath("//ul[@style='list-style-type: none;']/descendant::button")).click();

        Actions action=new Actions(driver);
        action.sendKeys(driver.findElement(By.cssSelector("input[placeholder='Select Country']")),"india").build().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();

        //Place order button
        driver.findElement(By.cssSelector(".action__submit")).click();

        //success msg validation
        String successMessage=driver.findElement(By.cssSelector(".hero-primary")).getText();
        Assert.assertTrue(successMessage.equalsIgnoreCase("Thankyou for the order."));

        //closing the browser
        driver.close();
        //test.fail("Failed");
        reports.flush();
    }
}
