/*
ParameterName
format = (default)^[a-z][a-zA-Z0-9]*$
ignoreOverridden = (default)false
accessModifiers = (default)public, protected, package, private


*/

package com.puppycrawl.tools.checkstyle.checks.naming.parametername;

public class InputParameterNameReceiver {
    public void foo4(InputParameterNameReceiver this) {}

    private class Inner {
        public Inner(InputParameterNameReceiver InputParameterNameReceiver.this) {} // ok
    }
}
