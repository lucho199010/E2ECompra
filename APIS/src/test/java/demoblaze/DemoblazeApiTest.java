package demoblaze;

import com.intuit.karate.junit5.Karate;

/**
 * Runner de Karate para las pruebas de la API de Demoblaze.
 * Ejecuta todos los .feature de este paquete (signup y login).
 *
 * Ejecucion:
 *   ./gradlew test
 *   ./gradlew test -Dkarate.options="--tags @signup"
 *   ./gradlew test -Dkarate.options="--tags @login"
 */
class DemoblazeApiTest {

    @Karate.Test
    Karate testAll() {
        return Karate.run("signup", "login").relativeTo(getClass());
    }
}
