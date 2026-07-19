/*
AtclauseOrder
violateExecutionOnNonTightHtml = (default)false
target = (default)CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, \
         CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF
tagOrder = (default)@author, @version, @param, @return, @throws, @exception, \
         @see, @since, @serial, @serialField, @serialData, @deprecated

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.atclauseorder;

import java.sql.SQLException;

public interface InputAtclauseOrderMethodReturningArrayType {
    // violation 5 lines below 'Block tags have to appear in the order .\[@author.*'
    /**
     * @return int array
     * @exception SQLException
     *
     * @throws java.sql.SQLTimeoutException
     * @see #addBatch
     * @see DatabaseMetaData#supportsBatchUpdates
     * @since 1.2
     */
    int[] executeBatch() throws SQLException;

    // violation 7 lines below 'Block tags have to appear in the order .\[@author.*'
    // violation 7 lines below 'Block tags have to appear in the order .\[@author.*'
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
