/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

import java.io.Serializable;

/**@author*/
public class InputAbstractJavadocJavadocTagsWithoutArgs implements Serializable{

    /**@serial*/
    private static final long serialVersionUID = 7556448691029650757L;

    /**@see*/ // violation 'Javadoc comment at column 4 has parse error.'
    // Details: no viable alternative at input '<EOF>' while parsing JAVADOC_TAG
    private static int field2;

    /**@since*/
    private static String field3;

    /**@version*/
    private static Object field4;

    /**serialField*/
    private static Object field5;

    /**@return*/
    public static int method3() {
        return -1;
    }

    /**@customTag*/
    public static void method5(int a) {

    }

    /**@deprecated*/
    public static void method6(int a) {

    }

    /**@serialData*/
    private void readObject(java.io.ObjectInputStream inputStream) {

    }

    // Details: no viable alternative at input '}' while parsing REFERENCE
    // violation 2 lines below 'Javadoc comment at column 13 has parse error.'
    /**
     * {@link}
     */
    public void method7() {

    }

    // Details: no viable alternative at input '}' while parsing REFERENCE
    // violation 2 lines below 'Javadoc comment at column 19 has parse error.'
    /**
     * {@linkplain }
     */
    public void method8() {

    }
}
