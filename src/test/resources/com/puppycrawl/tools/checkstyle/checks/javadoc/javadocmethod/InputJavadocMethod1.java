/*
JavadocMethod


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocmethod;

/**
 * @deprecated stuff
 */
public class InputJavadocMethod1 {
}

/**
 * @deprecated stuff
 */
@interface Bleh {

    /**
     * @deprecated stuff
     */
    int method(); // violation '@return tag should be present and have description'
}
