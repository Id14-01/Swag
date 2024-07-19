package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class Login {
    ChromeDriver chromeDriver;
    @Test
    public  void login(ChromeDriver chromeDriver)
    {
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
    }
    @Test
    public void usernameblank()
    {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        chromeDriver.get("https://www.saucedemo.com/");
        WebElement password = chromeDriver.findElement(By.xpath("//input[@id='password']"));
        password.sendKeys("secret_sauce");
        sleep(2000);
        WebElement login = chromeDriver.findElement(By.xpath("//input[@id='login-button']"));
        login.click();
        sleep(2000);
        WebElement erroorussername = chromeDriver.findElement(By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]/h3[1]"));
        String errorexpect = "Epic sadface: Username is required";
        assert erroorussername.getText().equals(errorexpect) : "Kết quả mong đợi không giống với kết quả thực tại";
    }
    @Test
    public  void passwordblank()
    {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        chromeDriver.get("https://www.saucedemo.com/");
        WebElement username = chromeDriver.findElement(By.xpath("//input[@id='user-name']"));
        username.sendKeys("standard_user");
        sleep(2000);
        WebElement login = chromeDriver.findElement(By.xpath("//input[@id='login-button']"));
        login.click();
        sleep(2000);
        WebElement errorpassword = chromeDriver.findElement(By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]/h3[1]"));
        String errorexpect = "Epic sadface: Password is required";
        assert errorpassword.getText().equals(errorexpect) : "Kết quả mong đợi không khớp với kết quả thực tại";
    }
    @Test
    public  void errorpassorusername ()
    {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        chromeDriver.get("https://www.saucedemo.com/");
        WebElement username = chromeDriver.findElement(By.xpath("//input[@id='user-name']"));
        username.sendKeys("pumnehihi");
        WebElement password = chromeDriver.findElement(By.xpath("//input[@id='password']"));
        password.sendKeys("secret_sauce");
        WebElement login = chromeDriver.findElement(By.xpath("//input[@id='login-button']"));
        login.click();
        WebElement error = chromeDriver.findElement(By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]/h3[1]"));
        String errorexpect = "Epic sadface: Username and password do not match any user in this service";
        assert error.getText().equals(errorexpect): "kết quả không khớp với kết quả thực tại";
        sleep(2000);
        // username đúng, password sai
        username = chromeDriver.findElement(By.xpath("//input[@id='user-name']"));
        username.clear();
        sleep(2000);
        username.sendKeys("standard_user");
        password = chromeDriver.findElement(By.xpath("//input[@id='password']"));
        password.clear();
        sleep(2000);
        password.sendKeys("wrongpassword");
        login = chromeDriver.findElement(By.xpath("//input[@id='login-button']"));
        login.click();
        sleep(2000);
        WebElement error2 = chromeDriver.findElement(By.xpath("//body/div[@id='root']/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/div[3]/h3[1]"));
        String errorexpect2= "Epic sadface: Username and password do not match any user in this service";
        assert error2.getText().equals(errorexpect2) : "Kết quả thực tại không khớp với kết quả mong đợi";
    }
    @AfterMethod
    public  void CleanUp()
    {
        //chromeDriver.quit();
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
