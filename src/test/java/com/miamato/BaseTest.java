package com.miamato;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Listeners;

@Listeners({TestResultsListener.class,TestReporter.class})
public abstract class BaseTest {

    public static WebDriver driver;
    static final String CHROME_DRIVER_PATH = "drivers/windows/chromedriver.exe";
    public static SoftAssert softAssert;
    public static final Logger logger = LogManager.getLogger("");

    @BeforeClass
    public void setup(){
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        softAssert = new SoftAssert();
    }

    @AfterClass
    public void cleanUp(){
        driver.quit();
    }
}