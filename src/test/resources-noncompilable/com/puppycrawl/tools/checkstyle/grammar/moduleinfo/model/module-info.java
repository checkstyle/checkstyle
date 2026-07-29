// non-compiled with javac: exported packages do not exist in this module

/**
 * A data model module, open for reflective serialization.
 */
open module com.example.orders.model {
    requires transitive java.sql;

    exports com.example.orders.model;
}
