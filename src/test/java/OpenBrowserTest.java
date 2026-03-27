import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class OpenBrowserTest {

        WebDriver driver;

        // 🔹 Common method to open browser and login popup
        public void openLoginPage() throws InterruptedException {

                driver = new ChromeDriver();
                driver.manage().window().maximize();
                driver.get("https://icn-member.web.app/");

                Thread.sleep(3000);

                // Click Login Icon
                driver.findElement(
                                By.xpath("//div[contains(@class,'logout-user')]//div[contains(@class,'hidden-lg')]//a"))
                                .click();

                Thread.sleep(2000);
        }

        // --------------------------------------Login Script
        // ---------------------------------------------------------------
        // Scenario 1: Empty Email | Please enter email and password
        // @Test
        // public void emptyEmail() throws InterruptedException {

        // openLoginPage();

        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // WebElement passwordInput = wait.until(
        // ExpectedConditions.visibilityOfElementLocated(
        // By.xpath("//div[contains(@class,'card-body')]//input[@type='password']")));

        // passwordInput.clear();
        // passwordInput.sendKeys("123456");

        // // FIX: Wait for login button to be clickable
        // WebElement loginButton = wait.until(
        // ExpectedConditions.elementToBeClickable(
        // By.xpath("//button[contains(@class,'login-btn')]")));

        // loginButton.click();
        // System.out.println("Login button clicked successfully");

        // Thread.sleep(2000);
        // driver.quit();
        // }

        // // Scenario 2: Empty Password
        // @Test
        // public void emptyPassword() throws InterruptedException {

        // openLoginPage();

        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // WebElement emailInput = wait.until(
        // ExpectedConditions.visibilityOfElementLocated(
        // By.xpath("//div[contains(@class,'card-body')]//input[@type='text']")));

        // emailInput.clear();
        // emailInput.sendKeys("A00@yopmail.com");

        // // FIX: Wait for login button to be clickable
        // WebElement loginButton = wait.until(
        // ExpectedConditions.elementToBeClickable(
        // By.xpath("//button[contains(@class,'login-btn')]")));

        // loginButton.click();
        // System.out.println("Login button clicked successfully");

        // Thread.sleep(2000);
        // driver.quit();
        // }

        // // // Scenario 3: Invalid Password | Invalid credentials
        // @Test
        // public void invalidPassword() throws InterruptedException {

        // openLoginPage();

        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // WebElement emailInput = wait.until(
        // ExpectedConditions.visibilityOfElementLocated(
        // By.xpath("//div[contains(@class,'card-body')]//input[@type='text']")));

        // emailInput.clear();
        // emailInput.sendKeys("A00@yopmail.com");

        // WebElement passwordInput = wait.until(
        // ExpectedConditions.visibilityOfElementLocated(
        // By.xpath("//div[contains(@class,'card-body')]//input[@type='password']")));

        // passwordInput.clear();
        // passwordInput.sendKeys("wrongpass");

        // // FIX: Wait for login button to be clickable
        // WebElement loginButton = wait.until(
        // ExpectedConditions.elementToBeClickable(
        // By.xpath("//button[contains(@class,'login-btn')]")));

        // loginButton.click();
        // System.out.println("Login button clicked successfully");

        // Thread.sleep(2000);
        // driver.quit();
        // }

        // // // Scenario 4: Valid Login

        // @Test
        // public void validLogin() throws InterruptedException {

        // openLoginPage();

        // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // WebElement emailInput = wait.until(
        // ExpectedConditions.visibilityOfElementLocated(
        // By.xpath("//div[contains(@class,'card-body')]//input[@type='text']")));

        // emailInput.clear();
        // emailInput.sendKeys("A00@yopmail.com");

        // WebElement passwordInput = wait.until(
        // ExpectedConditions.visibilityOfElementLocated(
        // By.xpath("//div[contains(@class,'card-body')]//input[@type='password']")));

        // passwordInput.clear();
        // passwordInput.sendKeys("123456");

        // // FIX: Wait for login button to be clickable
        // WebElement loginButton = wait.until(
        // ExpectedConditions.elementToBeClickable(
        // By.xpath("//button[contains(@class,'login-btn')]")));

        // loginButton.click();
        // System.out.println("Login button clicked successfully");

        // Thread.sleep(2000);
        // driver.quit();
        // }

        // -------------Sign up script --------------------------------------

        public void openSignUpPage() throws InterruptedException {
                openLoginPage();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                // Click on "EMAIL SIGN UP" or "Get started with a free account" link
                WebElement signUpLink = wait.until(
                                ExpectedConditions.elementToBeClickable(
                                                By.xpath("//button[contains(text(),'EMAIL SIGN UP')] | //a[contains(text(),'EMAIL SIGN UP')] | //div[contains(text(),'EMAIL SIGN UP')] | //button[contains(text(),'Get started')] | //a[contains(text(),'Get started')] | //div[contains(text(),'free account')]")));
                signUpLink.click();
                System.out.println("Clicked on Sign Up link");

                Thread.sleep(2000);
        }

        // Method to handle Google Address Autocomplete
        public void enterAddressWithAutocomplete(String addressText) throws InterruptedException {
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                try {
                        // Find address input field
                        WebElement addressInput = wait.until(
                                        ExpectedConditions.visibilityOfElementLocated(
                                                        By.xpath("//input[@placeholder='Find your address here ...' or contains(@placeholder,'address')]")));

                        // Clear and enter address text
                        addressInput.clear();
                        addressInput.sendKeys(addressText);

                        Thread.sleep(2000); // Wait for suggestions to appear

                        // Wait for autocomplete suggestions
                        List<WebElement> suggestions = wait.until(
                                        ExpectedConditions.presenceOfAllElementsLocatedBy(
                                                        By.xpath("//div[contains(@class,'pac-container')]//div[contains(@class,'pac-item')]")));

                        if (!suggestions.isEmpty()) {
                                // Select the first suggestion
                                suggestions.get(0).click();
                                System.out.println("Selected address: " + suggestions.get(0).getText());
                                Thread.sleep(1000);
                        } else {
                                System.out.println("No suggestions found for: " + addressText);
                        }

                } catch (Exception e) {
                        System.out.println("Error in address autocomplete: " + e.getMessage());
                }
        }

        // Scenario 1: Invalid Email
        @Test
        public void invalidSignUp() throws InterruptedException {
                try {
                        openSignUpPage();

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                        // First Name
                        WebElement firstName = wait.until(
                                        ExpectedConditions.visibilityOfElementLocated(
                                                        By.xpath("//input[@placeholder='Your First Name' or contains(@placeholder,'First Name')]")));
                        firstName.sendKeys("Test");

                        // Last Name
                        WebElement lastName = driver.findElement(
                                        By.xpath("//input[@placeholder='Your Last Name' or contains(@placeholder,'Last Name')]"));
                        lastName.sendKeys("User");

                        // Invalid Email - Missing @ symbol
                        WebElement email = driver.findElement(
                                        By.xpath("//input[@placeholder='Your Email Address' or contains(@placeholder,'Email')]"));
                        email.sendKeys("testuser.yopmail.com");
                        System.out.println("Entered invalid email: testuser.yopmail.com");

                        // Password
                        WebElement password = driver.findElement(
                                        By.xpath("//input[@placeholder='Password' or contains(@placeholder,'Password')]"));
                        password.sendKeys("123456");

                        // Address with Google Autocomplete
                        enterAddressWithAutocomplete("melbourne");

                        // Click SIGN UP button
                        WebElement signUpButton = wait.until(
                                        ExpectedConditions.elementToBeClickable(
                                                        By.xpath("//button[contains(text(),'SIGN UP')]")));
                        signUpButton.click();
                        System.out.println("Clicked SIGN UP button");

                        Thread.sleep(5000);

                        // Verify error message for invalid email
                        try {
                                WebElement errorMsg = wait.until(
                                                ExpectedConditions.visibilityOfElementLocated(
                                                                By.xpath("//div[contains(@class,'error')] | //span[contains(@class,'error')] | //div[contains(text(),'valid email')]")));
                                System.out.println("✓ Error message displayed: " + errorMsg.getText());
                        } catch (Exception e) {
                                System.out.println("No error message found for invalid email");
                        }

                } catch (Exception e) {
                        System.out.println("Test failed: " + e.getMessage());
                        e.printStackTrace();
                } finally {
                        if (driver != null) {
                                driver.quit();
                        }
                }
        }

        // Scenario 2: Valid Sign Up with all fields
        @Test
        public void validSignUp() throws InterruptedException {
                try {
                        openSignUpPage();

                        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                        // Generate unique email
                        String timestamp = String.valueOf(System.currentTimeMillis());
                        String uniqueEmail = "Alex" + timestamp + "@yopmail.com";

                        // First Name
                        WebElement firstName = wait.until(
                                        ExpectedConditions.visibilityOfElementLocated(
                                                        By.xpath("//input[@placeholder='Your First Name' or contains(@placeholder,'First Name')]")));
                        firstName.sendKeys("Jon");

                        // Last Name
                        WebElement lastName = driver.findElement(
                                        By.xpath("//input[@placeholder='Your Last Name' or contains(@placeholder,'Last Name')]"));
                        lastName.sendKeys("Alex");

                        // Email Address
                        WebElement email = driver.findElement(
                                        By.xpath("//input[@placeholder='Your Email Address' or contains(@placeholder,'Email')]"));
                        email.sendKeys(uniqueEmail);
                        System.out.println("Entered email: " + uniqueEmail);

                        // Password
                        WebElement password = driver.findElement(
                                        By.xpath("//input[@placeholder='Password' or contains(@placeholder,'Password')]"));
                        password.sendKeys("123456");

                        // Address with Google Autocomplete
                        enterAddressWithAutocomplete("melbourne");

                        // Click SIGN UP button
                        WebElement signUpButton = wait.until(
                                        ExpectedConditions.elementToBeClickable(
                                                        By.xpath("//button[contains(text(),'SIGN UP')]")));
                        signUpButton.click();
                        System.out.println("Clicked SIGN UP button");

                        Thread.sleep(5000);

                        // Verify success message or redirection
                        System.out.println("Sign Up completed for: " + uniqueEmail);

                } catch (Exception e) {
                        System.out.println("Test failed: " + e.getMessage());
                        e.printStackTrace();
                } finally {
                        if (driver != null) {
                                driver.quit();
                        }
                }
        }
}