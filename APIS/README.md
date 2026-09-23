# Pruebas de API con Karate — Demoblaze (Signup / Login)

Pruebas automatizadas de los servicios REST de [demoblaze.com](https://www.demoblaze.com/)
usando **Karate DSL**. Se validan los servicios de registro (signup) e inicio de sesión (login).

## Servicios probados

| Servicio | Endpoint |
|----------|----------|
| Signup   | `POST https://api.demoblaze.com/signup` |
| Login    | `POST https://api.demoblaze.com/login`  |

## Casos cubiertos

1. Crear un nuevo usuario en signup
2. Intentar crear un usuario ya existente
3. Usuario y password correctos en login
4. Usuario y password incorrecto en login
5. (Adicional) Usuario inexistente en login

## Requisitos

- Java JDK 17+ (probado con OpenJDK 21)
- Conexión a internet
- No requiere instalar Gradle: se incluye el Gradle Wrapper (`./gradlew`)

## Ejecución

```bash
# Todas las pruebas
./gradlew test

# Por etiqueta
./gradlew test -Dkarate.options="--tags @signup"
./gradlew test -Dkarate.options="--tags @login"
```

El reporte HTML se genera en `build/karate-reports/karate-summary.html`.

## Estructura

```
src/test/java/
├── karate-config.js            # Configuración global (baseUrl, timeouts)
└── demoblaze/
    ├── DemoblazeApiTest.java    # Runner JUnit5
    ├── signup.feature           # Escenarios de Signup
    └── login.feature            # Escenarios de Login
```

## Notas

- La API espera el **password codificado en Base64** (igual que el frontend real).
- Todas las respuestas llegan con **HTTP 200**; el éxito/error se determina por el
  cuerpo de la respuesta.

Ver `readme.txt` (instrucciones paso a paso) y `conclusiones.txt` (hallazgos) para más detalle.
