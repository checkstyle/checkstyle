/*
com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = (default)

com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$


*/

package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

public class InputSuppressWarningsHolderWithAndWithoutCheckSuffixDifferentCases {
    private static final int a = 0; // violation 'Name 'a' must match pattern'

    @SuppressWarnings("checkstyle:constantnamecheck") // filtered violation 'invalid pattern'
    private static final int b = 0;

    @SuppressWarnings("checkstyle:ConstantName") // filtered violation 'invalid pattern'
    private static final int c = 0;

    @SuppressWarnings("checkstyle:ConstantNameCheck") // filtered violation 'invalid pattern'
    private static final int d = 0;

    @SuppressWarnings("checkstyle:constantname") // filtered violation 'invalid pattern'
    private static final int e = 0;

    @SuppressWarnings("checkstyle:cOnStAnTnAmEcHeCk") // filtered violation 'invalid pattern'
    private static final int f = 0;

    @SuppressWarnings("checkstyle:cOnStAnTnAmE") // filtered violation 'invalid pattern'
    private static final int g = 0;
}
