// non-compiled with javac: reference to non existent modules and packages

module com.example.app {
  requires java.base;
  exports com.example.api;
  // violation above ''exports' directive block should be separated .* empty line.'

  opens com.example.model;

  uses com.example.api.Service;

  provides com.example.api.Service with com.example.impl.ServiceImpl;
}
