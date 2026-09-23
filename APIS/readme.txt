========================================================================
 PRUEBAS DE API REST - DEMOBLAZE (Signup / Login)
 Herramienta: Karate DSL 1.4.1 (sobre Java + Gradle)
========================================================================

DESCRIPCION
-----------
Proyecto de pruebas automatizadas de los servicios REST de la pagina
https://www.demoblaze.com/ :

  - Signup: https://api.demoblaze.com/signup
  - Login:  https://api.demoblaze.com/login

Se cubren los 4 casos solicitados (mas 1 adicional):
  1. Crear un nuevo usuario en signup
  2. Intentar crear un usuario ya existente
  3. Usuario y password correctos en login
  4. Usuario y password incorrecto en login (password erroneo)
  5. (Adicional) Usuario inexistente en login

------------------------------------------------------------------------
REQUISITOS
------------------------------------------------------------------------
  - Java JDK 17 o superior (probado con OpenJDK 21).
  - Conexion a internet (las pruebas consumen la API real de demoblaze).
  - No es necesario instalar Gradle: el proyecto incluye el Gradle Wrapper
    (./gradlew) que descarga Gradle 8.5 automaticamente en la primera
    ejecucion.

------------------------------------------------------------------------
ESTRUCTURA DEL PROYECTO
------------------------------------------------------------------------
  api-tests1-main/
  |-- build.gradle                       Configuracion Gradle + dependencia Karate
  |-- settings.gradle
  |-- gradlew / gradlew.bat              Gradle Wrapper
  |-- gradle/wrapper/                    Binarios del wrapper
  |-- readme.txt                         Este archivo
  |-- conclusiones.txt                   Hallazgos y conclusiones
  |-- src/test/java/
  |     |-- karate-config.js             Config global (baseUrl, timeouts)
  |     |-- demoblaze/
  |           |-- DemoblazeApiTest.java  Runner JUnit5 de Karate
  |           |-- signup.feature         Escenarios del servicio Signup
  |           |-- login.feature          Escenarios del servicio Login
  |-- build/karate-reports/              Reportes HTML (se generan al ejecutar)

------------------------------------------------------------------------
PASOS DE EJECUCION
------------------------------------------------------------------------
1. Abrir una terminal en la carpeta raiz del proyecto (api-tests1-main).

2. Ejecutar TODAS las pruebas:

       ./gradlew test

   En Windows:

       gradlew.bat test

3. Ejecutar solo un grupo por etiqueta (tags):

       ./gradlew test -Dkarate.options="--tags @signup"
       ./gradlew test -Dkarate.options="--tags @login"
       ./gradlew test -Dkarate.options="--tags @login-correcto"

4. Ver el reporte HTML generado (abrir en el navegador):

       build/karate-reports/karate-summary.html

------------------------------------------------------------------------
NOTA IMPORTANTE SOBRE EL PASSWORD
------------------------------------------------------------------------
La API de demoblaze NO recibe el password en texto plano: espera el
password codificado en Base64. Por ejemplo, "Password123" viaja como
"UGFzc3dvcmQxMjM=". Los features usan una funcion auxiliar (toBase64)
para codificarlo antes de enviarlo, replicando el comportamiento del
frontend real de demoblaze.

Los usuarios de signup/login-correcto se generan con un sufijo de
timestamp (System.currentTimeMillis) para que cada corrida use un
usuario unico y las pruebas sean repetibles.
========================================================================
