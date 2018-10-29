package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

public class InputMissingDeprecatedSkipNoJavadoc {
    @Deprecated
    public static final int MY_CONST = 123456;

    /**
     * Useless comment.
     */
    @Deprecated
    public static final int MY_CONST2 = 234567;

    /**
     * Useless comment.
     *
     * @deprecated Not for public use.
     */
    @Deprecated
    public static final int MY_CONST3 = 345678;

    /**
     * Useless comment.
     *
     * @deprecated Not for public use.
     */
    public static final int MY_CONST4 = 345678;
}
