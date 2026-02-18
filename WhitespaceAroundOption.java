package com.puppycrawl.tools.checkstyle.checks.whitespace;

/**
 * Represents the different whitespace handling options used by the
 * {@link WhitespaceAroundCheck}. Each option determines how whitespace
 * should be validated around various Java tokens such as operators,
 * keywords, and punctuation symbols.
 *
 * <p>This enum is used internally by Checkstyle to configure how the
 * WhitespaceAroundCheck enforces spacing rules in Java source code.</p>
 */
public enum WhitespaceAroundOption {
    /**
     * Require whitespace around the token.
     */
    REQUIRE,

    /**
     * Do not require whitespace around the token.
     */
    IGNORE
}
