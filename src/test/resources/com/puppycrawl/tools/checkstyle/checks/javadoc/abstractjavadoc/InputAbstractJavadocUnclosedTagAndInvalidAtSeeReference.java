/*
AbstractJavadocCheckTest$Temp


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/**
 * <unclosedTag> // violation
 */
class InputAbstractJavadocUnclosedTagAndInvalidAtSeeReference {
    /**
     * @see javax.swing.tree.DefaultTreeCellRenderer.getTreeCellRendererComponent() // violation
     */
    void invalidAtSeeReference() {
    }
}
