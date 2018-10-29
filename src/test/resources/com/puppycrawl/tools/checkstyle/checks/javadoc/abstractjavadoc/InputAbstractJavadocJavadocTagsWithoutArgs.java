package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

import java.io.Serializable;

/**@author*/
public class InputAbstractJavadocJavadocTagsWithoutArgs implements Serializable{
    /**@serial*/
    private static final long serialVersionUID = 7556448691029650757L;

    /**@see*/
    private static int field2;

    /**@since*/
    private static String field3;

    /**@version*/
    private static Object field4;

    /**serialField*/
    private static Object field5;

    /**@exception*/
    public static void method1() {

    }

    /**@throws*/
    public static void method2() {

    }

    /**@return*/
    public static int method3() {
        return -1;
    }

    /**@param*/
    public static void method4(int a) {

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
