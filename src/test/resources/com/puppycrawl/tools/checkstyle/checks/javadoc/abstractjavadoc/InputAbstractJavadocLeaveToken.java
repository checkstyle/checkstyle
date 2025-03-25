/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$JavadocLeaveTokenCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/**
 * some javadoc <p> some description </p>.
 *
 * @see #foo1() with
 * violation
 */
public class InputAbstractJavadocLeaveToken {
    void foo1() {}
}
