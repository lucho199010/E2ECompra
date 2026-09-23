# Pruebas Demoblaze — E2E y APIs

Repositorio con las dos pruebas automatizadas sobre [demoblaze.com](https://www.demoblaze.com/):

| Carpeta | Descripción | Herramientas |
|---------|-------------|--------------|
| [`E2E/`](./E2E) | Prueba End-to-End del flujo de compra (agregar productos, carrito, checkout, confirmación) | Selenium WebDriver 4 + JUnit 5 + Gradle (Page Object) |
| [`APIS/`](./APIS) | Pruebas de los servicios REST de Signup y Login | Karate DSL + Gradle |

Cada carpeta es un proyecto Gradle independiente con su propio `README.md`,
`readme.txt` (pasos de ejecución) y `conclusiones.txt` (hallazgos).

## Ejecución rápida

```bash
# Prueba E2E
cd E2E
./gradlew clean test

# Pruebas de API
cd APIS
./gradlew test
```

## Requisitos

- Java JDK 21 (o superior)
- Conexión a internet
- Google Chrome (solo para la prueba E2E)

No es necesario instalar Gradle: ambos proyectos incluyen el Gradle Wrapper.
