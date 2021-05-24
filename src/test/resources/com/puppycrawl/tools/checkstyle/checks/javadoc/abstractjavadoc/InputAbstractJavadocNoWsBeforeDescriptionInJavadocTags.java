package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

import java.io.ObjectStreamField;
import java.io.Serializable;

/**
 * @author author-name <link rel="author license" href="/about">
 */
public class InputAbstractJavadocNoWsBeforeDescriptionInJavadocTags implements Serializable{

    /**
     * Parse Error from ANTLR.
     * @serial include description
     * @see <a
     * href="https://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#serial">
     * oracle docs</a> Syntax: <b>@serial field-description | include | exclude</b>
     */
    private static final long serialVersionUID = 8669426759693842025L;

    /**
     * Parse Error from ANTLR.
     * @serialField Fieldname-fieldtype-fielddescription
     */
    private static ObjectStreamField objectStreamField1;

    /**
     * Parse Error from ANTLR.
     * @serialField Fieldname fieldtype-fielddescription
     */
    private static ObjectStreamField objectStreamField2;

    /**
     * @serialField Fieldname -fieldtype -fielddescription <!-- No
     * error -->
     */
    private static ObjectStreamField objectStreamField3;

    /**
     * Parse Error from ANTLR.
     * @exception RuntimeException-description
     */
    private static void method1() {

    }

    /**
     * Parse Error from ANTLR.
     * @throws RuntimeException-description
     */
    private static void method2() {

    }

    /**
     * Parse Error from ANTLR.
     *
     * @param a-description
     */
    private static void method3(int a) {

    }

    /**
     * @see #objectStreamField1-description
     */
    private static void method4() {

    }

    /**
     * @customTag<description>
     */
    private static void method8() {

    }

    /**
     * {@link #objectStreamField2-description}
     */
    private static void method5() {

    }

    /**
     * {@linkplain #objectStreamField3-description}
     */
    private static void method6() {

    }

    /**
     * {@customlink<description>}
     */
    private static void method7() {

    }
}
