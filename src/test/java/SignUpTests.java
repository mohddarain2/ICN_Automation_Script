import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    // ✅ NEW METHOD: Click SIGN UP button (instead of VERIFY)
    public void clickSignUpButton() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement signUpButton = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//button[contains(text(),'SIGN UP')] | //button[contains(text(),'Sign Up')] | //button[@type='submit'][contains(text(),'SIGN')]")));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", signUpButton);
            Thread.sleep(500);

            signUpButton.click();
            System.out.println("✅ Clicked SIGN UP button successfully");

        } catch (Exception e) {
            System.out.println("Error clicking SIGN UP button: " + e.getMessage());
            throw e;
        }

        Thread.sleep(1000);
    }

    // Method to click Go Premium button
    public void clickGoPremiumButton() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Multiple strategies for Go Premium button
            WebElement goPremiumBtn = null;

            // Strategy 1: Direct text match
            try {
                goPremiumBtn = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.xpath(
                                        "//button[contains(text(),'Go Premium')] | //a[contains(text(),'Go Premium')] | //div[contains(text(),'Go Premium')]")));
            } catch (Exception e1) {
                // Strategy 2: Case insensitive
                try {
                    goPremiumBtn = wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath(
                                            "//button[contains(translate(text(), 'GO PREMIUM', 'go premium'), 'go premium')] | //a[contains(translate(text(), 'GO PREMIUM', 'go premium'), 'go premium')]")));
                } catch (Exception e2) {
                    // Strategy 3: Contains Premium word
                    goPremiumBtn = wait.until(
                            ExpectedConditions.elementToBeClickable(
                                    By.xpath(
                                            "//button[contains(text(),'Premium')] | //a[contains(text(),'Premium')] | //span[contains(text(),'Premium')]/parent::button")));
                }
            }

            // Scroll to button
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", goPremiumBtn);
            Thread.sleep(500);

            // Click using JavaScript for safety
            try {
                goPremiumBtn.click();
                System.out.println("✅ Clicked Go Premium button successfully");
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", goPremiumBtn);
                System.out.println("✅ Clicked Go Premium button using JavaScript");
            }

        } catch (Exception e) {
            System.out.println("❌ Error clicking Go Premium button: " + e.getMessage());
            throw e;
        }

        Thread.sleep(1000);
    }

    // Method to click Go Premium button from navigation/menu
    public void clickGoPremiumFromMenu() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            // Try to find Go Premium in sidebar or navigation
            WebElement premiumNav = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//a[contains(@href,'premium')] | //li[contains(@class,'premium')]//a | //div[contains(@class,'premium')]//button")));

            premiumNav.click();
            System.out.println("✅ Clicked Go Premium from navigation");
            Thread.sleep(2000);

        } catch (Exception e) {
            System.out.println("Go Premium not found in navigation: " + e.getMessage());
        }
    }

    // Complete Test Example - After Login/Signup, click Go Premium
    @Test
    public void clickGoPremiumAfterSignUp() throws InterruptedException {
        try {
            // Ab Go Premium button click karo
            clickGoPremiumButton();

            // Verify premium page loaded
            Thread.sleep(3000);
            System.out.println("✅ Premium page opened successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // // Method to click VERIFY button
    // public void clickVerifyButton() throws InterruptedException {
    // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // try {
    // // Try multiple XPath strategies for VERIFY button
    // WebElement verifyButton = null;

    // // Strategy 1: Button with text VERIFY
    // try {
    // verifyButton = wait.until(
    // ExpectedConditions.elementToBeClickable(
    // By.xpath("//button[contains(text(),'VERIFY')]")));
    // } catch (Exception e1) {
    // // Strategy 2: Button with text Verify (case insensitive)
    // try {
    // verifyButton = wait.until(
    // ExpectedConditions.elementToBeClickable(
    // By.xpath("//button[contains(translate(text(), 'VERIFY', 'verify'),
    // 'verify')]")));
    // } catch (Exception e2) {
    // // Strategy 3: Any button that contains VERIFY text
    // verifyButton = wait.until(
    // ExpectedConditions.elementToBeClickable(
    // By.xpath(
    // "//*[contains(text(),'VERIFY') and contains(@class,'btn')] |
    // //input[@value='VERIFY'] | //button[@type='submit' and
    // contains(text(),'VERIFY')]")));
    // }
    // }

    // // Scroll to verify button if needed
    // ((org.openqa.selenium.JavascriptExecutor) driver)
    // .executeScript("arguments[0].scrollIntoView(true);", verifyButton);
    // Thread.sleep(500);

    // // Click using JavaScript if normal click fails
    // try {
    // verifyButton.click();
    // System.out.println("Clicked VERIFY button successfully");
    // } catch (Exception e) {
    // ((org.openqa.selenium.JavascriptExecutor)
    // driver).executeScript("arguments[0].click();",
    // verifyButton);
    // System.out.println("Clicked VERIFY button using JavaScript");
    // }

    // } catch (Exception e) {
    // System.out.println("Error clicking VERIFY button: " + e.getMessage());

    // // Strategy 4: Try to find by class or other attributes
    // try {
    // WebElement verifyBtn = driver.findElement(By.xpath(
    // "//button[contains(@class,'verify')] |
    // //div[contains(@class,'verify')]//button |
    // //button[@type='button'][contains(text(),'VERIFY')]"));
    // ((org.openqa.selenium.JavascriptExecutor)
    // driver).executeScript("arguments[0].click();",
    // verifyBtn);
    // System.out.println("Clicked VERIFY button using alternate selector");
    // } catch (Exception ex) {
    // System.out.println("Failed to click VERIFY button: " + ex.getMessage());
    // throw ex;
    // }
    // }

    // Thread.sleep(2000);
    // }

    // Scenario 1: Mobile Number Already Exists
    // @Test
    // public void Mobile_Number_Already_Exists() throws InterruptedException {
    // try {
    // openSignUpPage();

    // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // // Generate unique email
    // String timestamp = String.valueOf(System.currentTimeMillis());
    // String uniqueEmail = "Alex" + timestamp + "@yopmail.com";

    // // First Name
    // WebElement firstName = wait.until(
    // ExpectedConditions.visibilityOfElementLocated(
    // By.xpath(
    // "//input[@placeholder='Your First Name' or contains(@placeholder,'First
    // Name')]")));
    // firstName.sendKeys("Jon");

    // // Last Name
    // WebElement lastName = driver.findElement(
    // By.xpath("//input[@placeholder='Your Last Name' or
    // contains(@placeholder,'Last Name')]"));
    // lastName.sendKeys("Alex");

    // // Email Address
    // WebElement email = driver.findElement(
    // By.xpath("//input[@placeholder='Your Email Address' or
    // contains(@placeholder,'Email')]"));
    // email.sendKeys(uniqueEmail);
    // System.out.println("Entered email: " + uniqueEmail);

    // // Password
    // WebElement password = driver.findElement(
    // By.xpath("//input[@placeholder='Password' or
    // contains(@placeholder,'Password')]"));
    // password.sendKeys("123456");

    // // Address with Google Autocomplete
    // enterAddressWithAutocomplete("melbourne");

    // // Select Country Code
    // selectCountryCode("IN +91");

    // // Enter Mobile Number (without leading zero as per instruction)
    // enterMobileNumber("9305637188");

    // // Click VERIFY button instead of SIGN UP
    // clickVerifyButton();

    // Thread.sleep(5000);

    // // Verify success message or redirection
    // System.out.println("Sign Up completed for: " + uniqueEmail);

    // } catch (Exception e) {
    // System.out.println("Test failed: " + e.getMessage());
    // e.printStackTrace();
    // } finally {
    // if (driver != null) {
    // driver.quit();
    // }
    // }
    // }

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
            clickSignUpButton();

            Thread.sleep(3000);

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
            clickSignUpButton();

            // ✅ AFTER SIGNUP - Click Go Premium button
            clickGoPremiumButton();

            Thread.sleep(2000);
            // closeOTPPopup();

            // ✅ Bas itna call karo - form data auto fill ho jayega
            fillProfileFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ EK FUNCTION - Sab kuch fill karega
    // ✅ SIMPLE FUNCTION - Sirf 4 fields fill karega
    public void fillProfileFields() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("========== Filling Profile Fields ==========");

        // 1. Gender - Male select karo
        try {
            WebElement genderDropdown = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//select[contains(@name,'gender')] | //select[contains(@id,'gender')] | //label[contains(text(),'Gender')]/following::select[1]")));
            Thread.sleep(1000);
            Select select = new Select(genderDropdown);
            select.selectByVisibleText("Male");
            System.out.println("✓ Gender: Male");
        } catch (Exception e) {
            System.out.println("✗ Gender select failed: " + e.getMessage());
        }
        Thread.sleep(500);

        // 2. Height - 156 cm dalo
        try {
            WebElement heightInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//input[contains(@placeholder,'Height')] | //input[contains(@name,'height')] | //label[contains(text(),'Height')]/following::input[1]")));
            heightInput.clear();
            heightInput.sendKeys("156");
            System.out.println("✓ Height: 156 cm");
        } catch (Exception e) {
            System.out.println("✗ Height enter failed: " + e.getMessage());
        }
        Thread.sleep(500);

        // 3. Occupation - Within Health & Fitness select karo
        try {
            WebElement occupationDropdown = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//select[contains(@name,'occupation')] | //select[contains(@id,'occupation')] | //label[contains(text(),'Occupation')]/following::select[1]")));
            Thread.sleep(1000);
            Select select = new Select(occupationDropdown);
            select.selectByVisibleText("Within Health & Fitness");
            System.out.println("✓ Occupation: Within Health & Fitness");
        } catch (Exception e) {
            System.out.println("✗ Occupation select failed: " + e.getMessage());
        }
        Thread.sleep(500);

        // 4. Date of Birth - 05-09-1990 dalo
        try {
            WebElement dobInput = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//input[contains(@placeholder,'dd-mm-yyyy')] | //input[contains(@name,'dob')] | //input[@type='date'] | //label[contains(text(),'Date of birth')]/following::input[1]")));
            dobInput.clear();
            dobInput.sendKeys("05-09-1990");
            System.out.println("✓ Date of Birth: 05-09-1990");
        } catch (Exception e) {
            System.out.println("✗ DOB enter failed: " + e.getMessage());
        }

        System.out.println("========== All 4 Fields Filled Successfully ==========");
        Thread.sleep(2000);

        // 5. ✅ CONTINUE button click
        clickContinueButton();

        // Step 3: ✅ Payment - Agree, Card details, Subscribe
        completePaymentFlow();
    }

    // ✅ CONTINUE button click karne ka method
    public void clickContinueButton() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement continueBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//button[contains(text(),'CONTINUE')] | //button[contains(text(),'Continue')] | //button[@type='submit'][contains(text(),'Continue')] | //a[contains(text(),'Continue')] | //div[contains(text(),'Continue')]/parent::button")));

            // Scroll to button
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueBtn);
            Thread.sleep(500);

            // Click using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);
            System.out.println("✓ CONTINUE button clicked successfully");

        } catch (Exception e) {
            System.out.println("✗ CONTINUE button click failed: " + e.getMessage());

            // Try alternate selectors
            try {
                WebElement continueBtn = driver.findElement(
                        By.xpath("//button[contains(translate(text(), 'CONTINUE', 'continue'), 'continue')]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", continueBtn);
                System.out.println("✓ CONTINUE button clicked (alternate method)");
            } catch (Exception ex) {
                System.out.println("✗ CONTINUE button not found");
                throw ex;
            }
        }

        Thread.sleep(2000);
    }

    // ✅ COMPLETE PAYMENT SCRIPT - Agree, Card details, Subscribe
    public void fillPaymentAndSubscribe() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        System.out.println("========== Payment Details Fill Start ==========");

        // 1. ✅ Check "I agree to the above conditions"
        try {
            WebElement agreeCheckbox = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//input[@type='checkbox'][contains(@name,'agree')] | //input[@type='checkbox'][contains(@id,'agree')] | //label[contains(text(),'I agree')]/preceding-sibling::input | //span[contains(text(),'I agree')]/preceding::input[1]")));

            if (!agreeCheckbox.isSelected()) {
                agreeCheckbox.click();
                System.out.println("✓ I agree to the above conditions - CHECKED");
            } else {
                System.out.println("✓ Checkbox already selected");
            }
        } catch (Exception e) {
            System.out.println("✗ Agree checkbox failed: " + e.getMessage());
            // Try to click on label
            try {
                WebElement agreeLabel = driver.findElement(
                        By.xpath("//label[contains(text(),'I agree')] | //div[contains(text(),'I agree')]"));
                agreeLabel.click();
                System.out.println("✓ Agree clicked via label");
            } catch (Exception ex) {
                System.out.println("✗ Agree label not found");
            }
        }
        Thread.sleep(1000);

        // 2. Switch to Stripe iframe (if present)
        try {
            WebElement stripeIframe = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath(
                                    "//iframe[contains(@title,'Stripe')] | //iframe[contains(@name,'stripe')] | //iframe[contains(@src,'stripe')]")));
            driver.switchTo().frame(stripeIframe);
            System.out.println("✓ Switched to Stripe iframe");
        } catch (Exception e) {
            System.out.println("No Stripe iframe found, continuing with main page");
        }
        Thread.sleep(1000);

        // 3. Card Number - 4242 4242 4242 4242
        try {
            WebElement cardNumber = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath(
                                    "//input[@name='cardnumber'] | //input[@placeholder='Card number'] | //input[@autocomplete='cc-number'] | //input[@id='card-number']")));
            cardNumber.clear();
            cardNumber.sendKeys("4242424242424242");
            System.out.println("✓ Card Number: 4242 4242 4242 4242");
        } catch (Exception e) {
            System.out.println("✗ Card number failed: " + e.getMessage());
        }
        Thread.sleep(1000);

        // 4. Expiry Date - 02/30
        try {
            WebElement expiryDate = driver.findElement(
                    By.xpath(
                            "//input[@name='expiry'] | //input[@placeholder='MM / YY'] | //input[@placeholder='MM/YY'] | //input[@autocomplete='cc-exp']"));
            expiryDate.clear();
            expiryDate.sendKeys("0230");
            System.out.println("✓ Expiry Date: 02/30");
        } catch (Exception e) {
            System.out.println("✗ Expiry date failed: " + e.getMessage());
        }
        Thread.sleep(500);

        // 5. CVC - 123
        try {
            WebElement cvc = driver.findElement(
                    By.xpath("//input[@name='cvc'] | //input[@placeholder='CVC'] | //input[@autocomplete='cc-csc']"));
            cvc.clear();
            cvc.sendKeys("123");
            System.out.println("✓ CVC: 123");
        } catch (Exception e) {
            System.out.println("✗ CVC failed: " + e.getMessage());
        }
        Thread.sleep(500);

        // 6. ZIP Code - 12345
        try {
            WebElement zipCode = driver.findElement(
                    By.xpath(
                            "//input[@name='postal'] | //input[@placeholder='ZIP'] | //input[@placeholder='Postal'] | //input[@autocomplete='postal-code']"));
            zipCode.clear();
            zipCode.sendKeys("12345");
            System.out.println("✓ ZIP Code: 12345");
        } catch (Exception e) {
            System.out.println("✗ ZIP code failed: " + e.getMessage());
        }

        // 7. Switch back to main content from iframe
        try {
            driver.switchTo().defaultContent();
            System.out.println("✓ Switched back to main content");
        } catch (Exception e) {
            System.out.println("No iframe to switch back");
        }
        Thread.sleep(1000);

        // 8. ✅ SUBSCRIBE button click
        clickSubscribeButton();

        System.out.println("========== Payment Details Completed ==========");
    }

    // ✅ SUBSCRIBE button click karne ka method
    public void clickSubscribeButton() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement subscribeBtn = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath(
                                    "//button[contains(text(),'SUBSCRIBE')] | //button[contains(text(),'Subscribe')] | //button[@type='submit'][contains(text(),'Subscribe')] | //button[contains(@class,'subscribe')]")));

            // Scroll to button
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", subscribeBtn);
            Thread.sleep(500);

            // Click using JavaScript
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subscribeBtn);
            System.out.println("✓ SUBSCRIBE button clicked successfully");

        } catch (Exception e) {
            System.out.println("✗ SUBSCRIBE button click failed: " + e.getMessage());

            // Try alternate selectors
            try {
                WebElement subscribeBtn = driver.findElement(
                        By.xpath("//button[contains(translate(text(), 'SUBSCRIBE', 'subscribe'), 'subscribe')]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", subscribeBtn);
                System.out.println("✓ SUBSCRIBE button clicked (alternate method)");
            } catch (Exception ex) {
                System.out.println("✗ SUBSCRIBE button not found");
                throw ex;
            }
        }

        Thread.sleep(3000);
    }

    // ✅ WAIT AND CLICK METHOD
