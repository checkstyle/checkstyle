// non-compiled with javac: reference to non existent packages

/**
 * A JDBC driver module.
 */
module com.example.jdbc.driver {
    requires transitive java.sql;
    requires java.logging;

    exports com.example.jdbc.driver;

    provides java.sql.Driver with com.example.jdbc.driver.ExampleDriver;
}
