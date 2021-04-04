package com.miamato.test;

import com.miamato.BaseTest;
import com.miamato.LogUtil;
import com.miamato.PropertyManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.List;

public class AmazonBasicTests extends BaseTest {


    private static final String AMAZON_HOME_PAGE_URL = PropertyManager.getProperty("homepage.url");
    private static final String AMAZON_HOME_PAGE_TITLE = PropertyManager.getProperty("homepage.title");
    //--------TEST 1---------
    private static final String NAVIGATOR_ITEM_1 = PropertyManager.getProperty("navigator.item.1");
    private static final String NAVIGATOR_ITEM_2 = PropertyManager.getProperty("navigator.item.2");
    private static final String NAVIGATOR_ITEM_3 = PropertyManager.getProperty("navigator.item.3");
    private static final String NAVIGATOR_ITEM_4 = PropertyManager.getProperty("navigator.item.4");
    private static final String NAVIGATOR_ITEM_5 = PropertyManager.getProperty("navigator.item.5");
    private static final String NAVIGATOR_ITEM_6 = PropertyManager.getProperty("navigator.item.6");
    private static final String NAVIGATOR_ITEM_7 = PropertyManager.getProperty("navigator.item.7");
    private static final String NAVIGATOR_ITEM_8 = PropertyManager.getProperty("navigator.item.8");
    private static final List<String> NAVIGATOR_ITEMS_LIST= Arrays.asList(
            NAVIGATOR_ITEM_1,
            NAVIGATOR_ITEM_2,
            NAVIGATOR_ITEM_3,
            NAVIGATOR_ITEM_4,
            NAVIGATOR_ITEM_5,
            NAVIGATOR_ITEM_6,
            NAVIGATOR_ITEM_7,
            NAVIGATOR_ITEM_8
    );

    //--------TEST 2---------
    private static final String SEARCH_TERM_1 = PropertyManager.getProperty("term.to.search.1");
    private static final String SEARCH_TERM_2 = PropertyManager.getProperty("term.to.search.2");
    private static final String SEARCH_TERM_3 = PropertyManager.getProperty("term.to.search.3");
    private static final String SEARCH_EXPECTED_DEPARTMENT_1 = PropertyManager.getProperty("search.result.department.1");
    private static final String SEARCH_EXPECTED_DEPARTMENT_2 = PropertyManager.getProperty("search.result.department.2");
    private static final String SEARCH_EXPECTED_DEPARTMENT_3 = PropertyManager.getProperty("search.result.department.3");
    //--------TEST 3---------
    private static final String NUMBER_OF_PRODUCTS = PropertyManager.getProperty("product.count");
    //--------TEST 4---------
    private static final String NUMBER_OF_PRODUCTS_AFTER_REMOVAL = PropertyManager.getProperty("product.count.after.remove");


    //--------TEST 1---------
    private static final String ACCEPT_COOKIES_BUTTON_XPATH = "//input[@id='sp-cc-accept']";
    private static final String NAVIGATOR_ITEMS_XPATH = "//div[@id='nav-xshop']//a";
    //--------TEST 2---------
    private static final String SEARCH_FIELD_XPATH = "//input[@id='twotabsearchtextbox']";
    private static final String SEARCH_BUTTON_XPATH = "//input[@id='nav-search-submit-button']";
    private static final String SEARCH_RESULTS_DEPARTMENTS_IN_LEFT_MENU_XPATH = "//div[@id='departments']//span[@class='a-size-base a-color-base']";
    private static final int TARGET_DEPARTMENT_INDEX = 0;
    //--------TEST 3---------
    private static final String PRODUCT_TO_ADD_TO_CART_XPATH = "//div[@data-index = 4]//span[contains (@class, 'a-color-base a-text-normal')]";
    private static final String SELECT_NUMBER_OF_PRODUCTS_XPATH = "//select[@id='quantity']";
    private static final String ADD_TO_BASKET_BUTTON_XPATH = "//input[@id='add-to-cart-button']";
    private static final String BASKET_XPATH = "//span[@id='nav-cart-count']";
    private static final int TARGET_BASKET_INDEX = -1;
    //--------TEST 4---------
    private static final String BASKET_LINK_XPATH = "//span[@id='nav-cart-count']//parent::div//parent::a";
    private static final String REMOVE_ALL_PRODUCTS_FROM_BASKET_XPATH = "//input[@value='Delete']";