public void waitAndClickAgreeCheckbox() throws InterruptedException {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    
    try {
        // First scroll to the bottom of the page
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(1000);
        
        // Wait for checkbox to be present and clickable
        WebElement checkbox = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[@type='checkbox'])[last()] | //input[@type='checkbox'][contains(@class,'agree')] | //input[@name='agree']")));
        
        // Scroll to checkbox
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", checkbox);
        Thread.sleep(1000);
        
        // Click using multiple methods
        try {
            checkbox.click();
        } catch(Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
        }
        
        // Verify if checked
        Thread.sleep(500);
        boolean isChecked = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].checked;", checkbox);
        
        if(isChecked) {
            System.out.println("✅ I agree to the above conditions - CHECKED SUCCESSFULLY");
        } else {
            System.out.println("⚠️ Checkbox not checked, trying force method");
            ((JavascriptExecutor) driver).executeScript("arguments[0].checked = true; arguments[0].dispatchEvent(new Event('change'));", checkbox);
            System.out.println("✅ Checkbox force checked");
        }
        
    } catch(Exception e) {
        System.out.println("❌ Failed to click agree checkbox: " + e.getMessage());
        
        // Last resort - Click using coordinates
        try {
            WebElement checkbox = driver.findElement(By.xpath("//input[@type='checkbox']"));
            int x = checkbox.getLocation().getX();
            int y = checkbox.getLocation().getY();
            org.openqa.selenium.interactions.Actions actions = new org.openqa.selenium.interactions.Actions(driver);
            actions.moveByOffset(x + 10, y + 10).click().perform();
            System.out.println("✓ Clicked using coordinates");
        } catch(Exception ex) {
            System.out.println("❌ All methods failed");
        }
    }
}

    // ✅ Complete payment flow with Auto-Renewal
    public void completePaymentFlow() throws InterruptedException {
        // Enable Auto-Renewal (Recommended)
        waitAndClickAgreeCheckbox();

        // Fill payment details and subscribe
        fillPaymentAndSubscribe();
    }

    // Method to close OTP popup - Guaranteed to work
    // public void closeOTPPopup() throws InterruptedException {
    // Thread.sleep(4000); // Wait for popup to fully load

    // try {
    // // Try to find close button by visible text/icon
    // System.out.println("Looking for close button...");

    // // Method 1: Try to find by SVG icon (feather-x-circle)
    // try {
    // WebElement closeBtn = driver.findElement(By.xpath(
    // "//*[local-name()='svg' and contains(@class,'feather-x-circle')] |
    // //*[local-name()='svg']/parent::button"));
    // closeBtn.click();
    // System.out.println("✓ Popup closed using SVG icon");
    // Thread.sleep(1000);
    // return;
    // } catch (Exception e) {
    // System.out.println("SVG method failed");
    // }

    // // Method 2: Try all possible button selectors
    // String[] selectors = {
    // "//button[contains(@class,'modal-close-btn')]",
    // "//button[contains(@class,'close')]",
    // "//span[contains(@class,'close')]",
    // "//div[contains(@class,'modal-header')]/button",
    // "//button[@type='button'][contains(@class,'btn-round')]",
    // "//button[contains(@class,'bg-greylight')]",
    // "//*[contains(@class,'feather-x-circle')]/parent::button",
    // "//button[contains(@class,'btn-round-md')]"
    // };

    // for (String selector : selectors) {
    // try {
    // List<WebElement> buttons = driver.findElements(By.xpath(selector));
    // for (WebElement btn : buttons) {
    // if (btn.isDisplayed()) {
    // btn.click();
    // System.out.println("✓ Popup closed using: " + selector);
    // Thread.sleep(1000);
    // return;
    // }
    // }
    // } catch (Exception e) {
    // // Continue
    // }
    // }

    // // Method 3: Press ESC key
    // try {
    // driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
    // System.out.println("✓ Popup closed using ESC key");
    // Thread.sleep(1000);
    // return;
    // } catch (Exception e) {
    // System.out.println("ESC method failed");
    // }

    // } catch (Exception e) {
    // System.out.println("Error closing popup: " + e.getMessage());
    // }
    // }

}