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

    /**
      * {@code selected} is an array that will contain
      * the indices of items that have been selected.
      *
      * @serial
      * @see #getSelectedIndexes()
      * @see #getSelectedIndex()
      */
    int selected[] = new int[0];
}

