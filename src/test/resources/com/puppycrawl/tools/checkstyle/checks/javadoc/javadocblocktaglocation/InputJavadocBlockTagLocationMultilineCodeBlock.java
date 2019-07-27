package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

/**
 * configuration: default
 */
public class InputJavadocBlockTagLocationMultilineCodeBlock {

    /**
     * <p>
     * This javadoc is tricky - it contains block tags inside an inline tag:
     * </p>
     * <p>
     * Default value is {@code @author me
     * @deprecated, @exception Error,
     * @param}.
     * </p>
     * <p>
     * Although this is not documented, block tags take precedence over inline tags.
     * The result is rendered as
     * </p>
     * <pre>
     * Deprecated.
     * Default value is {&#64;code &#64;author me
     * </pre>
     */
    public int field;

}
