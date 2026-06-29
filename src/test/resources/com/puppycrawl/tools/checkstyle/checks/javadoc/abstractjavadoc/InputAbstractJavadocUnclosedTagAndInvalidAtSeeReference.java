/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

class InputAbstractJavadocUnclosedTagAndInvalidAtSeeReference {
    /**
     * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent()
     * @see javax.swing.tree.DefaultTreeCellRenderer.getTreeCellRendererComponent
     * @see javax.swing.tree.DefaultTreeCellRenderer.getTreeCellRendererComponent()
     */
    // violation 2 lines above 'Javadoc comment at column 12 has parse error.'
    // Details: no viable alternative at input
    // 'javax.swing.tree.DefaultTreeCellRenderer.getTreeCellRendererComponent'
    // while parsing REFERENCE
    void invalidAtSeeReference() {
    }
}
