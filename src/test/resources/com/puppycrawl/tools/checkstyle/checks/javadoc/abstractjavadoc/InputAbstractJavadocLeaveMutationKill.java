/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$JavadocLeaveTokenCheck
OFFSET = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/**
 * some javadoc <p> some description </p>.
 *
 * @see #foo1() with
 * violation
 */
public class InputAbstractJavadocLeaveMutationKill {
    // violation 6 lines above 'Line continuation .* expected level should be 4'
    // violation 4 lines above 'Line continuation .* expected level should be 4'

    void foo1() {}
}
