package com.example.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PikabuTests {
    private WebDriver driver;

    private static final By MENU_LOGIN_BUTTON = By.cssSelector("div[class='header-right-menu'] button");
    private static final By AUTH_HEADER = By.xpath("//div[@class='auth-modal']//div[text()='Войти']");
    private static final By LOGIN_INPUT = By.cssSelector("div[class='auth-modal'] input[name='username']");
    private static final By PASSWORD_INPUT = By.cssSelector("div[class='auth-modal'] input[name='password']");
    private static final By SUBMIT_BUTTON = By.cssSelector("div[class='auth-modal'] button[type='submit']");
    private static final By ERROR_MESSAGE = By.xpath("//div[@class='popup__content']//span[@class='auth__error auth__error_top']");

    @BeforeEach
    public void setUp()
    {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\ababik\\Downloads\\chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();

        driver.get("https://pikabu.ru");
    }

    @AfterEach
    public void tearDown()
    {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void pikabuLoginTest() throws InterruptedException {
        validatePageTitle();
        clickButton(MENU_LOGIN_BUTTON);
        verifyAuthModalElements();
        login("nastya@yandex.ru", "AB@test@3661");
        sleep(2000);
        validateErrorMessage(); //Проверка падает т к сообщение об ошибке не видно, его перекрывает капча
    }

    private void validatePageTitle() {
        assertEquals("Горячее – самые интересные и обсуждаемые посты | Пикабу", driver.getTitle());
    }

    private void clickButton(By locator) {
        driver.findElement(locator).click();
    }

    private void verifyAuthModalElements() {
        assertTrue(driver.findElement(AUTH_HEADER).isDisplayed());
        assertTrue(driver.findElement(LOGIN_INPUT).isDisplayed());
        assertTrue(driver.findElement(PASSWORD_INPUT).isDisplayed());
        assertTrue(driver.findElement(SUBMIT_BUTTON).isDisplayed());
    }

    private void login(String username, String password) throws InterruptedException {
        driver.findElement(LOGIN_INPUT).sendKeys(username);
        sleep(4000);
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
        sleep(1000);
        clickButton(SUBMIT_BUTTON);
    }

    private void validateErrorMessage() {
        WebElement errorMessage = driver.findElement(ERROR_MESSAGE);
        if (!errorMessage.isDisplayed()) {
            throw new AssertionError("Элемент найден, но не виден на странице");
        }
        assertEquals("Ошибка. Вы ввели неверные данные авторизации", errorMessage.getText());
    }

}
