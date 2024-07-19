package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
public class Logout {
    ChromeDriver chromeDriver;
    @Test
    public  void logout()
    {
        // Đăng nhập vào
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        chromeDriver.get("https://www.saucedemo.com/");
        WebElement username = chromeDriver.findElement(By.xpath("//input[@id='user-name']"));
        username.sendKeys("standard_user");
        WebElement password = chromeDriver.findElement(By.xpath("//input[@id='password']"));
        password.sendKeys("secret_sauce");
        sleep(2000);
        WebElement login = chromeDriver.findElement(By.xpath("//input[@id='login-button']"));
        login.click();
        sleep(2000);
        //Kiểm tra url trang hiện tại
        Assert.assertEquals(chromeDriver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");
        // Tìm nut đăng xuát và thực hiện đăng xuất
        WebElement menu = chromeDriver.findElement(By.xpath("//button[@id='react-burger-menu-btn']"));
        menu.click();
        sleep(2000);
        WebElement logout = chromeDriver.findElement(By.xpath("//a[@id='logout_sidebar_link']"));
        logout.click();
        sleep(2000);
        Assert.assertEquals(chromeDriver.getCurrentUrl(), "https://www.saucedemo.com/");
    }
    public  void sleep(int time)
    {
        try {
            Thread.sleep(time);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
