/*
com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck=paramnum

com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck
max = (default)7

*/

package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

public class InputSuppressWarningsHolderAlias {

    public void needsLotsOfParameters0(int a, // violation 'More than 7 parameters (found 8)'
        int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }

    @SuppressWarnings("paramnum")
    public void needsLotsOfParameters(int a, // filtered violation 'max parameter'
        int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }

    @SuppressWarnings("ParamnumUnknown")
    public void needsLotsOfParameters2(int a, // violation 'More than 7 parameters (found 8)'
        int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }

    @SuppressWarnings("PAramnuM")
    public void needsLotsOfParameters3(int a, // filtered violation 'max parameter'
        int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }

    @SuppressWarnings("PARAMNUM")
    public void needsLotsOfParameters4(int a, // filtered violation 'max parameter'
        int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }

    @SuppressWarnings("paRAMnum")
    public void needsLotsOfParameters5(int a, // filtered violation 'max parameter'
        int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }

    @SuppressWarnings("pAramNUm")
    public void needsLotsOfParameters6(int a, // filtered violation 'max parameter'
        int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }
}
