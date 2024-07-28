package com.ejemplo.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenCartTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup(); // Actualiza automáticamente el chromedriver
        driver = new ChromeDriver();
    }

    @Test
    public void testBuyItems() {
        // Tu código de prueba aquí
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

    @Test
    public void testBuyItems() {
        driver.get("https://opencart.abstracta.us/index.php");

        // Paso 1: Buscar el primer ítem
        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.sendKeys("iPhone");
        searchBox.submit();

        // Paso 2: Seleccionar el primer ítem de la búsqueda
        WebElement firstItem = driver.findElement(By.cssSelector(".product-thumb .caption a"));
        firstItem.click();

        // Paso 3: Añadir el ítem al carrito
        WebElement addToCartButton = driver.findElement(By.id("button-cart"));
        addToCartButton.click();

        // Volver a la página principal
        driver.get("https://opencart.abstracta.us/index.php");

        // Paso 4: Buscar el segundo ítem
        searchBox = driver.findElement(By.name("search"));
        searchBox.clear();
        searchBox.sendKeys("MacBook");
        searchBox.submit();

        // Paso 5: Seleccionar el primer ítem de la búsqueda
        WebElement secondItem = driver.findElement(By.cssSelector(".product-thumb .caption a"));
        secondItem.click();

        // Paso 6: Añadir el ítem al carrito
        addToCartButton = driver.findElement(By.id("button-cart"));
        addToCartButton.click();

        // Paso 7: Ir al carrito y proceder a la compra
        WebElement cart = driver.findElement(By.id("cart"));
        cart.click();

        WebElement checkoutButton = driver.findElement(By.linkText("Checkout"));
        checkoutButton.click();

        // Verifica que la página de checkout se haya cargado correctamente
        WebElement checkoutHeader = driver.findElement(By.cssSelector("h1"));
        assertTrue(checkoutHeader.getText().contains("Checkout"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
