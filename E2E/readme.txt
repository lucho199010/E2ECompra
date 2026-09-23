====================================================================
 PRUEBA E2E - FLUJO DE COMPRA EN DEMOBLAZE.COM
 Automatizacion con Selenium WebDriver + JUnit 5 + Gradle
====================================================================

--------------------------------------------------------------------
1. DESCRIPCION
--------------------------------------------------------------------
Prueba funcional automatizada End-to-End (E2E) del flujo de compra
en la pagina https://www.demoblaze.com/ que cubre:

  1. Agregar dos productos al carrito
  2. Visualizar el carrito
  3. Completar el formulario de compra
  4. Finalizar la compra (Purchase)

La prueba valida que el carrito contenga exactamente los 2 productos
agregados y que al finalizar se muestre el mensaje de confirmacion
"Thank you for your purchase!".

--------------------------------------------------------------------
2. REQUISITOS PREVIOS
--------------------------------------------------------------------
  - Java JDK 21 (o superior) instalado y accesible.
      Verificar con:  java -version
  - Conexion a internet (WebDriverManager descarga el driver y la
    prueba navega a un sitio web publico).
  - Google Chrome instalado.
      * WebDriverManager descarga automaticamente el ChromeDriver
        compatible con la version de Chrome instalada; NO es
        necesario configurar el driver manualmente.
  - No es necesario instalar Gradle: el proyecto incluye el Gradle
    Wrapper (./gradlew).

  NOTA: Si Java no esta instalado, en macOS se puede instalar con:
      brew install openjdk@21
  y luego enlazarlo segun las instrucciones que muestra brew.

--------------------------------------------------------------------
3. ESTRUCTURA DEL PROYECTO
--------------------------------------------------------------------
  build.gradle
      Configuracion de dependencias (Selenium, WebDriverManager,
      JUnit 5) y del toolchain de Java 21.

  src/test/java/com/ejemplo/tests/
      CompraE2ETest.java        -> Caso de prueba E2E del flujo de compra.
      pages/DemoblazePage.java  -> Page Object con selectores y acciones.

--------------------------------------------------------------------
4. COMO EJECUTAR LA PRUEBA
--------------------------------------------------------------------
Desde la raiz del proyecto (carpeta E2ECompra):

  macOS / Linux:
      ./gradlew clean test

  Windows:
      gradlew.bat clean test

  Modo headless (sin abrir ventana del navegador):
      ./gradlew clean test -Dheadless=true

--------------------------------------------------------------------
5. REPORTES
--------------------------------------------------------------------
Tras la ejecucion, Gradle genera un reporte HTML de resultados en:

      build/reports/tests/test/index.html

Abrir ese archivo en un navegador para ver el detalle de la prueba
(pasada / fallida, tiempos y trazas).

--------------------------------------------------------------------
6. DATOS DE PRUEBA UTILIZADOS
--------------------------------------------------------------------
  Producto 1 : Samsung galaxy s6
  Producto 2 : Nexus 6

  Formulario de compra:
    Name    : Luis Prado
    Country : Peru
    City    : Lima
    Card    : 4111111111111111
    Month   : 12
    Year    : 2026

Estos valores pueden modificarse en el archivo CompraE2ETest.java.
====================================================================
