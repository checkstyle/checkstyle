package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

class InputMissingJavadocTypeNoJavadocOnInterface { // no violation,
    // CLASS_DEF not in the list of tokens
    interface NoJavadoc {} // warn, INTERFACE_DEF in the list of tokens
}