    @Test
    public void checkNavigatorItemsName() {
        openWebPage(AMAZON_HOME_PAGE_URL);
        assertThatTitleIsCorrect(AMAZON_HOME_PAGE_TITLE);
        acceptCookiesIfPopupPresent();
        for (int i = 0; i < NAVIGATOR_ITEMS_LIST.size(); i++)
            assertThatItemIsPresentInNavigator(i, NAVIGATOR_ITEMS_XPATH, NAVIGATOR_ITEMS_LIST.get(i));
        softAssert.assertAll();
    }


    @DataProvider(name = "search-term-set")
    public Object[][] searchTerms() {
        return new Object[][]
                {{SEARCH_TERM_1, SEARCH_EXPECTED_DEPARTMENT_1}
                        ,{SEARCH_TERM_2, SEARCH_EXPECTED_DEPARTMENT_2}
                        ,{SEARCH_TERM_3, SEARCH_EXPECTED_DEPARTMENT_3}};
    }


    @Test(dataProvider = "search-term-set")
    public void basicAmazonProductSearch(String searchTerm, String expectedDepartmentName) {
        openWebPage(AMAZON_HOME_PAGE_URL);
        assertThatTitleIsCorrect(AMAZON_HOME_PAGE_TITLE);
        acceptCookiesIfPopupPresent();
        findAndFillInput(SEARCH_FIELD_XPATH, searchTerm);
        clickOnElement(SEARCH_BUTTON_XPATH);
        assertThatTextIsPresentInField(TARGET_DEPARTMENT_INDEX, SEARCH_RESULTS_DEPARTMENTS_IN_LEFT_MENU_XPATH, expectedDepartmentName);
    }

    @Test
    public void addProductToCart() {
        openWebPage(AMAZON_HOME_PAGE_URL);
        assertThatTitleIsCorrect(AMAZON_HOME_PAGE_TITLE);
        acceptCookiesIfPopupPresent();
        findAndFillInput(SEARCH_FIELD_XPATH, SEARCH_TERM_1);
        clickOnElement(SEARCH_BUTTON_XPATH);
        clickOnElement(PRODUCT_TO_ADD_TO_CART_XPATH);
        selectFromDropdownByValue(SELECT_NUMBER_OF_PRODUCTS_XPATH, NUMBER_OF_PRODUCTS);
        clickOnElement(ADD_TO_BASKET_BUTTON_XPATH);
        assertThatTextIsPresentInField(TARGET_BASKET_INDEX, BASKET_XPATH, NUMBER_OF_PRODUCTS);
    }

    @Test
    public void addProductToCartAndThenRemove() {
        openWebPage(AMAZON_HOME_PAGE_URL);
        assertThatTitleIsCorrect(AMAZON_HOME_PAGE_TITLE);
        acceptCookiesIfPopupPresent();
        findAndFillInput(SEARCH_FIELD_XPATH, SEARCH_TERM_1);
        clickOnElement(SEARCH_BUTTON_XPATH);
        clickOnElement(PRODUCT_TO_ADD_TO_CART_XPATH);
        selectFromDropdownByValue(SELECT_NUMBER_OF_PRODUCTS_XPATH, NUMBER_OF_PRODUCTS);
        clickOnElement(ADD_TO_BASKET_BUTTON_XPATH);
        clickOnElement(BASKET_LINK_XPATH);
        clickOnElement(REMOVE_ALL_PRODUCTS_FROM_BASKET_XPATH);
        assertThatTextIsPresentInField(TARGET_BASKET_INDEX, BASKET_XPATH, NUMBER_OF_PRODUCTS_AFTER_REMOVAL);
    }

    @Step("Open web page")
    private static void openWebPage(String url){
        logger.info(AmazonBasicTests.class.getName() + " Navigating to website with url:   " + url);
        driver.navigate().to(url);
    }

