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

public class AddToCart {
    ChromeDriver chromeDriver;
    WebDriverWait wait;
    @BeforeMethod
    public void setup()
    {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
        chromeDriver.get("https://www.saucedemo.com/");
    }
    @Test
    public  void addtocart ()
    {
        login("standard_user", "secret_sauce");
        addcartandremove();
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
    public void addcartandremove() {
        // Tìm các sản phẩm trên trang
        List<WebElement> products = chromeDriver.findElements(By.xpath("//div[@class='inventory_item']"));

        for (int i = 0; i < products.size(); i++) {
            // Lấy lại danh sách sản phẩm mỗi lần lặp để đảm bảo không gặp lỗi stale element reference
            products = chromeDriver.findElements(By.xpath("//div[@class='inventory_item']"));

            // Lấy tên sản phẩm trước khi click
            String productName = products.get(i).findElement(By.className("inventory_item_name")).getText();

            // Click vào nút "Add to Cart" của sản phẩm
            WebElement addToCartButton = products.get(i).findElement(By.xpath(".//button[contains(@class, 'btn_inventory')]"));
            addToCartButton.click();
            sleep(2000);

            // Kiểm tra xem nút đã chuyển thành "Remove" sau khi thêm vào giỏ hàng
            WebElement removeButton = products.get(i).findElement(By.xpath(".//button[contains(@class, 'btn_inventory') and contains(@class, 'btn_secondary')]"));
            if (removeButton.isDisplayed()) {
                System.out.println("SAN PHAM '" + productName + "' DA DUOC THEM VAO GIO HANG THANH CONG");
            } else {
                System.out.println("Không thể thêm sản phẩm '" + productName + "' vào giỏ hàng.");
            }

            // Click vào trang giỏ hàng để kiểm tra
            WebElement cart = chromeDriver.findElement(By.xpath("//div[@id='shopping_cart_container']/a"));
            cart.click();
            sleep(2000);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='continue-shopping']")));
            // Tìm nút continute shopping
            WebElement contiinuteshopping = chromeDriver.findElement(By.xpath("//button[@id='continue-shopping']"));
            contiinuteshopping.click();
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
