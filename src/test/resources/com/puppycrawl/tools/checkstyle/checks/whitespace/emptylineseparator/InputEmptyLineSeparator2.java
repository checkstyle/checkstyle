/*
EmptyLineSeparator
allowNoEmptyLineBetweenFields = true
allowMultipleEmptyLines = (default)true
allowMultipleEmptyLinesInsideClassMembers = (default)true
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT, CLASS_DEF, INTERFACE_DEF, ENUM_DEF, \
         STATIC_INIT, INSTANCE_INIT, METHOD_DEF, CTOR_DEF, VARIABLE_DEF, RECORD_DEF, \
         COMPACT_CTOR_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator; //no violation: trailing comment
import java.io.Serializable; // violation ''import' should be separated from previous line.'
import java.util.ArrayList; /*no violation: trailing comment*/
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.Collections;
/* no violation: block comment after token*/

import com.puppycrawl.tools.checkstyle.checks.whitespace.emptylineseparator.InputEmptyLineSeparator;
//no violation: single-line comment after token

import javax.swing.AbstractAction; /* no violation: no trailing comment
*/

import org.apache.commons.beanutils.locale.converters.ByteLocaleConverter;
import org.apache.commons.beanutils.BasicDynaBean;
class InputEmptyLineSeparator2 // violation ''CLASS_DEF' should be separated from previous line.'
{
    public static final double FOO_PI = 3.1415;
    private boolean flag = true;
    static { // violation ''STATIC_INIT' should be separated from previous line.'
        //empty static initializer
    }
    // no blank line - fail
    {
        //empty // violation above ''INSTANCE_INIT'.*separated.*from.*previous.*line.'
    }

    // one blank line - ok
    {
        //empty instance initializer
    }
    // no blank line - fail
    /**
     *
     *
     *
     */
    private InputEmptyLineSeparator2() // violation ''CTOR_DEF'.*separated.*from.*previous.*line.'
    {
        //empty
    }
    //separator blank line
    public int // violation ''METHOD_DEF'.*separated.*from.*previous.*line.'
    compareTo(Object aObject) {
        int number = 0;
        return 0;
    }

    public int compareTo2(Object aObject) // empty line - ok
    {
        int number = 0;
        return 0;
    }
    /**
     * No blank line between methods - fail
     * @param task
     * @param result
     * @return
     */
    public static <T> Callable<T> // violation ''METHOD_DEF'.*separated.*from.*previous.*line.'
    callable(Runnable task, T result) {
        return null;
    }

    /**
     * Blank line before Javadoc - ok
     * @param task
     * @param result
     * @return
     */
    public static <T> Callable<T> callable2(Runnable task, T result)
    {
        return null;
    }
    /**
     * Blank line after Javadoc - ok
     * @param task
     * @param result
     * @return
     */

    public static <T> Callable<T> callable3(Runnable task, T result)
    {
        return null;
    }

    public int getBeastNumber()
    {
        return 666;
    }
    interface IntEnum { // violation ''INTERFACE_DEF' should be separated from previous line.'
    }

    class InnerClass {

        public static final double FOO_PI_INNER = 3.1415;

        private boolean flagInner = true;

        {
            //empty instance initializer
        }

        private InnerClass()
        {
            //empty
        }
    }


    class SecondInnerClass {

        private int intVariable;
    }
}

class Class22{
    public int compareTo(InputEmptyLineSeparator aObject) //ok
    {
        int number = 0;
        return 0;
    }

    Class2 anon = new Class2(){
        public int compareTo(InputEmptyLineSeparator aObject) //ok
        {
            int number = 0;
            return 0;
        }
    };
}

class Class32 {
    int field;
}
