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

public class CheckOut {
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
        addtocart1and2();
        openandcheckcart();
        verifyTotalAmount();
        finish();
        testforminformation();
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
    public void addtocart1and2()
    {
        List<WebElement> products = chromeDriver.findElements(By.xpath("//div[@class='inventory_item']"));
        //Thêm sản phẩm thứ 1
        WebElement product1 = chromeDriver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']"));
        product1.click();
        sleep(2000);
        // Thêm sản phẩm thứ 2
        WebElement product2 = chromeDriver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-bike-light']"));
        product2.click();
        sleep(2000);
    }
    public  void openandcheckcart ()
    {
        // Mở trang giỏ hàng để kiểm tra
        WebElement cart = chromeDriver.findElement(By.xpath("//div[@id='shopping_cart_container']/a"));
        cart.click();
        sleep(2000);

        // Lấy các sản phẩm đã có trong giỏ hàng sau khi thêm
        List<WebElement> cartItems = chromeDriver.findElements(By.cssSelector(".cart_list .cart_item"));
        // Lấy các sản phẩm đã click để thêm vào giỏ hàng
        List<WebElement> addedToCart = chromeDriver.findElements(By.cssSelector(".inventory_item"));
        boolean allProductsMatch = true;
        for (WebElement addedProduct : addedToCart) {
            boolean productFound = false;
            String productName = addedProduct.findElement(By.cssSelector(".inventory_item_name")).getText().trim();
            for (WebElement cartItem : cartItems) {
                String cartItemName = cartItem.findElement(By.cssSelector(".inventory_item_name")).getText().trim();
                if (productName.equals(cartItemName)) {
                    productFound = true;
                    break;
                }
            }
            if (!productFound) {
                allProductsMatch = false;
                break;
            }
        }
        if (allProductsMatch) {
            System.out.println("CAC SAN PHAM DUOC THEM VAO GIO HANG KHOP VOI CAC SAN PHAM TRONG GIO HANG");
        } else {
            System.out.println("CO LOI XAY RA. CAC SAN PHAM DUOC THEM KHONNG KHOP VOI CAC SAN PHAM TRONG GIO HANG");
        }

        // Nếu tất cả sản phẩm khớp, click thanh toán
        if (allProductsMatch) {
            WebElement buttonCheckout = chromeDriver.findElement(By.xpath("//button[@id='checkout']"));
            buttonCheckout.click();
            sleep(2000);

            // Điền thông tin để tiếp tục đến bước xác nhận
            WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='first-name']")));
            WebElement lastName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='last-name']")));
            WebElement postal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='postal-code']")));

            firstName.sendKeys("DUYEN");
            lastName.sendKeys("MY");
            postal.sendKeys("1234");

            WebElement buttonContinue = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='continue']")));
            buttonContinue.click();
            sleep(2000);

            // Kiểm tra đã chuyển hướng đúng trang thông tin thanh toán
            wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/checkout-step-two.html"));
            sleep(2000);
        }
    }
    private void verifyTotalAmount() {
        // Lấy danh sách các sản phẩm trong giỏ hàng
        List<WebElement> cartItems = chromeDriver.findElements(By.className("cart_item"));

        double expectedTotal = 0.0;

        // Duyệt qua từng sản phẩm và lấy giá
        for (WebElement item : cartItems) {
            WebElement priceElement = item.findElement(By.className("inventory_item_price"));
            String priceText = priceElement.getText().trim().replace("$", "");
            double price = Double.parseDouble(priceText);
            expectedTotal += price;
        }

        // Lấy giá trị thuế
        WebElement taxElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("summary_tax_label")));
        String taxText = taxElement.getText().trim().replace("Tax: $", "");
        double tax = Double.parseDouble(taxText);

        // Tính tổng tiền mong đợi bao gồm thuế
        expectedTotal += tax;

        // Xác minh tổng tiền trên trang thanh toán
        WebElement totalPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("summary_total_label")));
        String totalAmountText = totalPriceElement.getText().trim();
        System.out.println("TONG TIEN HIEN THI LA: " + totalAmountText);

        // Trích xuất tổng số tiền từ văn bản
        String totalAmount = totalAmountText.replace("Total: $", "").trim();
        double total = Double.parseDouble(totalAmount);

        // So sánh tổng tiền mong đợi và tổng tiền hiển thị
        assert total == expectedTotal : String.format("TONG TIEN HIEN THI KHONG KHOP. MONG DOI: $%.2f, NHUNG NHAN DUOC: $%.2f", expectedTotal, total);
    }
    private  void finish()
    {
        WebElement buttonfinish = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='finish']")));
        buttonfinish.click();
        sleep(2000);
        WebElement buttonbackhome = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@id='back-to-products']")));
        buttonbackhome.click();
        sleep(2000);
    }
    public void testforminformation() {
        // Quay lại trang trước
        chromeDriver.get("https://www.saucedemo.com/checkout-step-one.html");
        // Trường hợp 1: Nhập dữ liệu cho firstname, để trống postal và lastname
        WebElement firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='first-name']")));
        WebElement lastName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='last-name']")));
        WebElement postal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='postal-code']")));

        firstName.sendKeys("TUI");
        lastName.clear();
        postal.clear();

        WebElement buttonContinue = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='continue']")));
        buttonContinue.click();
        sleep(5000);
        WebElement errorMessage1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(@data-test, 'error')]")));
        String expectedError1 = "Error: Last Name is required";
        String actualErrorMessage1 = errorMessage1.getText().trim();  // Ensure there's no extra whitespace
        System.out.printf("Expected error: '%s', Actual error: '%s'%n", expectedError1, actualErrorMessage1);
        assert expectedError1.equals(actualErrorMessage1) : String.format("Thông báo lỗi mong đợi: '%s', nhưng nhận được: '%s'", expectedError1, actualErrorMessage1);

        // Trường hợp 2: Nhập lastname, firstname và để trống postal
        firstName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='first-name']")));
        lastName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='last-name']")));
        postal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='postal-code']")));

        firstName.clear();
        lastName.clear();
        firstName.sendKeys("TUIII");
        lastName.sendKeys("NEEE");
        postal.clear(); // Clear the postal field if needed

        buttonContinue = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='continue']")));
        buttonContinue.click();
        sleep(5000);
        WebElement errorMessage3 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[contains(@data-test, 'error')]")));
        String expectedError3 = "Error: Postal Code is required";
        String actualErrorMessage3 = errorMessage3.getText().trim();  // Ensure there's no extra whitespace
        System.out.printf("Expected error: '%s', Actual error: '%s'%n", expectedError3, actualErrorMessage3);
        assert expectedError3.equals(actualErrorMessage3) : String.format("Thông báo lỗi mong đợi: '%s', nhưng nhận được: '%s'", expectedError3, actualErrorMessage3);
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
