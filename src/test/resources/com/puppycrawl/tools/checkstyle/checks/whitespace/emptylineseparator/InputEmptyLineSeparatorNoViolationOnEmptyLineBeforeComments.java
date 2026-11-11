/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, STATIC_INIT, INSTANCE_INIT, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator;

import java.lang.Object;

import java.lang.Class;

// ok below
import java.lang.Long;

/*
 * no violation
 */
import java.lang.Double;

/*
 * no violation
 */
/* no violation */
import java.lang.Object;

// .
// ok below
import java.lang.Boolean;
// ok below
import java.lang.Byte;

// ok below
/* no violation */
import java.lang.Short;
import java.lang.Number;
import java.lang.Runnable;
import java.lang.Thread;
import java.lang.StringBuilder;

public class InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeComments {


    static {
        Math.abs(2);
    }




    {
        Math.abs(1);




    }

    /* no violation */
    {

        /*
         * no
         * violation
         */


    }

    /* no violation */


    {
        int i = 1;
    }






}



/* no violation */
