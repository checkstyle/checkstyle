package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype;

class InputJavadocTypeNoJavadocOnInterface { // no violation, CLASS_DEF not in the list of tokens
    interface NoJavadoc {} // violation, INTERFACE_DEF in the list of tokens
}
