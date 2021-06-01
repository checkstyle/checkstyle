package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;

import java.sql.SQLException;

/* Config: default
 */
public interface InputAtclauseOrderMethodReturningArrayType {
    /**
     * @return int array // violation
     * @exception SQLException
     * @throws java.sql.SQLTimeoutException
     * @see #addBatch
     * @see DatabaseMetaData#supportsBatchUpdates
     * @since 1.2
     */
    int[] executeBatch() throws SQLException;
}

