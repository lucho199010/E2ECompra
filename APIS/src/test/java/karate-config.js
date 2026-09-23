function fn() {
  var env = karate.env; // definido por -Dkarate.env
  karate.log('karate.env seleccionado:', env);
  if (!env) {
    env = 'dev';
  }

  var config = {
    env: env,
    baseUrl: 'https://api.demoblaze.com'
  };

  // Timeouts de conexion y lectura (ms)
  karate.configure('connectTimeout', 10000);
  karate.configure('readTimeout', 10000);

  return config;
}
