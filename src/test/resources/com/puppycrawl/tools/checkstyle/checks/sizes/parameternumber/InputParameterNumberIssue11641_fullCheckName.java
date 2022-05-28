/*
ParameterNumber
max = (default)7
ignoreOverriddenMethods = true
tokens = (default)METHOD_DEF, CTOR_DEF

com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck=paramnum

*/

package com.puppycrawl.tools.checkstyle.checks.sizes.parameternumber;

public class InputParameterNumberIssue11641_fullCheckName {

    @SuppressWarnings("paramnum") // OK
    public void needsLotsOfParameters( int a, // OK
    int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }

    @SuppressWarnings("pAramNUM") // OK
    public void needsLotsOfParameters2( int a, // OK
    int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }


    @SuppressWarnings("ParameterNumber") // OK
    public void needsLotsOfParameters3( int a, // OK
    int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }

    @SuppressWarnings("parameternumber") // OK
    public void needsLotsOfParameters4( int a, // OK
    int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }

    @SuppressWarnings("ParaMETERnumber") // OK
    public void needsLotsOfParameters5( int a, // OK
    int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }

    @SuppressWarnings("Parameter")
    public void needsLotsOfParameters6( int a, // violation
    int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }

    @SuppressWarnings("error")
    public void needsLotsOfParameters7( int a, // violation
    int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }

    public void needsLotsOfParameters8( int a, // violation
    int b, int c, int d, int e, int f, int g, int h) {
        // ...
    }
}
