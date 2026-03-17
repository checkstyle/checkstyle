package com.puppycrawl.tools.checkstyle.grammar.comments;

/**
 * Real Javadoc for comparison.
 */
public class InputJavadocMarkdownIgnoredByTool {

    /// This is a field with triple-slash comment
    private int field;

    /**
     * Real constructor Javadoc.
     */
    public InputJavadocMarkdownIgnoredByTool() {
    }

    /// This is a method with triple-slash comment
    public void method() {
        /// Inside method body
    }
}
