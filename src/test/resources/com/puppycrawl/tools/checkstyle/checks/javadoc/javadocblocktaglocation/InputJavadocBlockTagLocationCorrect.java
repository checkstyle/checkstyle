package com.puppycrawl.tools.checkstyle.checks.javadoc.javadocblocktaglocation;

/**
 * configuration: default
 */
public class InputJavadocBlockTagLocationCorrect {

    /**
     * Summary {@code inline tag}.
     * Escaped &#64;version
     * <!-- @see #setRangeProperties -->
     * {@code @author, @deprecated}
     * {@literal @see @serial @hidden}
     * All @customTags should be ignored.
     * '@return' literal in {@code @return} Javadoc tag.
     * e-mail@author
     * (@param in parentheses)
     * '@param in single quotes'
     *
     * @since 1.0
     */
    public int field;

}
