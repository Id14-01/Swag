package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class Menu {
    ChromeDriver chromeDriver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        chromeDriver.get("https://www.saucedemo.com/");
    }

    @Test
    public void checkDisplayAndClick() {
        login("standard_user", "secret_sauce");
        clickMenuButton();
        checkMenuItems();
        nextpage();
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

    private void clickMenuButton() {
        // Chờ trang load
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
        // Đảm bảo rằng đã nhìn thấy trang và nút các thao tác thì mới tìm xpath
        WebElement menuButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='react-burger-menu-btn']")));
        //Đảm bảo rằng đã ti thấy nút và nút đã hiển thị
        if (menuButton != null && menuButton.isDisplayed()) {
            System.out.println("NUT MENU DA TIM THAY");
            // Cái nút click được
            wait.until(ExpectedConditions.elementToBeClickable(menuButton));
            menuButton.click();
            sleep(2000);
        } else {
            System.out.println("KHONG TIM THAY NUT MENU");
        }
    }

    private void checkMenuItems() {
        // Tìm tất cả các phần tử <a> có class là 'bm-item-list'
        List<WebElement> menuItems = chromeDriver.findElements(By.xpath("//nav[@class='bm-item-list']/a"));
        // Kiểm tra nếu số lượng các mục trong menu là 4
        if (menuItems.size() == 4) {
            System.out.println("MENU CO DU 4 MUC");
            // Duyệt qua từng mục và in ra text của mục đó
            for (WebElement item : menuItems) {
                System.out.println("- " + item.getText());
            }
        } else {
            // Nếu không đủ 4 mục, in ra số lượng các mục hiện có
            System.out.println("MENU KHONG CO DU 4 MUC CHI CO " + menuItems.size());
        }
    }
    public void  nextpage ()
    {
        WebElement nextpageabout = chromeDriver.findElement(By.xpath("//a[@id='about_sidebar_link']"));
        nextpageabout.click();
        //Chờ cho trang load xong
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(10));
        //Chờ load đúng cái địa chỉ trang mong muốn
        wait.until(ExpectedConditions.urlContains("https://saucelabs.com/"));
        //Lấy url thực tế so sánh với url mong đợi
        String url = chromeDriver.getCurrentUrl();
        if(url.equals("https://saucelabs.com/"))
        {
            System.out.println(" CHUYEN TRANG THANH CONG" + " " + url);
        }
        else
        {
            System.out.println("CHUYEN TRANG THAT BAI" + " "+ url);
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
