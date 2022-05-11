import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;


public class ParkingCalculatorTest {
    static WebDriver driver;
    private static final org.apache.log4j.Logger logger = Logger.getLogger(String.valueOf(ParkingCalculatorTest.class));

    @BeforeMethod
    public void before() {
        String driverExecutablePath = "Utilizatori\\corina\\Descărcări\\geckodriver.exe";

        System.setProperty("webdriver.gecko.driver", driverExecutablePath);
        driver = new FirefoxDriver();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--start-maximized");
        DOMConfigurator.configure("log4j.xml");
        logger.info("Deschiderea site-ului");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); //wait before each element
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.get("https://www.shino.de/parkcalc/");
    }


    @DataProvider(name = "dataProvider" )
    public Object[][] complete(){

        return new Object[][]{
                {"Long-Term Garage Parking", "6/12/2022", "9:00", "PM", "6/16/2022", "11:00", "AM"},
                {"Economy Parking", "5/4/2022", "2:00", "PM", "6/4/2022", "2:00", "PM"},
                {"Short-Term Parking", "11/9/2022", "6:00", "AM", "11/12/2022", "4:00", "PM"},
                {"Valet Parking", "9/29/2022", "7:00", "AM", "9/29/2022", "12:00", "PM"}
        };
    }

    @Test(dataProvider = "dataProvider", threadPoolSize = 4)
    public void test(String parkingLotS, String date1S, String time1S, String ampm1, String date2S, String time2S, String ampm2) {

        WebElement parkingLot = driver.findElement(By.xpath("//select[@name='ParkingLot']"));
        parkingLot.sendKeys(parkingLotS);
        WebElement date1 = driver.findElement(By.xpath("//input[@name='StartingDate']"));
        date1.clear();
        date1.sendKeys(date1S);
        WebElement time1 = driver.findElement(By.xpath("//input[@name='StartingTime']"));
        time1.clear();
        time1.sendKeys(time1S);
        WebElement radio1 = driver.findElement(By.xpath("//input[@name='StartingTimeAMPM'][@value='"+ampm1+"']"));
        radio1.click();
        WebElement date2 = driver.findElement(By.xpath("//input[@name='LeavingDate']"));
        date2.clear();
        date2.sendKeys(date2S);
        WebElement time2 = driver.findElement(By.xpath("//input[@name='LeavingTime']"));
        time2.clear();
        time2.sendKeys(time2S);
        WebElement radio2 = driver.findElement(By.xpath("//input[@name='LeavingTimeAMPM'][@value='"+ampm2+"']"));
        radio2.click();

        WebElement button = driver.findElement(By.xpath("//input[@name='Submit']"));
        button.click();
    }

    @AfterMethod
    public void after() {
        /*if (driver != null) {
//            driver.close();
            driver.quit();
        }*/
    }
}
