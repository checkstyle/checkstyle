/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

class InputAbstractJavadocUnclosedTagAndInvalidAtSeeReference {
    /**
     * @serialField Fieldname-fieldtype-fielddescription
     */
    // violation 2 lines above 'Javadoc comment at column 29 has parse error.'
    // Details: token recognition error at: '-' while parsing Fieldname
    void testLexerError() {
    }
}
