# Conclusiones

Prueba E2E del flujo de compra en [demoblaze.com](https://www.demoblaze.com/)
con Selenium WebDriver + JUnit 5, aplicando el patrón Page Object.

## Resultado

Se automatizó el flujo completo: agregar dos productos, visualizar el carrito,
completar el formulario de compra y finalizar (Purchase), validando el mensaje
de confirmación `Thank you for your purchase!`.

## Hallazgos técnicos

- El botón **Add to cart** dispara un `alert` nativo de JavaScript que debe
  aceptarse con `driver.switchTo().alert().accept()`, o la prueba se bloquea.
- El catálogo se carga de forma **asíncrona (AJAX)**, por lo que se usaron
  **esperas explícitas** (`WebDriverWait`) en lugar de esperas fijas para
  evitar tests inestables (*flaky*).
- La confirmación de compra se muestra en un modal **SweetAlert**
  (`.sweet-alert h2`), distinto de un `alert` nativo.
- **WebDriverManager** descarga y configura el ChromeDriver compatible en
  tiempo de ejecución, evitando versionar binarios manualmente.
- El formulario de compra no valida los datos ingresados: acepta valores
  ficticios y permite finalizar igualmente.

## Decisiones de diseño

- **Page Object** para separar selectores/acciones de la lógica de prueba.
- Esperas explícitas en lugar de `Thread.sleep`.
- Soporte **headless** vía `-Dheadless=true` (útil para CI).
- Toolchain de **Java 21** declarado en `build.gradle`.

## Limitaciones

- Depende de un sitio público de terceros; cambios o caídas pueden afectar la
  ejecución.
- Los nombres de producto usados como localizadores dependen del catálogo actual.

## Mejoras futuras

- Parametrizar productos y datos del formulario (*data-driven*).
- Capturas de pantalla automáticas ante fallos.
- Soporte multi-navegador (Firefox, Edge).
- Integración continua con GitHub Actions.
- Reportes enriquecidos (Allure).
