package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ComboBoxARRANGE {
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
        checkitems();
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
    // Kiểm tra combobox có đủ 4 mục cần sắp xếp
    public  void checkitems()
    {
        WebElement select = chromeDriver.findElement(By.xpath("//select[@class='product_sort_container']"));
        List<WebElement> allitems = chromeDriver.findElements(By.tagName("option"));
        if (allitems.size() == 4)
        {
            System.out.println("DA DU CAC MUC CAN SAP XEP");
            for (WebElement item : allitems)
            {
                System.out.println(item.getText());
            }
        }
        else
        {
            System.out.println("KHONG DU CAC MUC CAN SAP XEP: " + allitems.size());
        }
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
