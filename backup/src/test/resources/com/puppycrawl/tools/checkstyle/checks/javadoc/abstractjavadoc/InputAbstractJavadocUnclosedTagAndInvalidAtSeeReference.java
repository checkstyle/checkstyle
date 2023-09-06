package com.puppycrawl.tools.checkstyle.checks.javadoc.abstractjavadoc;

/*
 * Config: TempCheck
 */

/**
 * <unclosedTag>
 */
// violation above 'Unclosed HTML tag found: unclosedTag'
class InputAbstractJavadocUnclosedTagAndInvalidAtSeeReference {
    /**
     * @see javax.swing.tree.DefaultTreeCellRenderer.getTreeCellRendererComponent()
     */
    // violation above 'Javadoc comment at column 82 has parse error.
    // Details: mismatched input '(' expecting <EOF> while parsing JAVADOC'
    void invalidAtSeeReference() {
    }
}
