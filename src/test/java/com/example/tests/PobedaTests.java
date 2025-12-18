package com.example.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PobedaTests {
    private WebDriver  driver;
    private WebDriverWait wait;
    private WebDriverWait longWait;
    private WebDriverWait shortWait;

    private static final By KALININGRAD_BANNER = By.xpath("//*[contains(text(), 'Полетели в Калининград!')]");
    private static final By SELECTED_LANG_RU = By.xpath("//button[text()='РУС']");
    private static final By ITEM_LANG_ENG = By.xpath("//div[text()='English']");
    private static final By TICKET_SEARCH_BUTTON = By.xpath("//span[text()='Ticket search'][2]");
    private static final By CHECK_IN_BUTTON = By.xpath("//span[text()='Online check-in'][2]");
    private static final By MANAGE_BOOKING_BUTTON = By.xpath("//span[text()='Manage my booking'][2]");


    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36");
        options.addArguments("--lang=ru-RU");
        options.addArguments("--window-size=1920,1080");

        options.addArguments("--incognito");
        driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        longWait = new WebDriverWait(driver, Duration.ofSeconds(180));
        shortWait = new WebDriverWait(driver, Duration.ofSeconds(20));

    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void pobedaTest() {
        /*
        driver.get("https://google.com");

        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.name("q")));
        searchBox.sendKeys("Сайт компании Победа");
        searchBox.sendKeys(Keys.ENTER);

        //Дождаться результатов и кликнуть на первую ссылку
        WebElement firstResult = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href='https://www.flypobeda.ru']")
                )
        );
        assertEquals("https://www.flypobeda.ru", firstResult.getText());


        firstResult.click();

         */
        driver.get("https://www.flypobeda.ru");

        //Дождаться загрузки страницы Победы и проверки текста на картинке
        verifyBanner(KALININGRAD_BANNER, "Полетели в Калининград!", 180);

        //Сменить язык на английский
        switchLanguage(SELECTED_LANG_RU, ITEM_LANG_ENG);

        //Проверить смену языка надписей на сайте
        shortWait.until(
                ExpectedConditions.textToBePresentInElementLocated(TICKET_SEARCH_BUTTON, "Ticket search"));

        shortWait.until(
                ExpectedConditions.textToBePresentInElementLocated(CHECK_IN_BUTTON, "Online check-in"));

        shortWait.until(
                ExpectedConditions.textToBePresentInElementLocated(MANAGE_BOOKING_BUTTON, "Manage my booking"));

    }

    private void switchLanguage(By currentLanguage, By targetLanguage) {
        WebElement langSwitcher = shortWait.until(
                ExpectedConditions.elementToBeClickable(currentLanguage));
        langSwitcher.click();

        WebElement targetOption = shortWait.until(
                ExpectedConditions.elementToBeClickable(targetLanguage));
        targetOption.click();

    }

    private void verifyBanner(By locator, String expectedText, int seconds) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(seconds));

        WebElement banner = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator));

        // Проверка точного текста
        assertEquals(expectedText,  banner.getText().trim(),
                "Текст не совпадает");
    }

}
