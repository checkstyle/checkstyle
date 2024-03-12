/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/**
 * <unclosedTag>
 */
// violation 2 lines above 'Javadoc comment at column 4 has parse error.'
// Missed HTML close tag 'unclosedTag'. Sometimes it means that close tag missed for
// one of previous tags.
class InputAbstractJavadocUnclosedTagAndInvalidAtSeeReference {
    /**
     * @see javax.swing.tree.DefaultTreeCellRenderer.getTreeCellRendererComponent()
     */
    // violation 2 lines above 'Javadoc comment at column 82 has parse error.'
    // Details: mismatched input '(' expecting <EOF> while parsing JAVADOC
    void invalidAtSeeReference() {
    }
}
