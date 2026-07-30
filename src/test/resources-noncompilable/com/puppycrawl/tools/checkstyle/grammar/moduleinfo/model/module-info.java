// non-compiled with javac: reference to non existent packages

/**
 * A data model module, open for reflective serialization.
 */
open module com.example.orders.model {
    requires transitive java.sql;

    exports com.example.orders.model;
}
