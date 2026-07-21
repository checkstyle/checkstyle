/*
MissingJavadocType
excludeScope = (default)null
scope = PRIVATE
skipAnnotations = (default)Generated
violateExecutionOnNonTightHtml = (default)false
tokens = INTERFACE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.missingjavadoctype;

class InputMissingJavadocTypeNoJavadocOnInterface {
    // CLASS_DEF not in the list of tokens
    // violation below 'Missing a Javadoc comment.'
    interface NoJavadoc {}
}
