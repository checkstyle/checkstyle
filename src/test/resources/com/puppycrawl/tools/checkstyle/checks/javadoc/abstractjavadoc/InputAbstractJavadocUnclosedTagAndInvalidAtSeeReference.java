/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

class InputAbstractJavadocUnclosedTagAndInvalidAtSeeReference {
    /**
     * @see javax.swing.tree.DefaultTreeCellRenderer.getTreeCellRendererComponent()
     */
    // violation 2 lines above 'Javadoc comment at column 82 has parse error.'
    // Details: mismatched input '(' expecting <EOF> while parsing JAVADOC
    void invalidAtSeeReference() {
    }
}
