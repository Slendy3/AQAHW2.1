package ru.netology.card;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.By.className;

public class CardNegativeTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldBeFailedInvalidNameTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Schubert Franz");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeFailedEmptyNameTest() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeFailedEmptyPhoneTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Римский-Корсаков Николай");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeFailedMinPhoneTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Римский-Корсаков Николай");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7999");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeFailedMaxPhoneTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Римский-Корсаков Николай");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79999999999999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText();
        assertEquals(expected, actual);
    }

    @Test
    void shouldBeFailedUncheckedCheckboxTest() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79260001155");
        driver.findElement(By.cssSelector("button")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
}
