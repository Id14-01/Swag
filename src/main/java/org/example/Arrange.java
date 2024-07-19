package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Arrange {
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
    public  void arrange()
    {
        login("standard_user", "secret_sauce");
        checkitems();
        sortProductsAtoZ();
        verifyProductsSortedAtoZ();
        sleep(4000);
        sortProductsztoA();
        verifyProductsSortedZtoA();
        sleep(4000);
        pricelowtohigh();
        verifyProductsSortedPriceLowToHigh();
        sleep(4000);
        pricehightolow();
        verifyProductsSortedPriceHighToLow();
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
    // Kiểm tra combobox có đủ 4 mục cần sắp xếp
    public void checkitems() {
        WebElement select = chromeDriver.findElement(By.xpath("//select[@class='product_sort_container']"));
        List<WebElement> allitems = chromeDriver.findElements(By.tagName("option"));
        if (allitems.size() == 4) {
            System.out.println("DA DU CAC MUC CAN SAP XEP");
            for (WebElement item : allitems) {
                System.out.println(item.getText());
            }
        } else {
            System.out.println("KHONG DU CAC MUC CAN SAP XEP: " + allitems.size());
        }
    }
    // Chọn mục từ A tới Z trong combobox
    public void sortProductsAtoZ()
    {
        WebElement select = chromeDriver.findElement(By.xpath("//select[@class='product_sort_container']"));
        select.click();
        WebElement optionAZ = chromeDriver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[1]"));
        optionAZ.click();
        sleep(2000);
    }
    public void sortProductsztoA()
    {
        WebElement select = chromeDriver.findElement(By.xpath("//select[@class='product_sort_container']"));
        select.click();
        WebElement optionZA = chromeDriver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[2]"));
        optionZA.click();
        sleep(2000);
    }
    public void pricelowtohigh()
    {
        WebElement select = chromeDriver.findElement(By.xpath("//select[@class='product_sort_container']"));
        select.click();
        WebElement optionlowtohigh = chromeDriver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[3]"));
        optionlowtohigh.click();
        sleep(2000);
    }
    public void pricehightolow()
    {
        WebElement select = chromeDriver.findElement(By.xpath("//select[@class='product_sort_container']"));
        select.click();
        WebElement optionlowtohigh = chromeDriver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/option[4]"));
        optionlowtohigh.click();
        sleep(2000);
    }
    // Kiểm tra sản phẩm có được sắp xếp từ A tới Z hay không
    public void verifyProductsSortedAtoZ() {
        List<WebElement> productElements = chromeDriver.findElements(By.className("inventory_item_name"));
        List<String> productNames = new ArrayList<>();
        for (WebElement productElement : productElements) {
            productNames.add(productElement.getText());
        }

        List<String> sortedProductNames = new ArrayList<>(productNames);
        sortedProductNames.sort(String::compareTo);

        if (productNames.equals(sortedProductNames)) {
            System.out.println("SAN PHAM DA DUOC SAP XEP TU A TOI Z");
        } else {
            System.out.println("SAN PHAM KHONG DUOC SAP XEP TU A TOI Z");
        }
    }
    // Kiểm tra sản phẩm có được sắp xếp từ Z tới A hay không
    public void verifyProductsSortedZtoA() {
        // Lấy danh sách các tên sản phẩm hiển thị trên trang
        List<WebElement> productElements = chromeDriver.findElements(By.className("inventory_item_name"));
        List<String> productNames = new ArrayList<>();
        for (WebElement productElement : productElements) {
            productNames.add(productElement.getText());
        }

        // Tạo một bản sao của danh sách tên sản phẩm và sắp xếp nó theo thứ tự ngược lại (Z đến A)
        List<String> sortedProductNames = new ArrayList<>(productNames);
        sortedProductNames.sort((a, b) -> b.compareTo(a));

        // So sánh danh sách gốc với danh sách đã sắp xếp để kiểm tra
        if (productNames.equals(sortedProductNames)) {
            System.out.println("SAN PHAM DA DUOC SAP XEP TU Z TOI A");
        } else {
            System.out.println("SAN PHAM KHONG DUOC SAP XEP TU Z TOI A");
        }
    }
    // Kiểm tra sản phẩm có được sắp xếp theo giá từ thấp đến cao hay không
    public void verifyProductsSortedPriceLowToHigh() {
        // Lấy danh sách các phần tử sản phẩm
        List<WebElement> productElements = chromeDriver.findElements(By.className("inventory_item_price"));
        List<Double> productPrices = new ArrayList<>();
        for (WebElement productElement : productElements) {
            // Lấy giá sản phẩm và chuyển đổi thành kiểu Double
            String priceText = productElement.getText().replace("$", "");
            productPrices.add(Double.parseDouble(priceText));
        }

        // Tạo một bản sao của danh sách giá sản phẩm và sắp xếp nó theo giá từ thấp đến cao
        List<Double> sortedProductPrices = new ArrayList<>(productPrices);
        sortedProductPrices.sort(Double::compareTo);

        // So sánh danh sách giá gốc với danh sách đã sắp xếp để kiểm tra
        if (productPrices.equals(sortedProductPrices)) {
            System.out.println("SAN PHAM DA DUOC SAP XEP THEO GIA TU THAP DEN CAO");
        } else {
            System.out.println("SAN PHAM KHONG DUOC SAP XEP THEO GIA TU THAP DEN CAO");
        }
    }
    public void verifyProductsSortedPriceHighToLow() {
        List<WebElement> productElements = chromeDriver.findElements(By.className("inventory_item_price"));
        List<Double> productPrices = new ArrayList<>();
        for (WebElement productElement : productElements) {
            String priceText = productElement.getText().replace("$", "");
            productPrices.add(Double.parseDouble(priceText));
        }

        List<Double> sortedProductPrices = new ArrayList<>(productPrices);
        sortedProductPrices.sort((a, b) -> b.compareTo(a));

        if (productPrices.equals(sortedProductPrices)) {
            System.out.println("SAN PHAM DA DUOC SAP XEP THEO GIA TU CAO DEN THAP");
        } else {
            System.out.println("SAN PHAM KHONG DUOC SAP XEP THEO GIA TU CAO DEN THAP");
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
