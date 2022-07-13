package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

public class InputSuppressWarningsHolderWithAndWithoutCheckSuffixDifferentCases {
    private static final int a = 0; // violation 'invalid pattern'

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
