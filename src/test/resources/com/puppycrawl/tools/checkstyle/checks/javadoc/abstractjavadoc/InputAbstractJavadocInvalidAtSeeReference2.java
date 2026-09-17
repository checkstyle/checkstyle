/*
com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheckTest$ParseJavadocOnlyCheck

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

// Details: no viable alternative at input
// 'javax.swing.tree.DefaultTreeCellRenderer.getTreeCellRendererComponent'
// while parsing REFERENCE
// violation 4 lines below 'Javadoc comment at column 8 has parse error.'
/**
 * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent()
 * @see javax.swing.tree.DefaultTreeCellRenderer.getTreeCellRendererComponent
 * @see javax.swing.tree.DefaultTreeCellRenderer.getTreeCellRendererComponent()
 */
class InputAbstractJavadocInvalidAtSeeReference2 {
}
