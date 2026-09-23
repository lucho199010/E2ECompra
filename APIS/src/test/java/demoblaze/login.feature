@login
Feature: Servicio Login de Demoblaze
  Verifica el inicio de sesion en https://api.demoblaze.com/login
  Nota: la API espera el password codificado en Base64.

  Background:
    * url baseUrl
    * def toBase64 =
      """
      function(txt) {
        var Base64 = Java.type('java.util.Base64');
        var bytes = txt.getBytes('UTF-8');
        return Base64.getEncoder().encodeToString(bytes);
      }
      """

  @login-correcto
  Scenario: Usuario y password correctos en login
    # PREPARACION: primero se registra un usuario nuevo para garantizar credenciales validas
    * def unique = 'qalogin_' + java.lang.System.currentTimeMillis()
    * def clave = 'Password123'
    * def pass = toBase64(clave)
    Given path 'signup'
    And request { username: '#(unique)', password: '#(pass)' }
    When method post
    Then status 200

    # ENTRADA: mismas credenciales usadas en el registro
    Given path 'login'
    And request { username: '#(unique)', password: '#(pass)' }
    When method post
    Then status 200
    # SALIDA esperada: token de autenticacion "Auth_token: <token>"
    And match response contains 'Auth_token'
    * print 'Login correcto -> respuesta:', response

  @login-password-incorrecto
  Scenario: Password incorrecto en login
    # PREPARACION: registrar usuario valido
    * def unique = 'qabad_' + java.lang.System.currentTimeMillis()
    * def pass = toBase64('Password123')
    Given path 'signup'
    And request { username: '#(unique)', password: '#(pass)' }
    When method post
    Then status 200

    # ENTRADA: usuario correcto pero password erroneo
    * def passMal = toBase64('ClaveEquivocada999')
    Given path 'login'
    And request { username: '#(unique)', password: '#(passMal)' }
    When method post
    Then status 200
    # SALIDA esperada: mensaje "Wrong password."
    And match response == { errorMessage: 'Wrong password.' }
    * print 'Login password incorrecto -> respuesta:', response

  @login-usuario-inexistente
  Scenario: Usuario inexistente en login
    # ENTRADA: usuario que no existe en el sistema
    * def inexistente = 'no_existe_' + java.lang.System.currentTimeMillis()
    * def pass = toBase64('Password123')
    Given path 'login'
    And request { username: '#(inexistente)', password: '#(pass)' }
    When method post
    Then status 200
    # SALIDA esperada: mensaje "User does not exist."
    And match response == { errorMessage: 'User does not exist.' }
    * print 'Login usuario inexistente -> respuesta:', response
