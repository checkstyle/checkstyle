/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = false
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, STATIC_INIT, INSTANCE_INIT, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator; // violation ''package' should be separated from previous line.'



// violation ''//' has more than 1 empty lines before.'



import java.lang.Object; // violation ''import' has more than 1 empty lines before.'

import java.lang.Class;


// violation ''//' has more than 1 empty lines before.'
import java.lang.Integer;

// ok, because no more than 1 empty lines before
import java.lang.Long;


/*
 *
 */
import java.lang.Float; // violation 3 lines above ''/\*' has more than 1 empty lines before.'

/*
 * something
 */
import java.lang.Double;


/**
 *
 */
import java.lang.Character; // violation 3 lines above ''/\*' has more than 1 empty lines before.'

/**
 * something
 */
import java.lang.String;

/*
 * something
 */
/* something */
import java.lang.Object;


// violation ''//' has more than 1 empty lines before.'
// .
import java.lang.Boolean;

import java.lang.Byte;

// ok, because no more than 1 empty lines before
/* something  */
import java.lang.Short;


/* something */
// violation above ''/\*' has more than 1 empty lines before.'
import java.lang.Number;


import java.lang.Runnable; // violation ''import' has more than 1 empty lines before.'
import java.lang.Thread;

// ok, because no more than 1 empty lines before


// violation ''//' has more than 1 empty lines before.'
import java.lang.StringBuilder;


/*
 *
 */
import static java.lang.Math.abs; // violation 3 lines above ''/\*' has more than 1 empty lines before.'

public class InputEmptyLineSeparatorWithComments {


    // violation ''//' has more than 1 empty lines before.'
    static {
        abs(2);
    }

    // ok, because no more than 1 empty lines before


    // violation ''//' has more than 1 empty lines before.'
    {
       abs(1);
    }

    /* something */
    { }


    // violation ''//' has more than 1 empty lines before.'
    {
        int i = 1;
    }

}
