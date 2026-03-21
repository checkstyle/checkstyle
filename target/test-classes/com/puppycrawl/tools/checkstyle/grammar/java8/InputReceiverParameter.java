/*
com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck
format = (default)^[a-z][a-zA-Z0-9]*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.grammar.java8;


public class InputReceiverParameter { // ok
    public void m4(InputReceiverParameter this) {}

    private class Inner {
        public Inner(InputReceiverParameter InputReceiverParameter.this) {}
    }
}
