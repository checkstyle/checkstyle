/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = (default)false
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, MODULE_IMPORT, CLASS_DEF, \
         INTERFACE_DEF, ENUM_DEF, ENUM_CONSTANT_DEF, STATIC_INIT, INSTANCE_INIT, \
         METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, COMPACT_CTOR_DEF


*/
package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator; // violation ''package' should be separated from previous line.'



// warn



import java.lang.Object;

import java.lang.Class;



import java.lang.Integer;


import java.lang.Long;


/*
 * javadoc
 */
import java.lang.Float;

/*
 * javadoc
 */
import java.lang.Double;


/**
 * javadoc
 */
import java.lang.Character;

/**
 * javadoc
 */
import java.lang.String;

/*
 * javadoc
 */
/* javadoc */
import java.lang.Object;



// .
import java.lang.Boolean;

import java.lang.Byte;


/* javadoc */
import java.lang.Short;


/* ok javadoc */
// .
import java.lang.Number;


import java.lang.Runnable;
import java.lang.Thread;





import java.lang.StringBuilder;


/*
 * javadoc
 */
import static java.lang.Math.abs;

public class InputEmptyLineSeparatorWithComments2 {



    static {
        abs(2);
    }





    {
       abs(1);
    }

    /* something */
    { }



    {
        int i = 1;
    }

}
