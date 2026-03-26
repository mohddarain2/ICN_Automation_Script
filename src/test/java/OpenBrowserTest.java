import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

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
                By.xpath("//div[contains(@class,'logout-user')]//div[contains(@class,'hidden-lg')]//a")
        ).click();

        Thread.sleep(2000);
    }
    // --------------------------------------Login Script
    // ---------------------------------------------------------------
     //Scenario 1: Empty Email | Please enter email and password
     @Test
     public void emptyEmail() throws InterruptedException {

     openLoginPage();

     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

     WebElement passwordInput = wait.until(
     ExpectedConditions.visibilityOfElementLocated(
     By.xpath("//div[contains(@class,'card-body')]//input[@type='password']")
     )
     );

     passwordInput.clear();
     passwordInput.sendKeys("123456");

         // Try multiple strategies for login button
         WebElement loginButton = null;

         try {
             // Strategy 1: By text
             loginButton = wait.until(
                     ExpectedConditions.elementToBeClickable(
                             By.xpath("//button[normalize-space()='LOGIN']")
                     )
             );
         } catch (Exception e) {
             try {
                 // Strategy 2: By type submit
                 loginButton = wait.until(
                         ExpectedConditions.elementToBeClickable(
                                 By.xpath("//button[@type='submit']")
                         )
                 );
             } catch (Exception e2) {
                 // Strategy 3: By class
                 loginButton = wait.until(
                         ExpectedConditions.elementToBeClickable(
                                 By.cssSelector("button.login-btn")
                         )
                 );
             }
         }

         loginButton.click();
         System.out.println("Login button clicked successfully");

         Thread.sleep(2000);
         driver.quit();
     }


     // Scenario 2: Empty Password
     @Test
     public void emptyPassword() throws InterruptedException {

     openLoginPage();

     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

     WebElement emailInput = wait.until(
     ExpectedConditions.visibilityOfElementLocated(
     By.xpath("//div[contains(@class,'card-body')]//input[@type='text']")
     )
     );

     emailInput.clear();
     emailInput.sendKeys("A00@yopmail.com");

         // Try multiple strategies for login button
         WebElement loginButton = null;

         try {
             // Strategy 1: By text
             loginButton = wait.until(
                     ExpectedConditions.elementToBeClickable(
                             By.xpath("//button[normalize-space()='LOGIN']")
                     )
             );
         } catch (Exception e) {
             try {
                 // Strategy 2: By type submit
                 loginButton = wait.until(
                         ExpectedConditions.elementToBeClickable(
                                 By.xpath("//button[@type='submit']")
                         )
                 );
             } catch (Exception e2) {
                 // Strategy 3: By class
                 loginButton = wait.until(
                         ExpectedConditions.elementToBeClickable(
                                 By.cssSelector("button.login-btn")
                         )
                 );
             }
         }

         loginButton.click();
         System.out.println("Login button clicked successfully");

         Thread.sleep(2000);
         driver.quit();
     }


//     // Scenario 3: Invalid Password | Invalid credentials
     @Test
     public void invalidPassword() throws InterruptedException {

     openLoginPage();

     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

     WebElement emailInput = wait.until(
     ExpectedConditions.visibilityOfElementLocated(
     By.xpath("//div[contains(@class,'card-body')]//input[@type='text']")
     )
     );

     emailInput.clear();
     emailInput.sendKeys("A00@yopmail.com");

     WebElement passwordInput = wait.until(
     ExpectedConditions.visibilityOfElementLocated(
     By.xpath("//div[contains(@class,'card-body')]//input[@type='password']")
     )
     );

     passwordInput.clear();
     passwordInput.sendKeys("wrongpass");

         // Try multiple strategies for login button
         WebElement loginButton = null;

         try {
             // Strategy 1: By text
             loginButton = wait.until(
                     ExpectedConditions.elementToBeClickable(
                             By.xpath("//button[normalize-space()='LOGIN']")
                     )
             );
         } catch (Exception e) {
             try {
                 // Strategy 2: By type submit
                 loginButton = wait.until(
                         ExpectedConditions.elementToBeClickable(
                                 By.xpath("//button[@type='submit']")
                         )
                 );
             } catch (Exception e2) {
                 // Strategy 3: By class
                 loginButton = wait.until(
                         ExpectedConditions.elementToBeClickable(
                                 By.cssSelector("button.login-btn")
                         )
                 );
             }
         }

         loginButton.click();
         System.out.println("Login button clicked successfully");

         Thread.sleep(2000);
         driver.quit();
     }


//     // Scenario 4: Valid Login

     @Test
     public void validLogin() throws InterruptedException {

     openLoginPage();

     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

     WebElement emailInput = wait.until(
     ExpectedConditions.visibilityOfElementLocated(
     By.xpath("//div[contains(@class,'card-body')]//input[@type='text']")
     )
     );

     emailInput.clear();
     emailInput.sendKeys("A00@yopmail.com");

     WebElement passwordInput = wait.until(
     ExpectedConditions.visibilityOfElementLocated(
     By.xpath("//div[contains(@class,'card-body')]//input[@type='password']")
     )
     );

     passwordInput.clear();
     passwordInput.sendKeys("123456");

         // Try multiple strategies for login button
         WebElement loginButton = null;

         try {
             // Strategy 1: By text
             loginButton = wait.until(
                     ExpectedConditions.elementToBeClickable(
                             By.xpath("//button[normalize-space()='LOGIN']")
                     )
             );
         } catch (Exception e) {
             try {
                 // Strategy 2: By type submit
                 loginButton = wait.until(
                         ExpectedConditions.elementToBeClickable(
                                 By.xpath("//button[@type='submit']")
                         )
                 );
             } catch (Exception e2) {
                 // Strategy 3: By class
                 loginButton = wait.until(
                         ExpectedConditions.elementToBeClickable(
                                 By.cssSelector("button.login-btn")
                         )
                 );
             }
         }

         loginButton.click();
         System.out.println("Login button clicked successfully");

         Thread.sleep(2000);
         driver.quit();
     }
}