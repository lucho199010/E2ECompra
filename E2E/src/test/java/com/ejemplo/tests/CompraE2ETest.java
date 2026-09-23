package com.ejemplo.tests;

import com.ejemplo.tests.pages.DemoblazePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Prueba funcional End-to-End (E2E) del flujo de compra en https://www.demoblaze.com/
 *
 * Flujo cubierto (segun el enunciado del ejercicio):
 *   1. Agregar dos productos al carrito
 *   2. Visualizar el carrito
 *   3. Completar el formulario de compra
 *   4. Finalizar la compra
 */
public class CompraE2ETest {

    private WebDriver driver;
    private DemoblazePage demoblaze;

    // Productos que se agregaran al carrito
    private static final String PRODUCTO_1 = "Samsung galaxy s6";
    private static final String PRODUCTO_2 = "Nexus 6";

    @BeforeEach
    public void setUp() {
        // WebDriverManager descarga y configura el ChromeDriver adecuado automaticamente
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        // Permite ejecucion headless con: ./gradlew test -Dheadless=true
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        demoblaze = new DemoblazePage(driver);
    }

    @Test
    @DisplayName("Flujo E2E de compra: agregar 2 productos, ver carrito, completar formulario y finalizar")
    public void testFlujoCompraE2E() {
        // ----- Paso 1: Agregar dos productos al carrito -----
        demoblaze.addProductToCart(PRODUCTO_1);
        demoblaze.addProductToCart(PRODUCTO_2);

        // ----- Paso 2: Visualizar el carrito -----
        demoblaze.goToCart();

        // Espera activa: el carrito se llena via AJAX, esperamos hasta ver 2 filas
        boolean dosProductos = demoblaze.waitForCartItemCount(2);
        int itemCount = demoblaze.getCartItemCount();
        assertTrue(dosProductos && itemCount == 2,
                "El carrito debe contener exactamente 2 productos, pero contiene: " + itemCount);

        List<String> productos = demoblaze.getCartProductNames();
        assertTrue(productos.contains(PRODUCTO_1),
                "El carrito debe contener el producto: " + PRODUCTO_1);
        assertTrue(productos.contains(PRODUCTO_2),
                "El carrito debe contener el producto: " + PRODUCTO_2);

        // ----- Paso 3: Completar el formulario de compra -----
        demoblaze.clickPlaceOrder();
        demoblaze.fillPurchaseForm(
                "Luis Prado",     // Name
                "Peru",           // Country
                "Lima",           // City
                "4111111111111111", // Credit card
                "12",             // Month
                "2026"            // Year
        );

        // ----- Paso 4: Finalizar la compra -----
        demoblaze.clickPurchase();

        String confirmacion = demoblaze.getConfirmationText();
        assertTrue(confirmacion.contains("Thank you for your purchase!"),
                "Se esperaba el mensaje de confirmacion de compra, pero fue: " + confirmacion);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
