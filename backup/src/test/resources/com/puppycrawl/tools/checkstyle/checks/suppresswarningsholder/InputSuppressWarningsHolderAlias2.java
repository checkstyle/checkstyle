/*
com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck=constant

com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$


*/

package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

public class InputSuppressWarningsHolderAlias2 {
   private static final int a = 0; // violation 'Name 'a' must match pattern'

    @SuppressWarnings("Notconstant")
    private static final int b = 0; // violation 'Name 'b' must match pattern'

    @SuppressWarnings("CONSTANT") // filtered violation 'invalid pattern'
    private static final int c = 0;

    @SuppressWarnings("CONstant") // filtered violation 'invalid pattern'
    private static final int d = 0;

    @SuppressWarnings("consTant") // filtered violation 'invalid pattern'
    private static final int e = 0;

    @SuppressWarnings("cOnStAnT") // filtered violation 'invalid pattern'
    private static final int f = 0;

    @SuppressWarnings("cOnStAnT") // filtered violation 'invalid pattern'
    private static final int g = 0;
}
