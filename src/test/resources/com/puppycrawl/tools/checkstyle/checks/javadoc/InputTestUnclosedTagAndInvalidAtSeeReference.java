package com.puppycrawl.tools.checkstyle.checks.javadoc;

/**
 * <unclosedTag>
 */
class InputTestUnclosedTagAndInvalidAtSeeReference {
    /**
     * @see javax.swing.tree.DefaultTreeCellRenderer.getTreeCellRendererComponent()
     */
    void invalidAtSeeReference() {
    }
}
