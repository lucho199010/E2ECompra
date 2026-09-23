package com.ejemplo.tests.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object del sitio https://www.demoblaze.com/
 *
 * Encapsula los selectores y las acciones del flujo de compra E2E:
 *  - Agregar productos al carrito
 *  - Visualizar el carrito
 *  - Completar el formulario de compra
 *  - Finalizar (Purchase) la compra
 */
public class DemoblazePage {

    private static final String BASE_URL = "https://www.demoblaze.com/";

    private final WebDriver driver;
    private final WebDriverWait wait;

    public DemoblazePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    /** Abre la pagina principal y espera a que se carguen los productos. */
    public void open() {
        driver.get(BASE_URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".card-title a")));
    }

    /**
     * Agrega un producto al carrito a partir de su nombre visible en el catalogo.
     * Maneja el alert de confirmacion ("Product added.") que muestra el sitio.
     *
     * @param productName nombre exacto del producto, por ejemplo "Samsung galaxy s6"
     */
    public void addProductToCart(String productName) {
        // Ir siempre al home para asegurar catalogo visible
        open();

        // Clic en el producto del catalogo
        WebElement product = wait.until(ExpectedConditions.elementToBeClickable(
                By.linkText(productName)));
        product.click();

        // Esperar a que cargue la pagina de detalle del producto
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".product-content h2, .name")));

        // Boton "Add to cart" en el detalle del producto (link con texto "Add to cart")
        WebElement addToCart = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.btn.btn-success.btn-lg")));
        addToCart.click();

        // El sitio muestra un alert JS de confirmacion ("Product added"); aceptarlo.
        acceptAlert();
    }

    /**
     * Espera y acepta el alert nativo de JavaScript que confirma el alta del producto.
     * Se reintenta brevemente porque el alert puede tardar en dispararse.
     */
    private void acceptAlert() {
        try {
            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
            // Pequena pausa para que se complete el registro AJAX del carrito
            sleep(800);
        } catch (NoAlertPresentException e) {
            // Si por algun motivo no aparecio, continuamos: se validara en el carrito
        }
    }

    /** Navega a la vista del carrito y espera a que se rendericen sus filas. */
    public void goToCart() {
        WebElement cartLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("cartur")));
        cartLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));
    }

    /**
     * Espera hasta que el carrito contenga la cantidad esperada de productos.
     *
     * @param expected numero de productos esperados
     * @return true si el carrito alcanzo la cantidad esperada dentro del timeout
     */
    public boolean waitForCartItemCount(int expected) {
        try {
            return wait.until(d ->
                    d.findElements(By.cssSelector("#tbodyid tr")).size() == expected);
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    /**
     * Devuelve la lista de nombres de productos presentes en el carrito.
     */
    public List<String> getCartProductNames() {
        List<WebElement> rows = driver.findElements(By.cssSelector("#tbodyid tr td:nth-child(2)"));
        return rows.stream().map(WebElement::getText).toList();
    }

    /** @return numero de filas (productos) en el carrito. */
    public int getCartItemCount() {
        return driver.findElements(By.cssSelector("#tbodyid tr")).size();
    }

    /** Abre el modal "Place Order" del carrito. */
    public void clickPlaceOrder() {
        WebElement placeOrder = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Place Order']")));
        placeOrder.click();
        // Esperar a que el modal sea visible y sus campos interactuables
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("orderModal")));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("name")));
    }

    /**
     * Completa el formulario de compra dentro del modal "Place Order".
     */
    public void fillPurchaseForm(String name, String country, String city,
                                 String card, String month, String year) {
        typeInto(By.id("name"), name);
        typeInto(By.id("country"), country);
        typeInto(By.id("city"), city);
        typeInto(By.id("card"), card);
        typeInto(By.id("month"), month);
        typeInto(By.id("year"), year);
    }

    /** Pulsa el boton "Purchase" para finalizar la compra. */
    public void clickPurchase() {
        WebElement purchase = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Purchase']")));
        purchase.click();
    }

    /**
     * Espera el modal de confirmacion (SweetAlert) tras finalizar la compra.
     *
     * @return el texto de confirmacion mostrado, por ejemplo "Thank you for your purchase!"
     */
    public String getConfirmationText() {
        WebElement confirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".sweet-alert h2")));
        return confirmation.getText();
    }

    private void typeInto(By locator, String value) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        field.clear();
        field.sendKeys(value);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
