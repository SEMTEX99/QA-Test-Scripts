package com.selenium;
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

public class AppTest {
    WebDriver driver;
    WebDriverWait wait;
    WebElement burgerMenu;
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
    public void verifyHeader() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("header_secondary_container")));
        WebElement productsHeader = driver.findElement(By.className("header_secondary_container"));
        Assert.assertTrue(productsHeader.isDisplayed());
    }
    @Test(dependsOnMethods = "verifyHeader", priority = 2)
    public void VerifyHeader () {
        WebElement shoppingCart = driver.findElement(By.className("shopping_cart_link"));
        Assert.assertTrue(shoppingCart.isDisplayed());
    }
    @Test(dependsOnMethods = "login", priority = 3)
    public void BurgerMenuBtn() {
        burgerMenu = driver.findElement(By.className("bm-burger-button"));
        Assert.assertTrue(burgerMenu.isDisplayed());
    }
    @Test(dependsOnMethods = "login", priority = 4)
    public void verifySocials() {
        WebElement twitterLink = driver.findElement(By.xpath("//a[@href='https://twitter.com/saucelabs']"));
        Assert.assertTrue(twitterLink.isDisplayed());
        WebElement facebookLink = driver.findElement(By.xpath("//a[@href='https://www.facebook.com/saucelabs']"));
        Assert.assertTrue(facebookLink.isDisplayed());
        WebElement linkedinLink = driver.findElement(By.xpath("//a[@href='https://www.linkedin.com/company/sauce-labs/']"));
        Assert.assertTrue(linkedinLink.isDisplayed());
    }
    @Test(priority = 5)
    public void Logout() {    
        burgerMenu.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout_sidebar_link")));
        WebElement Logout_btn = driver.findElement(By.id("logout_sidebar_link"));
        Logout_btn.click();
    }

    @AfterClass
    public void CloseBrowser() {
        driver.quit();
    }
}