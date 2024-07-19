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

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import java.time.Duration;
import java.util.List;

public class AllItems {
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
        checknavigation();
        sleep(5000);
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
    // Kểm tra điều hướng đúng đến trang chi tiết sản phẩm
    public  void  checknavigation ()
    {
        List<WebElement> products = chromeDriver.findElements(By.xpath("//div[@class='inventory_list']/div"));

        for (int i = 0; i < products.size(); i++) {
            // Tìm lại danh sách sản phẩm và các phần tử sản phẩm để tránh tham chiếu phần tử lỗi thời
            products = chromeDriver.findElements(By.xpath("//div[@class='inventory_item']"));

            // Lấy tên sản phẩm trước khi click
            String productName = products.get(i).findElement(By.className("inventory_item_name")).getText();

            // Click vào liên kết sản phẩm
            products.get(i).findElement(By.className("inventory_item_img")).click();
            sleep(2000);

            // Lấy tiêu đề sản phẩm trên trang chi tiết sản phẩm
            WebElement productTitle = chromeDriver.findElement(By.className("inventory_details_name"));
            String productDetailTitle = productTitle.getText();

            if (productDetailTitle.equals(productName)) {
                System.out.println("DIEU HUONG DEN SAN PHAM  " + productName +" " + "THANH CONG.");
            } else {
                System.out.println("KHONG THE DIEU HUONG DEN TRANG SAN PHAM " + productName);
            }

            chromeDriver.navigate().back();
            sleep(2000);
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
