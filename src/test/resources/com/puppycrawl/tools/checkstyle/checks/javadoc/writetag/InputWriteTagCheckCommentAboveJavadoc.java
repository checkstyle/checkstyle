/*
WriteTag
tag = @since
tagFormat = (default)null
tokens = CLASS_DEF, METHOD_DEF
violateExecutionOnNonTightHtml = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

/*
 * File header block comment.
 */
public class InputWriteTagCheckCommentAboveJavadoc {

    /*
     * This class maintains a lazily-initialized table of atomically
     * updated variables, plus an extra "base" field. The table size
     * is a power of two. Indexing uses masked per-thread hash codes.
     * Nearly all declarations in this class are package-private,
     * accessed directly by subclasses.
     */

    /**
     * The real Javadoc for Cell, immediately adjacent to the declaration.
     */
    static final class Cell { // violation 'Javadoc comment is missing @since tag.'
        volatile long value;

        Cell(long x) {
            value = x;
        }
    }

    /* some comment */

    /** dongling javadoc comment */
    /**
     * to set method.
     *
     */
    public void method() { // violation 'Javadoc comment is missing @since tag.'
    }

}
