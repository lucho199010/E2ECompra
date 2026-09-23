# Prueba E2E - Flujo de Compra en Demoblaze

Prueba funcional automatizada **End-to-End (E2E)** del flujo de compra en
[https://www.demoblaze.com/](https://www.demoblaze.com/), implementada con
**Selenium WebDriver 4 + JUnit 5 + Gradle** y el patrón **Page Object**.

## Flujo automatizado

1. Agregar dos productos al carrito
2. Visualizar el carrito
3. Completar el formulario de compra
4. Finalizar la compra (Purchase)

La prueba valida que el carrito contenga exactamente los 2 productos y que al
finalizar se muestre el mensaje `Thank you for your purchase!`.

## Requisitos

- Java JDK 21 (o superior)
- Google Chrome
- Conexión a internet (WebDriverManager descarga el driver automáticamente)

> No es necesario instalar Gradle: el proyecto incluye el Gradle Wrapper (`./gradlew`).

## Estructura

```
build.gradle                                  Dependencias y toolchain Java 21
src/test/java/com/ejemplo/tests/
├── CompraE2ETest.java                        Caso de prueba E2E
└── pages/DemoblazePage.java                  Page Object (selectores + acciones)
readme.txt                                    Instrucciones de ejecución
conclusiones.txt                              Hallazgos y conclusiones
```

## Ejecución

```bash
# macOS / Linux
./gradlew clean test

# Windows
gradlew.bat clean test

# Modo headless (sin ventana del navegador)
./gradlew clean test -Dheadless=true
```

## Reporte de resultados

Tras la ejecución, el reporte HTML se genera en:

```
build/reports/tests/test/index.html
```

## Datos de prueba

| Campo    | Valor              |
|----------|--------------------|
| Producto 1 | Samsung galaxy s6 |
| Producto 2 | Nexus 6           |
| Name     | Luis Prado         |
| Country  | Peru               |
| City     | Lima               |
| Card     | 4111111111111111   |
| Month    | 12                 |
| Year     | 2026               |
