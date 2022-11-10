package com.selenium;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AppTest2 {
    WebDriver driver;
    WebDriverWait wait;
    WebElement burgerMenu;
    WebElement Backpack;
    WebElement Bck2ProductsBtn;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeTest
    public void LaunchBrave() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\BraveSoftware\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions options=new ChromeOptions();
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        driver= new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        wait = new WebDriverWait(driver, 5);
    }

    @Test
    public void login() {
        WebElement username = driver.findElement(By.id("user-name"));
        username.sendKeys("standard_user");
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("secret_sauce");
        WebElement loginButton = driver.findElement(By.className("btn_action"));
        loginButton.click();
    }
    @Test(dependsOnMethods = "login", priority = 1)
    public void sauceLabsBackpackClick() {
        WebElement Backpack = driver.findElement(By.xpath("//a[@id='item_4_title_link']/div"));
        Backpack.click();
    }
    @Test(dependsOnMethods = "sauceLabsBackpackClick", priority = 2)
    public void BackpackPriceVerify() throws Exception {
        try {
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='inventory_item_container']/div/div/div[2]/div")).getText(), "Sauce Labs Backpack");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@class='inventory_details_desc large_size']")).getText(), 
        "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.");
        Assert.assertEquals(driver.findElement(By.xpath("//div[@id='inventory_item_container']/div/div/div[2]/div[3]")).getText(),
        "$29.99");
        } 
        catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }
    @Test(dependsOnMethods = "sauceLabsBackpackClick", priority = 3)
    public void AddToCart() {
        WebElement AddToCartBtn = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        Assert.assertTrue(AddToCartBtn.isDisplayed());
        AddToCartBtn.click();
    }
    @Test(dependsOnMethods = "sauceLabsBackpackClick", priority = 4)
    public void BackToProducts() {
        WebElement Bck2ProductsBtn = driver.findElement(By.id("back-to-products"));
        Bck2ProductsBtn.click();
    }
    @Test(dependsOnMethods = "BackToProducts", priority = 5)
    public void JacketAdd() {
        WebElement JacketAddToCartBtn = driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket"));
        JacketAddToCartBtn.click();
    }
    @Test(dependsOnMethods = "JacketAdd", priority = 6)
    public void Go2ShoppingCart() {
        WebElement shoppingCart = driver.findElement(By.className("shopping_cart_link"));
        shoppingCart.click();
    }
    @Test(dependsOnMethods = "Go2ShoppingCart", priority = 7)
    public void Checkout() {
        WebElement Checkout = driver.findElement(By.id("checkout"));
        Checkout.click();
    }
    @Test(dependsOnMethods = "Checkout", priority = 8)
    public void FillOutDetails() {
        WebElement FirstName = driver.findElement(By.id("first-name"));
        FirstName.sendKeys("Test First Name");
        WebElement LastName = driver.findElement(By.id("last-name"));
        LastName.sendKeys("Test Last Name");
        WebElement PostalCode = driver.findElement(By.id("postal-code"));
        PostalCode.sendKeys("12345");
        WebElement ContinueBtn = driver.findElement(By.id("continue"));
        ContinueBtn.click();
    }
    @Test(dependsOnMethods = "FillOutDetails", priority = 9)
    public void ExitandLogout() {
        burgerMenu = driver.findElement(By.className("bm-burger-button"));
        burgerMenu.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));
        WebElement Logout_btn = driver.findElement(By.id("logout_sidebar_link"));
        Logout_btn.click();
    }

    @AfterClass (alwaysRun = true)
    public void CloseBrowser() {
        driver.quit();
    }
    public boolean ExceptionHandling(By by) throws Exception {
        try {
          driver.findElement(by);
          return true;
        } 
        catch (NoSuchElementException e) {
            return false;
        }
    }
}
