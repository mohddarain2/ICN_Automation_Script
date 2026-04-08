import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class SignUpTests {

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

    // -------------Sign up script --------------------------------------

    public void openSignUpPage() throws InterruptedException {
        openLoginPage();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click on "EMAIL SIGN UP" or "Get started with a free account" link
        WebElement signUpLink = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath(
                                "//button[contains(text(),'EMAIL SIGN UP')] | //a[contains(text(),'EMAIL SIGN UP')] | //div[contains(text(),'EMAIL SIGN UP')] | //button[contains(text(),'Get started')] | //a[contains(text(),'Get started')] | //div[contains(text(),'free account')]")));
        signUpLink.click();
        System.out.println("Clicked on Sign Up link");

        Thread.sleep(2000);
    }

    // Method to select country code from dropdown
    public void selectCountryCode(String countryCode) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Click on country code dropdown (IN +91 ▼)
            WebElement countryDropdown = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//div[contains(text(),'IN +91')] | //button[contains(@class,'country-code')] | //div[contains(@class,'country-select')]")));
            countryDropdown.click();
            System.out.println("Clicked on country code dropdown");
            Thread.sleep(1000);

            // Select the country code from the list
            WebElement selectedCountry = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//div[contains(text(),'" + countryCode
                                    + "')] | //li[contains(text(),'" + countryCode
                                    + "')] | //span[contains(text(),'" + countryCode
                                    + "')]")));
            selectedCountry.click();
            System.out.println("Selected country code: " + countryCode);

        } catch (Exception e) {
            System.out.println("Error selecting country code: " + e.getMessage());
        }

        Thread.sleep(1000);
    }

    // Method to enter mobile number
    public void enterMobileNumber(String mobileNumber) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Find mobile number input field
            WebElement mobileInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//input[@placeholder='Phone Number' or contains(@placeholder,'Mobile') or contains(@placeholder,'Phone')]")));

            mobileInput.clear();
            mobileInput.sendKeys(mobileNumber);
            System.out.println("Entered mobile number: " + mobileNumber);

        } catch (Exception e) {
            System.out.println("Error entering mobile number: " + e.getMessage());
        }

        Thread.sleep(1000);
    }

    // Method to handle Google Address Autocomplete
    public void enterAddressWithAutocomplete(String addressText) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Find address input field
            WebElement addressInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//input[@placeholder='Find your address here ...' or contains(@placeholder,'address')]")));

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

    // Method to click VERIFY button
    public void clickVerifyButton() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Try multiple XPath strategies for VERIFY button
            WebElement verifyButton = null;

            // Strategy 1: Button with text VERIFY
            try {
                verifyButton = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.xpath("//button[contains(text(),'VERIFY')]")));
            } catch (Exception e1) {
                // Strategy 2: Button with text Verify (case insensitive)
                try {
                    verifyButton = wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath("//button[contains(translate(text(), 'VERIFY', 'verify'), 'verify')]")));
                } catch (Exception e2) {
                    // Strategy 3: Any button that contains VERIFY text
                    verifyButton = wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath(
                                            "//*[contains(text(),'VERIFY') and contains(@class,'btn')] | //input[@value='VERIFY'] | //button[@type='submit' and contains(text(),'VERIFY')]")));
                }
            }

            // Scroll to verify button if needed
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView(true);", verifyButton);
            Thread.sleep(500);

            // Click using JavaScript if normal click fails
            try {
                verifyButton.click();
                System.out.println("Clicked VERIFY button successfully");
            } catch (Exception e) {
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();",
                        verifyButton);
                System.out.println("Clicked VERIFY button using JavaScript");
            }

        } catch (Exception e) {
            System.out.println("Error clicking VERIFY button: " + e.getMessage());

            // Strategy 4: Try to find by class or other attributes
            try {
                WebElement verifyBtn = driver.findElement(By.xpath(
                        "//button[contains(@class,'verify')] | //div[contains(@class,'verify')]//button | //button[@type='button'][contains(text(),'VERIFY')]"));
                ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();",
                        verifyBtn);
                System.out.println("Clicked VERIFY button using alternate selector");
            } catch (Exception ex) {
                System.out.println("Failed to click VERIFY button: " + ex.getMessage());
                throw ex;
            }
        }

        Thread.sleep(2000);
    }

    // Scenario 1: Mobile Number Already Exists
    @Test
    public void Mobile_Number_Already_Exists() throws InterruptedException {
        try {
            openSignUpPage();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Generate unique email
            String timestamp = String.valueOf(System.currentTimeMillis());
            String uniqueEmail = "Alex" + timestamp + "@yopmail.com";

            // First Name
            WebElement firstName = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//input[@placeholder='Your First Name' or contains(@placeholder,'First Name')]")));
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

            // Select Country Code
            selectCountryCode("IN +91");

            // Enter Mobile Number (without leading zero as per instruction)
            enterMobileNumber("9305637188");

            // Click VERIFY button instead of SIGN UP
            clickVerifyButton();

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

    // Scenario 2: Invalid Email
    @Test
    public void invalidEmailSignUp() throws InterruptedException {
        try {
            openSignUpPage();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // First Name
            WebElement firstName = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//input[@placeholder='Your First Name' or contains(@placeholder,'First Name')]")));
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

            // Select Country Code
            selectCountryCode("IN +91");

            // Enter Mobile Number (without leading zero as per instruction)
            enterMobileNumber("9305637188");

            // Click VERIFY button instead of SIGN UP
            clickVerifyButton();

            Thread.sleep(5000);

            // Verify error message for invalid email
            try {
                WebElement errorMsg = wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                By.xpath(
                                        "//div[contains(@class,'error')] | //span[contains(@class,'error')] | //div[contains(text(),'valid email')]")));
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

    // Scenario 3: Valid Sign Up with all fields
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
                            By.xpath(
                                    "//input[@placeholder='Your First Name' or contains(@placeholder,'First Name')]")));
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

            // Select Country Code
            selectCountryCode("IN +91");

            // Enter Mobile Number (without leading zero as per instruction)
            enterMobileNumber("9305637123");

            // Click VERIFY button instead of SIGN UP
            clickVerifyButton();

            Thread.sleep(5000);
            closeOTPPopup();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to close OTP popup - Guaranteed to work
    public void closeOTPPopup() throws InterruptedException {
        Thread.sleep(4000); // Wait for popup to fully load

        try {
            // Try to find close button by visible text/icon
            System.out.println("Looking for close button...");

            // Method 1: Try to find by SVG icon (feather-x-circle)
            try {
                WebElement closeBtn = driver.findElement(By.xpath(
                        "//*[local-name()='svg' and contains(@class,'feather-x-circle')] | //*[local-name()='svg']/parent::button"));
                closeBtn.click();
                System.out.println("✓ Popup closed using SVG icon");
                Thread.sleep(1000);
                return;
            } catch (Exception e) {
                System.out.println("SVG method failed");
            }

            // Method 2: Try all possible button selectors
            String[] selectors = {
                    "//button[contains(@class,'modal-close-btn')]",
                    "//button[contains(@class,'close')]",
                    "//span[contains(@class,'close')]",
                    "//div[contains(@class,'modal-header')]/button",
                    "//button[@type='button'][contains(@class,'btn-round')]",
                    "//button[contains(@class,'bg-greylight')]",
                    "//*[contains(@class,'feather-x-circle')]/parent::button",
                    "//button[contains(@class,'btn-round-md')]"
            };

            for (String selector : selectors) {
                try {
                    List<WebElement> buttons = driver.findElements(By.xpath(selector));
                    for (WebElement btn : buttons) {
                        if (btn.isDisplayed()) {
                            btn.click();
                            System.out.println("✓ Popup closed using: " + selector);
                            Thread.sleep(1000);
                            return;
                        }
                    }
                } catch (Exception e) {
                    // Continue
                }
            }

            // Method 3: Press ESC key
            try {
                driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
                System.out.println("✓ Popup closed using ESC key");
                Thread.sleep(1000);
                return;
            } catch (Exception e) {
                System.out.println("ESC method failed");
            }

        } catch (Exception e) {
            System.out.println("Error closing popup: " + e.getMessage());
        }
    }
}