/*
JavadocUtilizingTrailingSpace
lineLimit = 50
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocutilizingtrailingspace;

/**
 * Test file with custom line limit of 50.
 */
public class InputJavadocUtilizingTrailingSpaceCustomLimit {

    // violation 2 lines below 'Line is longer than 50 characters (found 58).'
    /**
     * This line exceeds the custom fifty characters limit
     */
    public void exceedsCustom() { }

    /**
     * This does not exceed the character limit
     */
    public void correctedExceedsCustom() { }

    /**
     * Good short line within limit. Exact 50 test
     */
    public void withinCustom() { }

    // violation 2 lines below 'Line under-utilized (12/50). Words from below could be moved up'
    /**
     * Short
     * text here.
     */
    public void tooShortCustom() { }

    /**
     * Short text here.
     */
    public void correctedTooShortCustom() { }

    /**
     * @param value the parameter with long description here
     */
    public void paramExceeds(int value) { }

    /**
     * @param v short param desc
     */
    public void paramOk(int v) { }

    /**
     * Exactly at limit for 49 characters limitss
     */
    public void exactlyAtLimit() { }

    /**
     * {@link com.example.VeryLongClassName}
     */
    public void linkExceedsCustom() { }

    /**
     * http://example.com/path/to/resource
     */
    public void urlExceedsCustom() { }

    /**
     * With link {@link Object} inline.
     */
    public void linkInMiddleExceeds() { }

    // violation 2 lines below 'Line is longer than 50 characters (found 75).'
    /**
     * Long text {@code code} http://example.com/extra/to/make/it/too/long.
     */
    void foo() {}

    /**
     * Long text {@code code}
     * http://example.com/extra/to/make/it/too/long.
     */
    void correctedFoo() {}
}
