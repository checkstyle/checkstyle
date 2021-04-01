package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

/* Config:
 *
 * tokens = INTERFACE_DEF
 */

class InputJavadocTypeNoJavadocOnInterface { // ok
    interface NoJavadoc {} // ok
}
