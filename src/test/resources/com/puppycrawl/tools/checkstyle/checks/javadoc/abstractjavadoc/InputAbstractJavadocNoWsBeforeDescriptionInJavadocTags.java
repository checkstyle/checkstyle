package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

import java.io.ObjectStreamField;
import java.io.Serializable;

/*
 * Config: TempCheck
 */

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
    // violation above 'Javadoc comment at column 23 has parse error.
    // Details: mismatched input 'd' expecting <EOF> while parsing JAVADOC'
    private static final long serialVersionUID = 8669426759693842025L;

    /**
     * Parse Error from ANTLR.
     * @serialField Fieldname-fieldtype-fielddescription
     */
    // violation above 'Javadoc comment at column 30 has parse error.
    // Details: mismatched input '-' expecting <EOF> while parsing JAVADOC'
    private static ObjectStreamField objectStreamField1;

    /**
     * Parse Error from ANTLR.
     * @serialField Fieldname fieldtype-fielddescription
     */
    // violation above 'Javadoc comment at column 39 has parse error.
    // Details: mismatched input '-' expecting <EOF> while parsing JAVADOC'
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
    // violation above 'Javadoc comment at column 34 has parse error.
    // Details: mismatched input '-' expecting <EOF> while parsing JAVADOC'
    private static void method1() {

    }

    /**
     * Parse Error from ANTLR.
     * @throws RuntimeException-description
     */
    // violation above 'Javadoc comment at column 31 has parse error.
    // Details: mismatched input '-' expecting <EOF> while parsing JAVADOC'
    private static void method2() {

    }

    /**
     * Parse Error from ANTLR.
     *
     * @param a-description
     */
    // violation above 'Javadoc comment at column 15 has parse error.
    // Details: mismatched input '-' expecting <EOF> while parsing JAVADOC'
    private static void method3(int a) {

    }

    /**
     * @see #objectStreamField1-description
     */
    // violation above 'Javadoc comment at column 33 has parse error.
    // Details: mismatched input '-' expecting <EOF> while parsing JAVADOC'
    private static void method4() {

    }

    // violation below 'Javadoc comment at column 17 has parse error.
    // Details: mismatched input '<' expecting <EOF> while parsing JAVADOC'
    /**
     * @customTag<description>}
     */
    private static void method8() {

    }

    /**
     * {@link #objectStreamField2-description}
     */
    // violation above 'Javadoc comment at column 34 has parse error.
    // Details: no viable alternative at input '-' while parsing JAVADOC_INLINE_TAG'
    private static void method5() {
    }
    /**
     * {@linkplain #objectStreamField3-description}
     */
    // violation above 'Javadoc comment at column 39 has parse error.
    // Details: no viable alternative at input '-' while parsing JAVADOC_INLINE_TAG'
    private static void method6() {

    }
    /**
     * {@customlink<description>}
     */
    // violation above 'Javadoc comment at column 34 has parse error.
    // Details: no viable alternative at input '<' while parsing JAVADOC_INLINE_TAG'
    private static void method7() {
    }
}
