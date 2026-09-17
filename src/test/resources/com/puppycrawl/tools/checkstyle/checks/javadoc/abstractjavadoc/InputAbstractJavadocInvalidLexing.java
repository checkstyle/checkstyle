/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

class InputAbstractJavadocInvalidLexing {
    // Details: token recognition error at: '-' while parsing Fieldname
    // violation 2 lines below 'Javadoc comment at column 29 has parse error.'
    /**
     * @serialField fieldName-fieldtype-fielddescription
     */
    void testLexerError() {
    }
}
