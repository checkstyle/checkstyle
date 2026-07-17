// non-compiled with javac: reference to non existent packages

import java.sql.Driver;

/**
 * The descriptor of the java.sql platform module, with the service type
 * referenced by its simple name through an import declaration.
 */
module java.sql {
    requires transitive java.logging;
    requires transitive java.transaction.xa;
    requires transitive java.xml;

    exports java.sql;
    exports javax.sql;

    uses Driver;
}
