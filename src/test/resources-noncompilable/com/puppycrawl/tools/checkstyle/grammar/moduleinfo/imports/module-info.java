// non-compiled with javac: reference to non existent packages

import java.sql.Driver;
import java.sql.*;
import static java.sql.Types.INTEGER;
import static java.sql.Types.*;
import module java.base;

/**
 * The descriptor of the java.sql platform module, preceded by every kind of
 * import declaration that a modular compilation unit accepts.
 */
module java.sql {
    requires transitive java.logging;
    requires transitive java.transaction.xa;
    requires transitive java.xml;

    exports java.sql;
    exports javax.sql;

    uses Driver;
}
