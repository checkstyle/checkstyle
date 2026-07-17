// non-compiled with javac: referenced modules are not on the module path

/**
 * A JDBC driver module.
 */
module com.example.jdbc.driver {
    requires transitive java.sql;
    requires java.logging;

    exports com.example.jdbc.driver;

    provides java.sql.Driver with com.example.jdbc.driver.ExampleDriver;
}
