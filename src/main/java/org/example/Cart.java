package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Cart {
    ChromeDriver chromeDriver;
    @BeforeMethod
    public void setup()
    {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        chromeDriver.get("https://www.saucedemo.com/");
    }
    @Test
    public  void checkcombo ()
    {
        login("standard_user", "secret_sauce");
        sleep(5000);
        clickcart();
        sleep(2000);
    }
    private void login(String username, String password) {
        WebElement usernameField = chromeDriver.findElement(By.xpath("//input[@id='user-name']"));
        usernameField.sendKeys(username);

        WebElement passwordField = chromeDriver.findElement(By.xpath("//input[@id='password']"));
        passwordField.sendKeys(password);

        sleep(2000);

        WebElement loginButton = chromeDriver.findElement(By.xpath("//input[@id='login-button']"));
        loginButton.click();

        sleep(2000);
    }
    public  void  clickcart()
    {
        WebElement cart = chromeDriver.findElement(By.xpath("//body/div[@id='root']/div[@id='page_wrapper']/div[@id='contents_wrapper']/div[@id='header_container']/div[1]/div[3]/a[1]"));
        cart.click();
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("https://www.saucedemo.com/cart.html"));
        String url = chromeDriver.getCurrentUrl();
        if(url.equals("https://www.saucedemo.com/cart.html"))
        {
            System.out.println("DA CHUYEN HUONG DUNG TRANG GIO HANG" + " " + url);
        }
        else
        {
            System.out.println("KHONG DEN DUOC TRANG GIO HANG");
        }
        sleep(5000);
        // Click button thanh toán
        WebElement buttoncheckout = chromeDriver.findElement(By.xpath("//button[@id='checkout']"));
        buttoncheckout.click();
        sleep(2000);
        // Hủy thanh toán => về lại trang giỏ hàng
        WebElement buttoncancel = chromeDriver.findElement(By.xpath("//button[@id='cancel']"));
        buttoncancel.click();
        sleep(2000);
        // Từ trang giỏ hàng Click tiếp tục mua sắm => Chuyển hướng về lại trang mua sắm
        WebElement buttoncontinuteshopping = chromeDriver.findElement(By.xpath("//button[@id='continue-shopping']"));
        buttoncontinuteshopping.click();
    }
    @AfterMethod
    public void cleanUp() {
        if (chromeDriver != null) {
            chromeDriver.quit();
        }
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
