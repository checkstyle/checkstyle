/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$TempCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/**
 * <unclosedTag>
 */
// violation 2 lines above
class InputAbstractJavadocUnclosedTagAndInvalidAtSeeReference {
    /**
     * @see javax.swing.tree.DefaultTreeCellRenderer.getTreeCellRendererComponent()
     */
    // violation 2 lines above
    void invalidAtSeeReference() {
    }
}
