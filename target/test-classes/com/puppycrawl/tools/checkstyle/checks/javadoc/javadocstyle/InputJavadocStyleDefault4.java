/*
JavadocStyle


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocstyle;

public class InputJavadocStyleDefault4 {

    /** <h1> Some header </h1> {@inheritDoc} {@code bar1} text*/
    void bar2() {} // violation above 'First sentence should end with a period'


    /**          <h1> Some header </h1> {@inheritDoc} {@code bar1} text       */
    void bar3() {} // violation above 'First sentence should end with a period'
}
