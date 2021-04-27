package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;

import java.sql.SQLException;

public interface InputAtclauseOrderMethodReturningArrayType {
    /**
     * @return int array
     * @exception SQLException
     * @throws java.sql.SQLTimeoutException
     * @see #addBatch
     * @see DatabaseMetaData#supportsBatchUpdates
     * @since 1.2
     */
    int[] executeBatch() throws SQLException;
}

