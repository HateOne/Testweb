package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CardOrderFormTest {
    private WebDriver driver;


    @BeforeAll
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldSendCardOrderFormSuccessful() {
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("Храмов Никита");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("+79934656881");
        driver.findElement(By.cssSelector("[data-test-id ='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=order-success]"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }

    @Test
    public void shouldCardOrderFormNoName() {

        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("+79934656881");
        driver.findElement(By.cssSelector("[data-test-id ='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id ='name'].input_invalid .input__sub")).getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id ='name'].input_invalid .input__sub")).isDisplayed());
    }

    @Test
    public void shouldCardOrderFormIncorrectName() {
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("Nik");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("+79934656881");
        driver.findElement(By.cssSelector("[data-test-id ='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.cssSelector("[data-test-id ='name'].input_invalid .input__sub")).getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id ='name'].input_invalid .input__sub")).isDisplayed());
    }
    @Test
    public void shouldCardOrderFormNoPhone() {
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("Храмов Никита");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("45");
        driver.findElement(By.cssSelector("[data-test-id ='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.cssSelector("[data-test-id ='phone'].input_invalid .input__sub")).getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id ='phone'].input_invalid .input__sub")).isDisplayed());
    }
    @Test
    public void shouldCardOrderFormEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("Храмов Никита");
        driver.findElement(By.cssSelector("[data-test-id ='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id ='phone'].input_invalid .input__sub")).getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id ='phone'].input_invalid .input__sub")).isDisplayed());
    }
    @Test
    public void shouldCardOrderFormEmptyCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("Храмов Никита");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("+79934656881");
        driver.findElement(By.cssSelector("button.button")).click();
        driver.findElement(By.cssSelector("[data-test-id ='agreement'].input_invalid"));
        assertTrue(driver.findElement(By.cssSelector("[data-test-id ='agreement'].input_invalid")).isDisplayed());
    }


}