    @Step("Get title from page")
    private static String getPageTitle() {
        logger.info(AmazonBasicTests.class.getName() + " Get title from page");
        return driver.getTitle();
    }

    @Step("Input {1} in the search field")
    private static void findAndFillInput(String xpath, String text){
        logger.info(AmazonBasicTests.class.getName() + " Input: " + text);
        WebElement element = findElement(xpath);
        element.sendKeys(text);
    }

    @Step("Click on element with xpath {0}")
    private static void clickOnElement(String xpath){
        clickOnElement(xpath, -1);
    }

    @Step("Click on element from list with position {1}")
    private static void clickOnElement(String xpath, int position){
        logger.info(AmazonBasicTests.class.getName() + " Clicking on elment with xpath: " + xpath);
        Actions actions = new Actions(driver);
        actions.click(findElement(xpath, position)).perform();
    }

    @Step("Select option {1} from drop-down with xpath {0}")
    private static void selectFromDropdownByValue(String xpath, String value){
        logger.info(AmazonBasicTests.class.getName()+ " Trying to select option: \"" + value + "\" from dropdown with XPath: " + xpath );
        Select dropdown = new Select(findElement(xpath));
        try {
            dropdown.selectByValue(value);
        } catch (Exception e) {
            logger.error(AmazonBasicTests.class.getName() + " Option cannot be selected from dropdown");
            LogUtil.logStackTrace(e, logger);
            throw e;
        }
    }

    @Step
    private static void assertThatTitleIsCorrect(String text) {
        logger.info(AmazonBasicTests.class.getName() + " Checking that title: \"" + text
                + "\" is correct");
        try{
            Assert.assertEquals(getPageTitle(), text);
            logger.info(AmazonBasicTests.class.getName() + " Title is equal to expected");
        } catch (Exception e) {
            logger.error(AmazonBasicTests.class.getName() + e.getLocalizedMessage());
            LogUtil.logStackTrace(e, logger);
            throw e;
        }
    }

    @Step
    private static void assertThatTextIsPresentInField(int position, String xpath, String text) {
        logger.info(AmazonBasicTests.class.getName() + " Checking that text: \"" + text
                + "\" is present in field with XPath:  " + xpath);
        try{
            Assert.assertEquals(findElement(xpath, position).getText(), text);
            logger.info(AmazonBasicTests.class.getName() + " Text is equal to expected");
        } catch (Exception e) {
            logger.error(AmazonBasicTests.class.getName() + e.getLocalizedMessage());
            LogUtil.logStackTrace(e, logger);
            throw e;
        }
    }

    @Step
    private static void assertThatItemIsPresentInNavigator(int position, String xpath, String text) {
        logger.info(AmazonBasicTests.class.getName() + " Checking that text: \"" + text
                + "\" is present in field with XPath:  " + xpath);
        try{
            softAssert.assertEquals(findElement(xpath, position).getText(), text);
            logger.info(AmazonBasicTests.class.getName() + " Text is equal to expected");
        } catch (Exception e) {
            logger.error(AmazonBasicTests.class.getName() + e.getLocalizedMessage());
            LogUtil.logStackTrace(e, logger);
            throw e;
        }
    }


    @Step
    private static void acceptCookiesIfPopupPresent(){
        try{
            logger.info(AmazonBasicTests.class.getName() + " Accept cookies");
            driver.findElement(By.xpath(ACCEPT_COOKIES_BUTTON_XPATH)).click();
        } catch(NoSuchElementException e) {
            logger.info(AmazonBasicTests.class.getName() + " Cookie accept pop-up is not displayed, so who cares");
            LogUtil.logStackTrace(e, logger);
        }
    }

    private static WebElement findElement(String xpath){
        return findElement(xpath, -1);
    }

    private static WebElement findElement(String xpath, int position){
        WebElement element = null;
        try{
            if(position == -1)
                element = driver.findElement(By.xpath(xpath));
            else
                element = driver.findElements(By.xpath(xpath)).get(position);
        } catch (Exception e) {
            logger.error(AmazonBasicTests.class.getName() + " Element with XPath: " + xpath + "  was not found on the page." );
            LogUtil.logStackTrace(e, logger);
        }
        return element;
    }
}
