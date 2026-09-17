// non-compiled with javac: reference to non existent modules and packages

module com.example.app {
  requires java.base;

  exports com.example.api;

  requires java.sql;
  // violation above 'All 'requires' directives should be in a single block.'

  opens com.example.model;

  uses com.example.api.Service;
}
