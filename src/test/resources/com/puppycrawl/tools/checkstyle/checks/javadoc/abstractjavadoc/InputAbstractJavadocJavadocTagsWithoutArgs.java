/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

import java.io.Serializable;

/**@author*/ // ok
public class InputAbstractJavadocJavadocTagsWithoutArgs implements Serializable{

    /**@serial*/ // ok
    private static final long serialVersionUID = 7556448691029650757L;

    /**@see*/ // violation 'Javadoc comment at column 4 has parse error.'
    // Details: no viable alternative at input '<EOF>' while parsing JAVADOC_TAG
    private static int field2;

    /**@since*/ // ok
    private static String field3;

    /**@version*/ // ok
    private static Object field4;

    /**serialField*/ // ok
    private static Object field5;

    /**@exception*/ // violation 'Javadoc comment at column 10 has parse error.'
    // Details: no viable alternative at input '<EOF>' while parsing JAVADOC_TAG
    public static void method1() {

    }

    /**@throws*/ // violation 'Javadoc comment at column 7 has parse error.'
    // Details: no viable alternative at input '<EOF>' while parsing JAVADOC_TAG
    public static void method2() {

    }

    /**@return*/ // ok
    public static int method3() {
        return -1;
    }

    /**@param*/ // violation 'Javadoc comment at column 6 has parse error.'
    // Details: no viable alternative at input '<EOF>' while parsing JAVADOC_TAG
    public static void method4(int a) {

    }

    /**@customTag*/ // ok
    public static void method5(int a) {

    }

    /**@deprecated*/ // ok
    public static void method6(int a) {

    }

    /**@serialData*/ // ok
    private void readObject(java.io.ObjectInputStream inputStream) {

    }

    /**
     * {@link}
     */
    public void method7() {

    }

    /**
     * {@linkplain }
     */
    public void method8() {

    }
}
