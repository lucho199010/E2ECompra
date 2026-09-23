@signup
Feature: Servicio Signup de Demoblaze
  Verifica el registro de usuarios en https://api.demoblaze.com/signup
  Nota: la API espera el password codificado en Base64.

  Background:
    * url baseUrl
    # Genera un password en Base64 (la API lo almacena/compara asi)
    * def toBase64 =
      """
      function(txt) {
        var Base64 = Java.type('java.util.Base64');
        var bytes = txt.getBytes('UTF-8');
        return Base64.getEncoder().encodeToString(bytes);
      }
      """

  @signup-nuevo
  Scenario: Crear un nuevo usuario en signup
    # ENTRADA: usuario unico (timestamp) + password en Base64
    * def unique = 'qauser_' + java.lang.System.currentTimeMillis()
    * def pass = toBase64('Password123')
    Given path 'signup'
    And request { username: '#(unique)', password: '#(pass)' }
    When method post
    Then status 200
    # SALIDA esperada: la API responde el texto literal "" (cadena JSON vacia) cuando el registro es exitoso.
    # Se normaliza con trim() porque el cuerpo llega como '""' seguido de salto de linea.
    * def cuerpo = (response + '').trim()
    And match cuerpo == '""'
    * print 'Signup nuevo -> usuario:', unique, '| respuesta:', response

  @signup-existente
  Scenario: Intentar crear un usuario ya existente
    # ENTRADA: usuario que ya fue registrado previamente
    * def existente = 'admin'
    * def pass = toBase64('admin')
    Given path 'signup'
    And request { username: '#(existente)', password: '#(pass)' }
    When method post
    Then status 200
    # SALIDA esperada: mensaje de error indicando que el usuario ya existe
    And match response == { errorMessage: 'This user already exist.' }
    * print 'Signup existente -> respuesta:', response
