/*
JavadocUtilizingTrailingSpace
lineLimit = (default)80
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file for @see and @link reference handling in various contexts.
 */
public class InputJavadocUtilizingTrailingSpaceReferences {

    /**
     * @see String
     */
    public void simpleRef() { }

    /**
     * @see java.util.List#add(Object)
     */
    public void methodRef() { }

    /**
     * @see <a href="http://example.com">Example Site</a>
     */
    public void htmlLinkRef() { }

    /**
     * @see com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocUtilizingTrailingSpaceCheck
     */
    public void longPackageRef() { }

    /**
     * @see "The Java Language Specification"
     */
    public void stringRef() { }

    /**
     * Multiple references:
     *
     * @see String
     * @see Object
     * @see java.util.List
     */
    public void multipleRefs() { }

    /**
     * {@link String} inline reference.
     */
    public void inlineRef() { }

    /**
     * {@linkplain String plain link} with text.
     */
    public void plainLink() { }

    /**
     * Reference to {@link #anotherMethod(String, int)} in same class.
     */
    public void internalRef() { }

    /**
     * Another method for reference.
     *
     * @param name the name parameter
     * @param count the count parameter
     */
    public void anotherMethod(String name, int count) { }

    /**
     * @see Object#equals(Object)
     * @see Object#hashCode()
     * @see Object#toString()
     */
    public void standardObjectMethods() { }

    /**
     * {@link java.util.concurrent.atomic.AtomicLongFieldUpdater} very long.
     */
    public void veryLongLink() { }
}
