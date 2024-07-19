package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class navigateinformation {
    ChromeDriver chromeDriver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        chromeDriver = new ChromeDriver();
        wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(15));
        chromeDriver.get("https://www.saucedemo.com/");
    }

    @Test
    public void checkDisplayAndClick() {
        login("standard_user", "secret_sauce");
        navigateinformationpage();
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
    public void navigateinformationpage() {
            // Click vào trang Twitter
            WebElement twitter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='social_twitter']/a")));
            twitter.click();
            try {
                wait.until(ExpectedConditions.urlContains("https://x.com/saucelabs"));
            } catch (TimeoutException e) {
                System.out.println("Timeout while waiting for URL to contain 'https://x.com/saucelabs'");
                // Handle or log the TimeoutException here
            }

            // Kiểm tra URL sau khi click
            String currentUrl = chromeDriver.getCurrentUrl();
            if (currentUrl.contains("https://x.com/saucelabs")) {
                chromeDriver.navigate().back();
            }
            // Chờ cho đến khi URL chứa "https://www.saucedemo.com/inventory.html"
            wait.until(ExpectedConditions.urlContains("https://www.saucedemo.com/inventory.html"));

            // Click facebook
            WebElement facebook = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='social_facebook']/a")));
            facebook.click();
            try {
                wait.until(ExpectedConditions.urlContains("https://www.facebook.com/saucelabs"));
            }
            catch (TimeoutException e) {
                System.out.println("Timeout while waiting for URL to contain 'https://www.facebook.com/saucelabs'");
            }
            String currentUrl2 = chromeDriver.getCurrentUrl();
            if (currentUrl2.contains("https://www.facebook.com/saucelabs")) {
                chromeDriver.navigate().back();
            }
            wait.until(ExpectedConditions.urlContains("https://www.saucedemo.com/inventory.html"));
            // Click LinkedIn
            WebElement linkedIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@class='social_linkedin']/a")));
            linkedIn.click();
            try {
                wait.until(ExpectedConditions.urlContains("https://www.linkedin.com/company/sauce-labs/"));
            }
            catch (TimeoutException e) {
                System.out.println("Timeout while waiting for URL to contain 'https://www.linkedin.com/company/sauce-labs/'");
            }
            String currentUrl3 = chromeDriver.getCurrentUrl();
            if (currentUrl3.contains("https://www.linkedin.com/company/sauce-labs/")) {
                chromeDriver.navigate().back();
            }
            wait.until(ExpectedConditions.urlContains("https://www.saucedemo.com/inventory.html"));
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
