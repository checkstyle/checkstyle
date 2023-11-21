/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$TempCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/**
 * <unclosedTag>
 */
// violation 2 lines above 'Javadoc comment at column 4 has parse error.'
class InputAbstractJavadocUnclosedTagAndInvalidAtSeeReference {
    /**
     * @see javax.swing.tree.DefaultTreeCellRenderer.getTreeCellRendererComponent()
     */
    // violation 2 lines above 'Javadoc comment at column 82 has parse error.'
    void invalidAtSeeReference() {
    }
}
